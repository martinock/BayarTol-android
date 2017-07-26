package com.upiki.bayartol.page.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.ApiClass.User;
import com.upiki.bayartol.api.BayarTolApi;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mAddressField;
    private EditText mPhoneNumberField;
    private Button btnSubmit;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNameField = (EditText) findViewById(R.id.profile_name_field);
        mEmailField = (EditText) findViewById(R.id.profile_email_field);
        mAddressField = (EditText) findViewById(R.id.profile_address_field);
        mPhoneNumberField = (EditText) findViewById(R.id.profile_phone_number_field);
        btnSubmit = (Button) findViewById(R.id.btn_submit_profile);
        sp = getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        initEditTextsValue();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEditProfile();
            }
        });
    }

    private void initEditTextsValue() {
        mNameField.append(sp.getString(ProfileFragment.USERNAME, ""));
        mEmailField.append(sp.getString(ProfileFragment.EMAIL, ""));
        mPhoneNumberField.append(sp.getString(ProfileFragment.PHONE_NUMBER, ""));
        mAddressField.append(sp.getString(ProfileFragment.ADDRESS, ""));
    }

    private void submitEditProfile() {
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
            BayarTolApi.userApi.postRegisterUser(getApplicationContext(),
                    mEmailField.getText().toString(),
                    "123456",
                    mNameField.getText().toString(),
                    mPhoneNumberField.getText().toString(),
                    mAddressField.getText().toString(),
                    new Api.ApiListener<User>() {
                        @Override
                        public void onApiSuccess(User result, String rawJson) {
                            SharedPreferences sharedPreferences
                                    = getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(ProfileFragment.UID, result.uid);
                            editor.apply();
                            Toast.makeText(getApplicationContext(),
                                    "Berhasil mengubah profil",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onApiError(String errorMessage) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Gagal melakukan perubahan",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
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
