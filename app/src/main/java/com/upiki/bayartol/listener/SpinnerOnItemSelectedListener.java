package com.upiki.bayartol.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * A class used for handling home spinner item select.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.06.30
 */

public class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private TextView tvPaymentMethod;
    private TextView tvCurrentBalance;

    public SpinnerOnItemSelectedListener(
            TextView tvPaymentMethod,
            TextView tvCurrentBalance) {
        this.tvPaymentMethod = tvPaymentMethod;
        this.tvCurrentBalance = tvCurrentBalance;
    }

    @Override
    public void onItemSelected(
            AdapterView<?> parent,
            View view,
            int position,
            long id) {
        String parsedItem = parent.getItemAtPosition(position).toString();
        tvPaymentMethod.setText(parsedItem);

        //TODO: get the balance from API
        if (position == 0) {
            tvCurrentBalance.setText("100.000");
        } else {
            tvCurrentBalance.setText("50.000");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }
}
