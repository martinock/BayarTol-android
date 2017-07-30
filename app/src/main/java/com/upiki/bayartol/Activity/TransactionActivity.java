package com.upiki.bayartol.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.upiki.bayartol.Adapter.PaymentAdapter;
import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.BayarTolApi;
import com.upiki.bayartol.api.TransactionApi;
import com.upiki.bayartol.model.Payment;
import com.upiki.bayartol.page.profile.ProfileFragment;
import com.upiki.bayartol.util.ProgressView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    public Spinner mSpinnerMonth;
    public Spinner mSpinnerYear;
    public Activity activity;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressView mLoading;
    private TextView mNoHistoryText;


    private List<Payment> listTransaction = new ArrayList<>();
    private PaymentAdapter mAdapter;

    private boolean loading;
    private int current = 0;
    private int limit = 10;
    private int visibleItemCount, totalItemCount, firstVisibleItemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        activity = this;

        // Initialize View
//        mSpinnerMonth = (Spinner) findViewById(R.id.spinner_month);
//        mSpinnerYear = (Spinner) findViewById(R.id.spinner_year);
        mRecyclerView =
            (RecyclerView) findViewById(R.id.list_transaction);
        mAdapter = new PaymentAdapter(listTransaction);
        layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(onScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        mLoading = (ProgressView) findViewById(R.id.loading);
        mNoHistoryText = (TextView) findViewById(R.id.no_history);

        // Initialize List of Month
        String[] list = new String[] {
                "All", "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        };
        List<String> listMonth = new ArrayList<>(Arrays.asList(list));

        // Initialize List of Year
        ArrayList<String> listYear = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = 2010; i <= year; i++) {
            listYear.add(Integer.toString(i));
        }

        // Initialize Spinner
//        initializeSpinner(mSpinnerMonth, listMonth, new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mSpinnerMonth.setSelection(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        initializeSpinner(mSpinnerYear, listYear, new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mSpinnerYear.setSelection(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        getTransaction();
    }

    /**
     * to initlialize view of spinner
     * @param spinner spinner view
     * @param list list of object
     * @param listener item on selected listener
     */
    private void initializeSpinner(Spinner spinner, List<?> list, AdapterView.OnItemSelectedListener listener) {
        ArrayAdapter<?> adapter = new ArrayAdapter<>(activity, R.layout.item_spinner, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);
    }

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity, TransactionActivity.class));
    }

    public void getTransaction() {
        mLoading.startProgressBar();
        SharedPreferences sharedPreferences = getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(ProfileFragment.UID, "");

        BayarTolApi.transactionApi.getTransaction(activity, uid, new Api.ApiListener<TransactionApi.DataTransaction>() {
            @Override
            public void onApiSuccess(TransactionApi.DataTransaction result, String rawJson) {
                mNoHistoryText.setVisibility(listTransaction.size() == 0?View.VISIBLE : View.GONE);
                if (!result.data.isEmpty()) {
                    listTransaction.addAll(result.data);
                    mAdapter.notifyDataSetChanged();
                    current += limit;
                    loading = true;
                } else {
                    mRecyclerView.removeOnScrollListener(onScrollListener);
                    mAdapter.stopLoadMore();
                }

                mLoading.stopProgressBar();
            }

            @Override
            public void onApiError(VolleyError errorMessage) {
                mLoading.stopShowError(errorMessage.getMessage(), true);
                mLoading.setRefresh(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTransaction();
                    }
                });
            }
        });
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            visibleItemCount = layoutManager.getChildCount();
            totalItemCount = layoutManager.getItemCount();
            firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (loading && (visibleItemCount + firstVisibleItemPos) >= totalItemCount) {
                loading = false;
                getTransaction();
            }
        }
    };
}
