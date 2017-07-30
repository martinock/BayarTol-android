package com.upiki.bayartol.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upiki.bayartol.R;
import com.upiki.bayartol.model.Payment;
import com.upiki.bayartol.util.BayarTolUtil;
import com.upiki.bayartol.util.LoadingViewHolder;

import java.util.List;

/**
 * An adapter to payment recycler view.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.07.01
 */

public class PaymentAdapter extends
        RecyclerView.Adapter<
            RecyclerView.ViewHolder> {

    private static final int PAYMENT = 0;
    private static final int SEE_MORE = 1;

    private boolean stop;

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

    @Override
    public int getItemViewType(int position) {
        if (position == paymentList.size() + 1) {
            return SEE_MORE;
        } else {
            return PAYMENT;
        }
    }

    public void stopLoadMore() {
        stop = true;
        notifyItemRemoved(paymentList.size());
    }

    public PaymentAdapter(List<Payment> paymentList) {
        this.paymentList = paymentList;
        stop = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == PAYMENT) {
            return new PaymentViewHolder(inflater.inflate(R.layout.item_payment_history, parent, false));
        } else if (viewType == SEE_MORE) {
            return new LoadingViewHolder(inflater.inflate(R.layout.row_loading, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PaymentViewHolder) {
            PaymentViewHolder paymentViewHolder = (PaymentViewHolder) holder;
            if (paymentList.size() > 0) {
                Payment payment = paymentList.get(position);
                paymentViewHolder.tvPaymentLocation.setText(payment.getToll_name());
                String formatedCost = BayarTolUtil.currencyFormatter(
                    payment.getCost());
                paymentViewHolder.tvPaymentCost.setText(formatedCost);
                paymentViewHolder.tvPaymentDate.setText(payment.getDatetime());
                paymentViewHolder.tvBusinessTrip.setVisibility(
                    payment.isIs_business()
                        ? View.VISIBLE
                        : View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return paymentList != null?
            paymentList.size() + (stop? 0 : 1) :
            0;
    }
}
