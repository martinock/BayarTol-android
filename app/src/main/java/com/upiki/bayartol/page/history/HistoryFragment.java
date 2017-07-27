package com.upiki.bayartol.page.history;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.upiki.bayartol.Adapter.PaymentAdapter;
import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.BayarTolApi;
import com.upiki.bayartol.model.Payment;
import com.upiki.bayartol.page.profile.ProfileFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private static final String TODAY = "Hari ini";
    private static final String YESTERDAY = "Kemarin";
    private static final String LASTWEEK = "1 minggu yang lalu";
    private static final String LASTMONTH = "1 bulan yang lalu";
    private static final String ALL = "> 1 bulan yang lalu";

    private List<Payment> paymentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PaymentAdapter mAdapter;
    private Spinner mSpinnerPaymentTime;
    private String[] dateList;
    private String start_date;
    private String end_date;
    private int minusDay = 0;
    private int minusMonth = 0;
    private Calendar calendar;
    private String stringFormat = "yyyy-MM-dd hh:mm:ss";
    private SimpleDateFormat simpleDateFormat;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView =
                (RecyclerView) view.findViewById(R.id.rv_transaction_history);
        mAdapter = new PaymentAdapter(paymentList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mSpinnerPaymentTime = (Spinner) view.findViewById(R.id.spinner_trip_time);

        dateList = getResources().getStringArray(R.array.history_trip_times);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(stringFormat);

        initializeSpinner(mSpinnerPaymentTime, historyListener);

        return view;
    }

    /**
     * to initlialize view of spinner
     * @param spinner spinner view
     * @param listener item on selected listener
     */
    private void initializeSpinner(Spinner spinner, AdapterView.OnItemSelectedListener listener) {
        spinner.setOnItemSelectedListener(listener);
    }

    private AdapterView.OnItemSelectedListener historyListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (TODAY.equals(mSpinnerPaymentTime.getSelectedItem().toString())) {
                minusDay = 0;
                minusMonth = 0;
            } else if (YESTERDAY.equals(mSpinnerPaymentTime.getSelectedItem().toString())) {
                minusDay = -1;
                minusMonth = 0;
            } else if (LASTWEEK.equals(mSpinnerPaymentTime.getSelectedItem().toString())) {
                minusDay = -7;
                minusMonth = 0;
            } else if (LASTMONTH.equals(mSpinnerPaymentTime.getSelectedItem().toString())) {
                minusDay = 0;
                minusMonth = -1;
            } else {
                // all
                minusDay = -1;
                minusMonth = -1;
            }

            getPaymentData(minusDay, minusMonth);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //TODO : get the payment data from API.
    private void getPaymentData(int minusDay, int minusMonth) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(ProfileFragment.UID, "");

        calendar.setTime(new Date());
        if (minusDay == 0 && minusMonth == 0) {
            start_date = simpleDateFormat.format(calendar.getTime());
        } else if (minusDay < 0 && minusMonth == 0) {
            calendar.add(Calendar.DAY_OF_MONTH, minusDay);
            start_date = simpleDateFormat.format(calendar.getTime());
        } else if (minusDay == 0 && minusMonth < 0) {
            calendar.add(Calendar.DAY_OF_MONTH, minusMonth);
            start_date = simpleDateFormat.format(calendar.getTime());
        } else {
            start_date = "";
        }

        // today
        calendar.setTime(new Date());
        end_date = (minusDay < 0 && minusMonth < 0)? "" : simpleDateFormat.format(calendar.getTime());
        
        if (TextUtils.isEmpty(uid)) {
            // do nothing because no user ID.
        } else {
            BayarTolApi.transactionApi.getHistoryPayment(getActivity(), uid, start_date, "", new Api.ApiListener<List<Payment>>() {
                @Override
                public void onApiSuccess(List<Payment> result, String rawJson) {
                    paymentList.addAll(result);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onApiError(String errorMessage) {

                }
            });
        }
//        Payment payment = new Payment(
//                "Padalarang - Cikampek",
//                35000,
//                "1 Juli 2017 (13.50)",
//                false);
//        paymentList.add(payment);
//
//        payment = new Payment(
//                "Cikampek - Padalarang",
//                650,
//                "1 Juli 2017 (18.50)",
//                true);
//        paymentList.add(payment);
//
//        payment = new Payment(
//                "Cikampek - Padalarang",
//                650100,
//                "1 Juli 2017 (18.51)",
//                false);
//        paymentList.add(payment);
//
//        payment = new Payment(
//                "Cikampek",
//                6502000,
//                "1 Juli 2017 (18.52)",
//                true);
//        paymentList.add(payment);
//
//        payment = new Payment(
//                "Cikampek - Padalarang",
//                65030000,
//                "1 Juli 2017 (18.53)",
//                false);
//        paymentList.add(payment);
//
//        payment = new Payment(
//                "Cikampek - Padalarang",
//                650400000,
//                "1 Juli 2017 (18.54)",
//                false);
//        paymentList.add(payment);
//
//        payment = new Payment(
//                "Cikampek - Padalarang",
//                6505,
//                "1 Juli 2017 (18.55)",
//                false);
//        paymentList.add(payment);
//
//        payment = new Payment(
//                "Cikampek - Padalarang",
//                6506,
//                "1 Juli 2017 (18.56)",
//                false);
//        paymentList.add(payment);


    }
}
