package com.upiki.gatesimulatorapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.ADDRESS;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.EMAIL;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.PHONE_NUMBER;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.PROFILE;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.USERNAME;

public class ProfileActivity extends AppCompatActivity {

    private TextView mUserName;
    private TextView mUserEmail;
    private TextView mPhoneNumber;
    private TextView mAddress;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        mUserName = (TextView) findViewById(R.id.username);
        mUserEmail = (TextView) findViewById(R.id.email);
        mPhoneNumber = (TextView) findViewById(R.id.phone_number);
        mAddress = (TextView) findViewById(R.id.address);
        progressBar = (ProgressBar) findViewById(R.id.profile_progress_bar);
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                showConfirmationDialog();
                break;
            case R.id.edit_profile:
                Intent intent = new Intent(getApplicationContext(),
                        EditProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        builder.setMessage(R.string.logout_confirmation_message)
                .setTitle(R.string.app_name);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences
                        = getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                Intent intent = new Intent(getApplicationContext(),
                        LoginAndRegisterActivity.class);
                startActivity(intent);
                finish();
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
                = getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
        mUserName.setText(sharedPreferences.getString(USERNAME, ""));
        mUserEmail.setText(sharedPreferences.getString(EMAIL, ""));
        mPhoneNumber.setText(sharedPreferences.getString(PHONE_NUMBER, ""));
        mAddress.setText(sharedPreferences.getString(ADDRESS, ""));
        progressBar.setVisibility(View.GONE);
    }
}
