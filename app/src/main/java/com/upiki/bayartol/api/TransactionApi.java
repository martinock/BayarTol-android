package com.upiki.bayartol.api;

import android.content.Context;

import com.upiki.bayartol.api.ApiClass.DataResponse;
import com.upiki.bayartol.model.Payment;

import java.util.List;

public class TransactionApi extends Api {

    /**
     * to get user's transaction/history payment
     * @param context
     * @param uid
     * @param start_date
     * @param end_date
     * @param current
     * @param limit
     * @param apiListener
     */
    public void getHistoryPayment(Context context, String uid, String start_date, String end_date, int current, int limit, ApiListener<DataTransaction> apiListener) {
//        callGetArrayApi(context, String.format(ApiConstanta.GET_HISTORY_BY_DATE, uid, start_date, end_date, String.valueOf(current), String.valueOf(limit)), null, new TypeToken<List<Payment>>() {}.getType(), apiListener);
        callGetApi(context, String.format(ApiConstanta.GET_HISTORY_BY_DATE, uid, start_date,
            end_date, String.valueOf(current), String.valueOf(limit)), null, DataTransaction.class, apiListener);

    }

    /**
     * to get organization's transaction
     * @param context
     * @param apiListener
     */
    public void getTransaction(Context context, ApiListener apiListener) {

    }
    public class DataTransaction extends DataResponse<List<Payment>> {}

}
