package com.upiki.bayartol.page.organization;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.upiki.bayartol.Activity.ListMemberActivity;
import com.upiki.bayartol.Activity.TransactionActivity;
import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.BayarTolApi;
import com.upiki.bayartol.api.OrganizationApi;
import com.upiki.bayartol.page.profile.ProfileFragment;
import com.upiki.bayartol.util.ProgressView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizationFragment extends Fragment {

    private LinearLayout mNoOrganizationContainer;
    private TextView mNoOrganizationLabel;
    private LinearLayout mMember;
    private LinearLayout mTransaction;
    private TextView mOrganizationName;
    private ProgressView mLoading;

    public OrganizationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organization, container, false);

        mMember = (LinearLayout) view.findViewById(R.id.goto_list_member);
        mTransaction = (LinearLayout) view.findViewById(R.id.goto_list_transaction);
        mNoOrganizationContainer = (LinearLayout) view.findViewById(R.id.no_organization_container);
        mNoOrganizationLabel = (TextView) view.findViewById(R.id.no_organization_label);
        mOrganizationName = (TextView) view.findViewById(R.id.organization_name);
        mLoading = (ProgressView) view.findViewById(R.id.loading);

//        mNoOrganizationLabel.setText("");
        getOrganizationData();


        return view;
    }

    private View.OnClickListener gotoListMember() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListMemberActivity.startThisActivity(getActivity());
            }
        };
    }

    private View.OnClickListener gotoListTransaction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionActivity.startThisActivity(getActivity());
            }
        };
    }

    private void getOrganizationData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(ProfileFragment.UID, "");

        mLoading.startProgressBar();
        BayarTolApi.organizationApi.getOrganizationData(getActivity(), uid, new Api.ApiListener<OrganizationApi.DataOrganization>() {
            @Override
            public void onApiSuccess(OrganizationApi.DataOrganization result, String rawJson) {
                mLoading.stopProgressBar();
                if (result.data.name.equals(null)) {
                    mNoOrganizationContainer.setVisibility(View.VISIBLE);
                } else {
                    mOrganizationName.setText(result.data.name);
                    mNoOrganizationContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onApiError(String errorMessage) {
                mLoading.stopShowError(errorMessage, true);
                mLoading.setRefresh(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getOrganizationData();
                    }
                });
            }
        });
        mMember.setOnClickListener(gotoListMember());
        mTransaction.setOnClickListener(gotoListTransaction());
        mNoOrganizationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
