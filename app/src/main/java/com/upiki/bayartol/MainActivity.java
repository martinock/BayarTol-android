package com.upiki.bayartol;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.BayarTolApi;
import com.upiki.bayartol.api.UserApi;
import com.upiki.bayartol.page.history.HistoryFragment;
import com.upiki.bayartol.page.home.HomeFragment;
import com.upiki.bayartol.page.organization.OrganizationFragment;
import com.upiki.bayartol.page.profile.ProfileFragment;

/**
 * The container activity of all fragments.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.06.30
 */
public class MainActivity extends AppCompatActivity 
{

    private ImageView ivHomeIcon;
    private ImageView ivHistoryIcon;
    private ImageView ivOrganizationIcon;
    private ImageView ivProfileIcon;

    private TextView tvHomeIcon;
    private TextView tvHistoryIcon;
    private TextView tvOrganizationIcon;
    private TextView tvProfileIcon;

    private TextView tvTitle;

    SharedPreferences sharedPreferences;

    private int selectedFragment;
    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        sharedPreferences = getSharedPreferences(
                ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        findIconView();
        selectedFragment = 0;
        HomeFragment homeFragment = new HomeFragment();
        if (!sharedPreferences.contains(ProfileFragment.USERNAME)) {
            getProfile();
        } else 
           {
            setFragment(homeFragment);
           }
    }

    private void getProfile() 
    {
        String uid = sharedPreferences.getString(ProfileFragment.UID, "");
        BayarTolApi.userApi.getUserProfile(getApplicationContext(),
                uid, new Api.ApiListener<UserApi.DataUser>() {
            @Override
            public void onApiSuccess(UserApi.DataUser result, String rawJson) {
                sharedPreferences = getSharedPreferences(
                        ProfileFragment.PROFILE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ProfileFragment.USERNAME, result.data.name);
                editor.putString(ProfileFragment.EMAIL, result.data.email);
                editor.putString(ProfileFragment.PHONE_NUMBER, result.data.phone_number);
                editor.putString(ProfileFragment.ADDRESS, result.data.address);
                editor.apply();
                HomeFragment homeFragment = new HomeFragment();
                setFragment(homeFragment);
     }

            @Override
            public void onApiError(VolleyError error) {
                getProfile();
            }
        });
    }

    private void findIconView() 
    {
        ivHomeIcon =
                (ImageView) findViewById(R.id.iv_home_icon);
        tvHomeIcon = (TextView) findViewById(R.id.tv_home_icon);

        ivHistoryIcon =
                (ImageView) findViewById(R.id.iv_history_icon);
        tvHistoryIcon = (TextView) findViewById(R.id.tv_history_icon);

        ivOrganizationIcon =
                (ImageView) findViewById(R.id.iv_organization_icon);
        tvOrganizationIcon =
                (TextView) findViewById(R.id.tv_organization_icon);

        ivProfileIcon =
                (ImageView) findViewById(R.id.iv_profile_icon);
        tvProfileIcon = (TextView) findViewById(R.id.tv_profile_icon);
    }

    private void clearSelectedItem() 
    {
        ivHomeIcon.setImageResource(
                R.drawable.ic_home);
        tvHomeIcon.setTextColor(
                ContextCompat.getColor(
                        getApplicationContext(),
                        R.color.default_grey));
        ivHistoryIcon.setImageResource(
                R.drawable.ic_history);
        tvHistoryIcon.setTextColor(
                ContextCompat.getColor(
                        getApplicationContext(),
                        R.color.default_grey));
        ivOrganizationIcon.setImageResource(
                R.drawable.ic_organization);
        tvOrganizationIcon.setTextColor(
                ContextCompat.getColor(
                        getApplicationContext(),
                        R.color.default_grey));
        ivProfileIcon.setImageResource(
                R.drawable.ic_profile);
        tvProfileIcon.setTextColor(
                ContextCompat.getColor(getApplicationContext(),
                        R.color.default_grey));
    }

    private void setSelectedItem(int index) {
        clearSelectedItem();
        selectedFragment = index;
        switch (index) {
            case 0:
                tvTitle.setText(getString(R.string.app_name));
                ivHomeIcon.setImageResource(
                        R.drawable.ic_home_selected);
                tvHomeIcon.setTextColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorPrimary));
                break;
            case 1:
                tvTitle.setText(getString(R.string.history));
                ivHistoryIcon.setImageResource(
                        R.drawable.ic_history_selected);
                tvHistoryIcon.setTextColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorPrimary));
                break;
            case 2:
                tvTitle.setText(getString(R.string.organization));
                ivOrganizationIcon.setImageResource(
                        R.drawable.ic_organization_selected);
                tvOrganizationIcon.setTextColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorPrimary));
                break;
            case 3:
                tvTitle.setText(getString(R.string.profile));
                ivProfileIcon.setImageResource(
                        R.drawable.ic_profile_selected);
                tvProfileIcon.setTextColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorPrimary));
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        boolean isFragmentExist =
                getSupportFragmentManager()
                        .popBackStackImmediate(
                               String.valueOf(selectedFragment),
                                0);

        if (!isFragmentExist) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content,
                            fragment,
                            String.valueOf(
                                    selectedFragment))
                    .addToBackStack(String.valueOf(
                            selectedFragment))
                    .commit();
        }
    }

    /**
     * A method for handling click on home icon.
     * @param v the button view
     */
    public void onNavHomeClicked(View v) {
        if (selectedFragment != 0) {
            setSelectedItem(0);
            HomeFragment homeFragment = new HomeFragment();
            setFragment(homeFragment);
        }
    }

    /**
     * A method for handling click on history icon.
     * @param v the button view
     */
    public void onNavHistoryClicked(View v) {
        if (selectedFragment != 1) {
            setSelectedItem(1);
            HistoryFragment historyFragment =
                    new HistoryFragment();
            setFragment(historyFragment);
        }
    }

    /**
     * A method for handling click on organization icon.
     * @param v the button view
     */
    public void onNavOrganizationClicked(View v) {
        if (selectedFragment != 2) {
            setSelectedItem(2);
            OrganizationFragment organizationFragment =
                    new OrganizationFragment();
            setFragment(organizationFragment);
        }
    }

    /**
     * A method for handling click on profile icon.
     * @param v the button view
     */
    public void onNavProfileClicked(View v) {
        if (selectedFragment != 3) {
            setSelectedItem(3);
            ProfileFragment profileFragment =
                    new ProfileFragment();
            setFragment(profileFragment);
        }
    }

    /**
     * A method for handling back button press.
     */
    @Override
    public void onBackPressed() {
        int index = getSupportFragmentManager()
                .getBackStackEntryCount() - 1;
        if (index > 0) {
            super.onBackPressed();
            index--;
            FragmentManager.BackStackEntry backStackEntry =
                    getSupportFragmentManager()
                            .getBackStackEntryAt(index);
            String tag = backStackEntry.getName();
            setSelectedItem(Integer.parseInt(tag));
            return;
        }

        //if there is no fragment in back stack anymore
        if (isBackPressed) {
            finish();
            return;
        }
        isBackPressed = true;
        Toast.makeText(
                getApplicationContext(),
                R.string.quit_application_instruction,
                Toast.LENGTH_SHORT).show();

        //If user not pressed back button for 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
