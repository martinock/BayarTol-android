package com.upiki.bayartol.page.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.upiki.bayartol.MainActivity;
import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.ApiClass.User;
import com.upiki.bayartol.api.BayarTolApi;

import static com.upiki.bayartol.page.profile.ProfileFragment.ADDRESS;
import static com.upiki.bayartol.page.profile.ProfileFragment.EMAIL;
import static com.upiki.bayartol.page.profile.ProfileFragment.PHONE_NUMBER;
import static com.upiki.bayartol.page.profile.ProfileFragment.PROFILE;
import static com.upiki.bayartol.page.profile.ProfileFragment.UID;
import static com.upiki.bayartol.page.profile.ProfileFragment.USERNAME;

public class InputUserDataActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mAddressField;
    private EditText mPhoneNumberField;
    private String email;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_user_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNameField = (EditText) findViewById(R.id.profile_name_field);
        mAddressField = (EditText) findViewById(R.id.profile_address_field);
        mPhoneNumberField = (EditText) findViewById(R.id.profile_phone_number_field);
        btnSubmit = (Button) findViewById(R.id.btn_submit_profile);
        progressBar = (ProgressBar) findViewById(R.id.edit_profile_progress_bar);
        sp = getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
        email = getIntent().getStringExtra(EMAIL);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEditProfile();
            }
        });

        Toast.makeText(getApplicationContext(),
                getString(R.string.register_description), Toast.LENGTH_LONG).show();
    }

    private void submitEditProfile() {
        disableView();
        boolean isValid = true;

        if (TextUtils.isEmpty(mNameField.getText().toString())) {
            isValid = false;
            mNameField.setError("Harus diisi");
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
            final String name = mNameField.getText().toString();
            final String phone = mPhoneNumberField.getText().toString();
            final String address = mAddressField.getText().toString();
            BayarTolApi.userApi.postRegisterUser(
                    getApplicationContext(), email, name, phone, address,
                    new Api.ApiListener<User>() {
                        @Override
                        public void onApiSuccess(User result, String rawJson) {
                            Toast.makeText(getApplicationContext(),
                                    "Profil berhasil dibuat",
                                    Toast.LENGTH_SHORT).show();
                            sp.edit().putString(UID, result.uid).apply();
                            sp.edit().putString(USERNAME, result.name).apply();
                            sp.edit().putString(EMAIL, result.email).apply();
                            sp.edit().putString(PHONE_NUMBER, result.phone_number).apply();
                            sp.edit().putString(ADDRESS, result.address).apply();
                            toHomeActivity();
                            finish();
                        }

                        @Override
                        public void onApiError(String errorMessage) {
                            Toast.makeText(getApplicationContext(),
                                    "Gagal membuat profil",
                                    Toast.LENGTH_SHORT).show();
                            enableView();
                        }
                    });
        } else {
            enableView();
        }
    }

    private void toHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void disableView() {
        mNameField.setEnabled(false);
        mPhoneNumberField.setEnabled(false);
        mAddressField.setEnabled(false);
        btnSubmit.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btnSubmit.setBackgroundColor(getColor(R.color.button_disabled));
        } else {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.button_disabled));
        }
    }

    private void enableView() {
        mNameField.setEnabled(true);
        mPhoneNumberField.setEnabled(true);
        mAddressField.setEnabled(true);
        btnSubmit.setClickable(true);
        progressBar.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btnSubmit.setBackgroundColor(getColor(R.color.colorPrimary));
        } else {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
