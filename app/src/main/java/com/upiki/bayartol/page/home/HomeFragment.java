package com.upiki.bayartol.page.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.upiki.bayartol.R;
import com.upiki.bayartol.listener.SpinnerOnItemSelectedListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Home fragment for scanning barcode.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.06.30
 */
public class HomeFragment extends Fragment
        implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    private CheckBox cbBusinessTrip;
    private Spinner spinnerPaymentMethod;

    private TextView tvUserName;
    private TextView tvPaymentMethod;
    private TextView tvCurrentBalance;

    public HomeFragment() {
        //do nothing
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_home, container, false);
        scannerView = (ZXingScannerView) view
                .findViewById(R.id.barcode_scanner);
        cbBusinessTrip = (CheckBox) view
                .findViewById(R.id.cb_mark_as_business);
        spinnerPaymentMethod = (Spinner) view
                .findViewById(R.id.spinner_payment_method);
        tvUserName = (TextView) view
                .findViewById(R.id.tv_user_name);
        tvPaymentMethod = (TextView) view
                .findViewById(R.id.tv_payment_method);
        tvCurrentBalance = (TextView) view
                .findViewById(R.id.tv_current_balance);
        addSpinnerOnItemSelectedListener();
        return view;
    }

    private void addSpinnerOnItemSelectedListener() {
        spinnerPaymentMethod.setOnItemSelectedListener(
                new SpinnerOnItemSelectedListener(
                        tvPaymentMethod,
                        tvCurrentBalance)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        if (isBusinessTrip()) {
            Toast.makeText(getActivity(),
                    result.getText(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),
                    "Not your business",
                    Toast.LENGTH_SHORT).show();
        }
        scannerView.resumeCameraPreview(this);
    }

    private boolean isBusinessTrip() {
        return cbBusinessTrip.isChecked();
    }
}
