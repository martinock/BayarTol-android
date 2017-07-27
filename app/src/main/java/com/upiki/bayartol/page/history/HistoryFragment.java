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
import com.upiki.bayartol.util.ProgressView;

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

    private RecyclerView recyclerView;
    private PaymentAdapter mAdapter;
    private Spinner mSpinnerPaymentTime;

    ProgressView mLoading;

    private List<Payment> paymentList = new ArrayList<>();
    private String start_date;
    private String end_date;
    private int minusDay = 0;
    private int minusMonth = 0;
    private Calendar calendar;
    private String stringFormat = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat;

    private LinearLayoutManager layoutManager;
    private boolean loading;
    private int current = 0;
    private int limit = 10;
    private int visibleItemCount, totalItemCount, firstVisibleItemPos;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        loading = true;

        recyclerView =
                (RecyclerView) view.findViewById(R.id.rv_transaction_history);
        mAdapter = new PaymentAdapter(paymentList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.setAdapter(mAdapter);

        mLoading = (ProgressView) view.findViewById(R.id.progress_view);
        mSpinnerPaymentTime = (Spinner) view.findViewById(R.id.spinner_trip_time);

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

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            visibleItemCount = layoutManager.getChildCount();
            totalItemCount = layoutManager.getItemCount();
            firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (loading && (visibleItemCount + firstVisibleItemPos) >= totalItemCount) {
                loading = false;
                getPaymentData(minusDay, minusMonth);
            }
        }
    };

    //TODO : get the payment data from API.
    private void getPaymentData(final int minusDay, final int minusMonth) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(ProfileFragment.UID, "");

        calendar.setTime(new Date());
        if (minusDay == 0 && minusMonth == 0) {
            start_date = simpleDateFormat.format(calendar.getTime());
        } else if (minusDay < 0 && minusMonth == 0) {
            calendar.add(Calendar.DAY_OF_MONTH, minusDay);
            start_date = simpleDateFormat.format(calendar.getTime());
        } else if (minusDay == 0 && minusMonth < 0) {
            calendar.add(Calendar.MONTH, minusMonth);
            start_date = simpleDateFormat.format(calendar.getTime());
        } else {
            start_date = null;
        }

        // today
        calendar.setTime(new Date());
        end_date = (minusDay < 0 && minusMonth < 0)? null : simpleDateFormat.format(calendar.getTime());
        
        if (TextUtils.isEmpty(uid)) {
            // do nothing because no user ID.
        } else {
            mLoading.startProgressBar();
            BayarTolApi.transactionApi.getHistoryPayment(getActivity(), uid, start_date, end_date, current, limit, new Api.ApiListener<List<Payment>>() {
                @Override
                public void onApiSuccess(List<Payment> result, String rawJson) {
                    if (result.isEmpty()) {
                        recyclerView.removeOnScrollListener(onScrollListener);
                        mAdapter.stopLoadMore();
                    } else {
                        paymentList.clear();
                        paymentList.addAll(result);
                        mAdapter.notifyDataSetChanged();
                        mLoading.stopProgressBar();
                        current += limit;
                        loading = true;
                    }
                }

                @Override
                public void onApiError(String errorMessage) {
                    mLoading.stopShowError(errorMessage, true);
                    mLoading.setRefresh(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPaymentData(minusDay, minusMonth);
                        }
                    });
                }
            });
        }
    }
}
