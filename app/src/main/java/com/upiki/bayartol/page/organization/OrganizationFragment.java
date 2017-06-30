package com.upiki.bayartol.page.organization;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_organization, container, false);
    }

}
