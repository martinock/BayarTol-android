package com.upiki.gatesimulatorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.upiki.gatesimulatorapp.api.Api;
import com.upiki.gatesimulatorapp.api.ApiClass.User;
import com.upiki.gatesimulatorapp.api.BayarTolApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginAndRegisterActivity extends AppCompatActivity {
    public static String PROFILE = "gate_profile";
    public static String UID = "gate_uid";
    public static String USERNAME = "gate_username";
    public static String EMAIL = "gate_email";
    public static String PHONE_NUMBER = "gate_phone_number";
    public static String ADDRESS = "gate_address";

    private EditText etEmail;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        etEmail = (EditText) findViewById(R.id.login_email_field);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void onSubmitClick(final View view) {
        view.setClickable(false);
        etEmail.setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(getColor(R.color.button_disabled));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.button_disabled));
        }
        progressBar.setVisibility(View.VISIBLE);
        etEmail.setError(null);
        final String email = etEmail.getText().toString();
        if (email.isEmpty()) {
            view.setClickable(true);
            etEmail.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setBackgroundColor(getColor(R.color.colorPrimary));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            progressBar.setVisibility(View.INVISIBLE);
            etEmail.setError(getString(R.string.error_field_required));
            return;
        }
        if (!isEmailValid(email)) {
            view.setClickable(true);
            etEmail.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setBackgroundColor(getColor(R.color.colorPrimary));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            progressBar.setVisibility(View.INVISIBLE);
            etEmail.setError(getString(R.string.error_email_format_invalid));
            return;
        }
        BayarTolApi.userApi.getUserId(getApplicationContext(), email, new Api.ApiListener<User>() {
            @Override
            public void onApiSuccess(User result, String rawJson) {
                login(result.uid);
            }

            @Override
            public void onApiError(String errorMessage) {
                view.setClickable(true);
                etEmail.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setBackgroundColor(getColor(R.color.colorPrimary));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                registerAndLogin();
            }
        });
    }

    private boolean isEmailValid(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    private void registerAndLogin() {
//        BayarTolApi.userApi.postRegisterUser(getApplicationContext(),
//                mEmailField.getText().toString(),
//                mNameField.getText().toString(),
//                mPhoneNumberField.getText().toString(),
//                mAddressField.getText().toString(),
//                new Api.ApiListener<User>() {
//                    @Override
//                    public void onApiSuccess(User result, String rawJson) {
//                        SharedPreferences sharedPreferences
//                                = getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(ProfileFragment.UID, result.uid);
//                        editor.apply();
//                        Toast.makeText(getApplicationContext(),
//                                "Berhasil mengubah profil",
//                                Toast.LENGTH_SHORT)
//                                .show();
//                    }
//
//                    @Override
//                    public void onApiError(String errorMessage) {
//                        Toast.makeText(
//                                getApplicationContext(),
//                                "Gagal melakukan perubahan",
//                                Toast.LENGTH_SHORT
//                        ).show();
//                    }
//                }
//        );
    }

    private void login(String uid) {
        SharedPreferences sharedPreferences = getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UID, uid);
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
