package com.upiki.bayartol;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.upiki.bayartol.page.history.HistoryFragment;
import com.upiki.bayartol.page.home.HomeFragment;
import com.upiki.bayartol.page.organization.OrganizationFragment;
import com.upiki.bayartol.page.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private ImageView ivHomeIcon;
    private ImageView ivHistoryIcon;
    private ImageView ivOrganizationIcon;
    private ImageView ivProfileIcon;

    private TextView tvHomeIcon;
    private TextView tvHistoryIcon;
    private TextView tvOrganizationIcon;
    private TextView tvProfileIcon;

    private TextView tvTitle;

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

        findIconView();

        selectedFragment = 0;
        HomeFragment homeFragment = new HomeFragment();
        setFragment(homeFragment);
    }

    private void findIconView() {
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

    private void clearSelectedItem() {
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
                ContextCompat.getColor(
                        getApplicationContext(),
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

    public void onNavHomeClicked(View v) {
        setSelectedItem(0);
        HomeFragment homeFragment = new HomeFragment();
        setFragment(homeFragment);
    }

    public void onNavHistoryClicked(View v) {
        setSelectedItem(1);
        HistoryFragment historyFragment =
                new HistoryFragment();
        setFragment(historyFragment);
    }

    public void onNavOrganizationClicked(View v) {
        setSelectedItem(2);
        OrganizationFragment organizationFragment =
                new OrganizationFragment();
        setFragment(organizationFragment);
    }

    public void onNavProfileClicked(View v) {
        setSelectedItem(3);
        ProfileFragment profileFragment =
                new ProfileFragment();
        setFragment(profileFragment);
    }

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }
}
