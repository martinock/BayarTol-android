package com.upiki.bayartol.page.home;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.upiki.bayartol.R;
import com.upiki.bayartol.listener.SpinnerOnItemSelectedListener;
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

    public HomeFragment() {
        //do nothing
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_home, container, false);
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
        generateBarcode();
        addSpinnerOnItemSelectedListener();
        return view;
    }

    public void generateBarcode() {
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
            Bitmap bitmap = Bitmap.createBitmap(
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
            barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void addSpinnerOnItemSelectedListener() {
        spinnerPaymentMethod.setOnItemSelectedListener(
                new SpinnerOnItemSelectedListener(
                        tvPaymentMethod,
                        tvCurrentBalance)
        );
    }

    private static String convertTextViewToString(TextView tv) {
        return tv.getText().toString().replace(".", "");
    }

    private boolean isBusinessTrip() {
        return cbBusinessTrip.isChecked();
    }
}
