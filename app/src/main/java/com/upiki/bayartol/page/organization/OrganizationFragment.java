package com.upiki.bayartol.page.organization;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.upiki.bayartol.Activity.ListMemberActivity;
import com.upiki.bayartol.Activity.TransactionActivity;
import com.upiki.bayartol.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizationFragment extends Fragment {


    public OrganizationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organization, container, false);

        LinearLayout mMember = (LinearLayout) view.findViewById(R.id.goto_list_member);
        LinearLayout mTransaction = (LinearLayout) view.findViewById(R.id.goto_list_transaction);

        mMember.setOnClickListener(gotoListMember());
        mTransaction.setOnClickListener(gotoListTransaction());

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

}
