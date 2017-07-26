package com.upiki.bayartol.page.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.upiki.bayartol.R;
import com.upiki.bayartol.listener.SpinnerOnItemSelectedListener;
import com.upiki.bayartol.page.profile.ProfileFragment;
import com.upiki.bayartol.util.BayarTolUtil;


/**
 * Home fragment for scanning barcode.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.06.30
 */
public class HomeFragment extends Fragment {
    private static final int DEFAULT_QR_CODE_SIZE = 512;

    private CheckBox cbBusinessTrip;
    private Spinner spinnerPaymentMethod;
    private ImageView barcode;

    private TextView tvUserName;
    private TextView tvPaymentMethod;
    private TextView tvCurrentBalance;

    private ProgressBar progressBar;

    public HomeFragment() {
        //do nothing
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.home_img_progress_bar);
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
        barcode = (ImageView) view.findViewById(R.id.barcode);

        SharedPreferences sp = getActivity().getSharedPreferences(
                ProfileFragment.PROFILE, Context.MODE_PRIVATE);
        tvUserName.setText(sp.getString(ProfileFragment.USERNAME, ""));

        generateBarcode();
        addSpinnerOnItemSelectedListener();
        addCheckBoxClickListener();
        return view;
    }

    private void addCheckBoxClickListener() {
        cbBusinessTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateBarcode();
            }
        });
    }

    /**
     * A method for generate barcode based on username,
     * payment method, business trip check, and current balance.
     */
    public void generateBarcode() {
        progressBar.setVisibility(View.VISIBLE);
        barcode.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String rawCode = BayarTolUtil.barcodeStringGenerator(
                        convertTextViewToString(tvUserName),
                        convertTextViewToString(tvPaymentMethod),
                        isBusinessTrip(),
                        convertTextViewToString(tvCurrentBalance)
                );
                QRCodeWriter writer = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = writer.encode(
                            rawCode,
                            BarcodeFormat.QR_CODE,
                            DEFAULT_QR_CODE_SIZE,
                            DEFAULT_QR_CODE_SIZE);
                    final Bitmap bitmap = Bitmap.createBitmap(
                            DEFAULT_QR_CODE_SIZE,
                            DEFAULT_QR_CODE_SIZE,
                            Bitmap.Config.RGB_565);
                    for (int x = 0; x < DEFAULT_QR_CODE_SIZE; x++) {
                        for (int y = 0; y < DEFAULT_QR_CODE_SIZE; y++) {
                            bitmap.setPixel(
                                    x,
                                    y,
                                    bitMatrix.get(x, y)
                                            ? Color.BLACK
                                            : Color.WHITE);
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            barcode.setImageBitmap(bitmap);
                            progressBar.setVisibility(View.GONE);
                            barcode.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addSpinnerOnItemSelectedListener() {
        spinnerPaymentMethod.setOnItemSelectedListener(
                new SpinnerOnItemSelectedListener(
                        tvPaymentMethod,
                        tvCurrentBalance,
                        this)
        );
    }

    private static String convertTextViewToString(TextView tv) {
        return tv.getText().toString().replace(".", "");
    }

    private boolean isBusinessTrip() {
        return cbBusinessTrip.isChecked();
    }
}
