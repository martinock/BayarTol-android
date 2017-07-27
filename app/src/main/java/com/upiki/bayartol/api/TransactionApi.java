package com.upiki.bayartol.api;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.upiki.bayartol.model.Payment;

import java.util.List;

/**
 * Created by taufic on 7/16/2017.
 */

public class TransactionApi extends Api {

    public void getHistoryPayment(Context context, String uid, String start_date, String end_date, int current, int limit, ApiListener<List<Payment>> apiListener) {
        callGetArrayApi(context, String.format(ApiConstanta.GET_HISTORY_BY_DATE, uid, start_date, end_date, current, limit), null, new TypeToken<List<Payment>>() {}.getType(), apiListener);
    }

}
