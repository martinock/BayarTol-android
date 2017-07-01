package com.upiki.bayartol.page.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.upiki.bayartol.Adapter.PaymentAdapter;
import com.upiki.bayartol.R;
import com.upiki.bayartol.model.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private List<Payment> paymentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PaymentAdapter mAdapter;
    private Spinner spinnerPaymentTime;

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
        
        getPaymentData();

        return view;
    }

    //TODO : get the payment data from API.
    private void getPaymentData() {
        Payment payment = new Payment(
                "Padalarang - Cikampek",
                35000,
                "1 Juli 2017 (13.50)",
                false);
        paymentList.add(payment);

        payment = new Payment(
                "Cikampek - Padalarang",
                650,
                "1 Juli 2017 (18.50)",
                true);
        paymentList.add(payment);

        payment = new Payment(
                "Cikampek - Padalarang",
                650100,
                "1 Juli 2017 (18.51)",
                false);
        paymentList.add(payment);

        payment = new Payment(
                "Cikampek",
                6502000,
                "1 Juli 2017 (18.52)",
                true);
        paymentList.add(payment);

        payment = new Payment(
                "Cikampek - Padalarang",
                65030000,
                "1 Juli 2017 (18.53)",
                false);
        paymentList.add(payment);

        payment = new Payment(
                "Cikampek - Padalarang",
                650400000,
                "1 Juli 2017 (18.54)",
                false);
        paymentList.add(payment);

        payment = new Payment(
                "Cikampek - Padalarang",
                6505,
                "1 Juli 2017 (18.55)",
                false);
        paymentList.add(payment);

        payment = new Payment(
                "Cikampek - Padalarang",
                6506,
                "1 Juli 2017 (18.56)",
                false);
        paymentList.add(payment);

        mAdapter.notifyDataSetChanged();
    }
}
