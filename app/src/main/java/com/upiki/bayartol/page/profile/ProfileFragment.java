package com.upiki.bayartol.page.profile;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.ApiClass.User;
import com.upiki.bayartol.api.BayarTolApi;
import com.upiki.bayartol.page.login.LoginAndRegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static String PROFILE = "profile";
    public static String UID = "uid";

    public TextView mUserName;
    public TextView mUserEmail;
    public EditText mNameField;
    public EditText mEmailField;
    public EditText mAddressField;
    public EditText mPhoneNumberField;
    public Button mSubmit;
    private TextView tvEditProfile;
    private ProgressBar progressBar;

    private boolean toogleShowForm = false;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);


        mUserName = (TextView) view.findViewById(R.id.username);
        mUserEmail = (TextView) view.findViewById(R.id.email);
        mNameField = (EditText) view.findViewById(R.id.profile_name_field);
        mEmailField = (EditText) view.findViewById(R.id.profile_email_field);
        mAddressField = (EditText) view.findViewById(R.id.profile_address_field);
        mPhoneNumberField = (EditText) view.findViewById(R.id.profile_phone_number_field);
        mSubmit = (Button) view.findViewById(R.id.save_button);
        TextView tvLogout = (TextView) view.findViewById(R.id.tv_logout);
        tvEditProfile = (TextView) view.findViewById(R.id.tv_edit_profile);
        progressBar = (ProgressBar) view.findViewById(R.id.profile_progress_bar);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideForm();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        getProfile();

        // Inflate the layout for this fragment
        return view;
    }

    private void showOrHideForm() {
        toogleShowForm = !toogleShowForm;
        LinearLayout llEditForm = (LinearLayout) getActivity().findViewById(R.id.ll_edit_form);
        if (toogleShowForm) {
            tvEditProfile.setText(R.string.cancel);
            mSubmit.setVisibility(View.VISIBLE);
            llEditForm.setVisibility(View.VISIBLE);
        } else {
            tvEditProfile.setText(R.string.edit_profile);
            mSubmit.setVisibility(View.GONE);
            llEditForm.setVisibility(View.GONE);
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.logout_confirmation_message)
                .setTitle(R.string.app_name);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences
                        = getActivity()
                        .getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                Intent intent = new Intent(getActivity(), LoginAndRegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveProfile() {
        boolean isValid = true;

        if (TextUtils.isEmpty(mNameField.getText().toString())) {
            isValid = false;
            mNameField.setError("Harus diisi");
        }
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            isValid = false;
            mEmailField.setError("Harus diisi");
        }
        if (TextUtils.isEmpty(mAddressField.getText().toString())) {
            isValid = false;
            mAddressField.setError("Harus diisi");
        }
        if (TextUtils.isEmpty(mPhoneNumberField.getText().toString())) {
            isValid = false;
            mPhoneNumberField.setError("Harus diisi");
        } else if (!mPhoneNumberField.getText().toString().matches("[0-9]+")){
            isValid = false;
            mPhoneNumberField.setError("Harus berupa angka");
        }

        if (isValid) {
            BayarTolApi.userApi.postRegisterUser(getActivity(),
                    mEmailField.getText().toString(),
                    "123456",
                    mNameField.getText().toString(),
                    mPhoneNumberField.getText().toString(),
                    mAddressField.getText().toString(),
                    new Api.ApiListener<User>() {
                        @Override
                        public void onApiSuccess(User result, String rawJson) {
                            SharedPreferences sharedPreferences
                                    = getActivity()
                                    .getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(UID, result.uid);
                            editor.apply();
                            Toast.makeText(getActivity(),
                                    "Berhasil melakukan registrasi",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onApiError(String errorMessage) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }

    private void getProfile() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(ProfileFragment.UID, "");
        BayarTolApi.userApi.getUserProfile(getActivity(), uid, new Api.ApiListener<User>() {
            @Override
            public void onApiSuccess(User result, String rawJson) {
                progressBar.setVisibility(View.GONE);
                mNameField.append(result.name);
                mEmailField.append(result.email);
                mPhoneNumberField.append(result.phone_number);
                mAddressField.append(result.address);
                mUserName.setText(result.name);
                mUserEmail.setText(result.email);
            }

            @Override
            public void onApiError(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
