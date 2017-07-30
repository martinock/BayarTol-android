package com.upiki.gatesimulatorapp.api;

import android.content.Context;

import com.upiki.gatesimulatorapp.api.ApiClass.DataResponse;
import com.upiki.gatesimulatorapp.api.ApiClass.MessageResponse;
import com.upiki.gatesimulatorapp.api.ApiClass.Toll;
import com.upiki.gatesimulatorapp.api.ApiClass.User;

import java.util.HashMap;
import java.util.Map;

public class UserApi extends Api {

    public void postRegisterUser(Context context, String email, String name, String password, String phone_number, String address, String cost, ApiListener<DataUser> apiListener) {
        Map<String, String> body = new HashMap<>();
        body.put("role", "toll");
        body.put("email", email);
        body.put("password", password);
        body.put("name", name);
        body.put("phone_number", phone_number);
        body.put("address", address);
        body.put("cost", cost);

        callPostApi(context, ApiConstanta.REGISTER, null, body, null, DataUser.class, apiListener);
    }

    public void postEditProfile(Context context, String uid, String name, String phone_number, String address, ApiListener<DataMsgResponse> apiListener) {
        Map<String, String> body = new HashMap<>();
        body.put("uid", uid);
        body.put("name", name);
        body.put("phone_number", phone_number);
        body.put("address", address);
        callPostApi(context, ApiConstanta.EDIT, null, body, null, DataMsgResponse.class, apiListener);
    }

    public void getUserProfile(Context context, String uid, ApiListener<DataUser> apiListener) {
        callGetApi(context, String.format(ApiConstanta.GET_PROFILE, uid), null, DataUser.class, apiListener);
    }

    public void getUserId(Context context, String email, ApiListener<DataUser> apiListener) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("role", "toll");
        callPostApi(context, ApiConstanta.LOGIN, null, body, null, DataUser.class, apiListener);
    }

    public void getTollTransaction(Context context, String uid, String isBusiness, ApiListener<DataMsgResponse> apiListener) {
        callGetApi(context, String.format(ApiConstanta.TRANSACTION, uid, uid, String.valueOf(isBusiness)), null, DataMsgResponse.class, apiListener);
    }

    public class DataToll extends DataResponse<Toll> {}
    public class DataUser extends DataResponse<User> {}
    public class DataMsgResponse extends DataResponse<MessageResponse> {}
}

