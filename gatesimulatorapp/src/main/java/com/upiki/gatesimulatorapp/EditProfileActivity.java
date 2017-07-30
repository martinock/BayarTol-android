package com.upiki.gatesimulatorapp;

import android.content.Context;
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

import com.android.volley.VolleyError;
import com.upiki.gatesimulatorapp.api.Api;
import com.upiki.gatesimulatorapp.api.BayarTolApi;
import com.upiki.gatesimulatorapp.api.UserApi;

import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.ADDRESS;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.COST;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.PHONE_NUMBER;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.PROFILE;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.UID;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.USERNAME;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mAddressField;
    private EditText mPhoneNumberField;
    private EditText mCost;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        mNameField = (EditText) findViewById(R.id.profile_name_field);
        mAddressField = (EditText) findViewById(R.id.profile_address_field);
        mPhoneNumberField = (EditText) findViewById(R.id.profile_phone_number_field);
        mCost = (EditText) findViewById(R.id.profile_toll_cost);
        btnSubmit = (Button) findViewById(R.id.btn_submit_profile);
        progressBar = (ProgressBar) findViewById(R.id.edit_profile_progress_bar);
        sp = getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
        initEditTextsValue();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEditProfile();
            }
        });
    }

    private void initEditTextsValue() {
        mNameField.append(sp.getString(USERNAME, ""));
        mPhoneNumberField.append(sp.getString(PHONE_NUMBER, ""));
        mAddressField.append(sp.getString(ADDRESS, ""));
        mCost.append(sp.getString(COST, ""));
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
        if (TextUtils.isEmpty(mCost.getText().toString())) {
            isValid = false;
            mCost.setError("Harus diisi");
        }
        if (TextUtils.isEmpty(mPhoneNumberField.getText().toString())) {
            isValid = false;
            mPhoneNumberField.setError("Harus diisi");
        } else if (!mPhoneNumberField.getText().toString().matches("[0-9]+")){
            isValid = false;
            mPhoneNumberField.setError("Harus berupa angka");
        }

        if (isValid) {
            final String uid = sp.getString(UID, "");
            final String name = mNameField.getText().toString();
            final String phone = mPhoneNumberField.getText().toString();
            final String address = mAddressField.getText().toString();
            final String cost = mCost.getText().toString();
            BayarTolApi.userApi.postEditProfile(
                    getApplicationContext(), uid, name, phone, address,
                    new Api.ApiListener<UserApi.DataMsgResponse>() {
                        @Override
                        public void onApiSuccess(UserApi.DataMsgResponse result, String rawJson) {
                            Toast.makeText(getApplicationContext(),
                                    "Profil berhasil diubah",
                                    Toast.LENGTH_SHORT).show();
                            sp.edit().putString(UID, uid).apply();
                            sp.edit().putString(USERNAME, name).apply();
                            sp.edit().putString(PHONE_NUMBER, phone).apply();
                            sp.edit().putString(ADDRESS, address).apply();
                            finish();
                        }

                        @Override
                        public void onApiError(VolleyError error) {
                            Toast.makeText(getApplicationContext(),
                                    "Gagal mengubah profil",
                                    Toast.LENGTH_SHORT).show();
                            enableView();
                        }
                    });
        } else {
            enableView();
        }
    }

    private void disableView() {
        mNameField.setEnabled(false);
        mPhoneNumberField.setEnabled(false);
        mAddressField.setEnabled(false);
        mCost.setEnabled(false);
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
        mCost.setEnabled(true);
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
