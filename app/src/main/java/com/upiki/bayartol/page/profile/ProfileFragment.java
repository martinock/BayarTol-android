package com.upiki.bayartol.page.profile;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.ApiClass.User;
import com.upiki.bayartol.api.UserApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static String PROFILE = "profile";
    public static String UID = "uid";

    TextView mUserName;
    TextView mUserEmail;
    EditText mNameField;
    EditText mEmailField;
    EditText mAddressField;
    EditText mPhoneNumberField;
    Button mSubmit;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mUserName = (TextView) view.findViewById(R.id.username);
        mUserEmail = (TextView) view.findViewById(R.id.email);
        mNameField = (EditText) view.findViewById(R.id.profile_name_field);
        mEmailField = (EditText) view.findViewById(R.id.profile_email_field);
        mAddressField = (EditText) view.findViewById(R.id.profile_address_field);
        mPhoneNumberField = (EditText) view.findViewById(R.id.profile_phone_number_field);
        mSubmit = (Button) view.findViewById(R.id.save_button);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
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

            UserApi userApi = new UserApi();
            userApi.postRegisterUser(getActivity(),
                    mEmailField.getText().toString(),
                    "1234",
                    mNameField.getText().toString(),
                    mPhoneNumberField.getText().toString(),
                    mAddressField.getText().toString(),
                    new Api.ApiListener<User>() {
                        @Override
                        public void onApiSuccess(User result, String rawJson) {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(UID, result.uid);
                            Toast.makeText(getActivity(), "berhasil melakukan registrasi", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onApiError(String errorMessage) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
            );

        }
    }

}
