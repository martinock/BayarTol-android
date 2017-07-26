package com.upiki.bayartol.page.profile;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.upiki.bayartol.R;
import com.upiki.bayartol.page.login.LoginAndRegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static String PROFILE = "profile";
    public static String UID = "uid";
    public static String USERNAME = "username";
    public static String EMAIL = "email";
    public static String PHONE_NUMBER = "phone_number";
    public static String ADDRESS = "address";

    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mPhoneNumber;
    private TextView mAddress;
    private ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mUserName = (TextView) view.findViewById(R.id.username);
        mUserEmail = (TextView) view.findViewById(R.id.email);
        mPhoneNumber = (TextView) view.findViewById(R.id.phone_number);
        mAddress = (TextView) view.findViewById(R.id.address);
        progressBar = (ProgressBar) view.findViewById(R.id.profile_progress_bar);

        getProfile();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                showConfirmationDialog();
                break;
            case R.id.edit_profile:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void getProfile() {
        SharedPreferences sharedPreferences
                = getActivity()
                .getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
        mUserName.setText(sharedPreferences.getString(USERNAME, ""));
        mUserEmail.setText(sharedPreferences.getString(EMAIL, ""));
        mPhoneNumber.setText(sharedPreferences.getString(PHONE_NUMBER, ""));
        mAddress.setText(sharedPreferences.getString(ADDRESS, ""));
        progressBar.setVisibility(View.GONE);
    }
}
