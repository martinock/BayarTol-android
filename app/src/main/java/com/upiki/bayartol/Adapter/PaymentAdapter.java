package com.upiki.bayartol.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upiki.bayartol.R;
import com.upiki.bayartol.model.Payment;
import com.upiki.bayartol.util.BayarTolUtil;

import java.util.List;

/**
 * An adapter to payment recycler view.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.07.01
 */

public class PaymentAdapter extends
        RecyclerView.Adapter<
                PaymentAdapter.PaymentViewHolder> {
    private List<Payment> paymentList;

    public class PaymentViewHolder
            extends RecyclerView.ViewHolder {
        public TextView tvPaymentLocation;
        public TextView tvPaymentCost;
        public TextView tvPaymentDate;
        public TextView tvBusinessTrip;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            tvPaymentLocation =
                    (TextView) itemView.findViewById(
                            R.id.tv_payment_gate
                    );
            tvPaymentCost=
                    (TextView) itemView.findViewById(
                            R.id.tv_payment_cost
                    );
            tvPaymentDate =
                    (TextView) itemView.findViewById(
                            R.id.tv_payment_date
                    );
            tvBusinessTrip =
                    (TextView) itemView.findViewById(
                            R.id.tv_history_business_trip
                    );
        }
    }

    public PaymentAdapter(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_history, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        holder.tvPaymentLocation.setText(payment.getToll_name());
        String formatedCost = BayarTolUtil.currencyFormatter(
                payment.getCost());
        holder.tvPaymentCost.setText(formatedCost);
        holder.tvPaymentDate.setText(payment.getDatetime());
        holder.tvBusinessTrip.setVisibility(
                payment.isBusinessTrip()
                        ? View.VISIBLE
                        : View.GONE);
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }
}
