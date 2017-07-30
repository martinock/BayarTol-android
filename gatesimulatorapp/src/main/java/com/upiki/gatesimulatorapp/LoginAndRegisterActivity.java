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

import com.android.volley.VolleyError;
import com.upiki.gatesimulatorapp.api.Api;
import com.upiki.gatesimulatorapp.api.BayarTolApi;
import com.upiki.gatesimulatorapp.api.UserApi;

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
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
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
        BayarTolApi.userApi.getUserId(getApplicationContext(), email, new Api.ApiListener<UserApi.DataUser>() {
            @Override
            public void onApiSuccess(UserApi.DataUser result, String rawJson) {
                login(result.data.uid);
            }

            @Override
            public void onApiError(VolleyError error) {
                view.setClickable(true);
                etEmail.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setBackgroundColor(getColor(R.color.colorPrimary));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                progressBar.setVisibility(View.INVISIBLE);
                if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                    registerAndLogin(email);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Terjadi kesalahan. Silahkan coba beberapa saat lagi.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    private void registerAndLogin(String email) {
        Intent intent = new Intent(getApplicationContext(), InputUserDataActivity.class);
        intent.putExtra(EMAIL, email);
        startActivity(intent);
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
