package com.upiki.bayartol.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.upiki.bayartol.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    public Spinner mSpinnerMonth;
    public Spinner mSpinnerYear;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        activity = this;

        // Initialize View
        mSpinnerMonth = (Spinner) findViewById(R.id.spinner_month);
        mSpinnerYear = (Spinner) findViewById(R.id.spinner_year);

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
        initializeSpinner(mSpinnerMonth, listMonth, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerMonth.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initializeSpinner(mSpinnerYear, listYear, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerYear.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
}
