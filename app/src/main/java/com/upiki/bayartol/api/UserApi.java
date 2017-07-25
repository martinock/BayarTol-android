package com.upiki.bayartol.api;

import android.content.Context;

import com.upiki.bayartol.api.ApiClass.User;

import java.util.HashMap;
import java.util.Map;

public class UserApi extends Api {

    public void postRegisterUser(Context context, String email, String password, String name, String phone_number, String address, ApiListener<User> apiListener) {
        Map<String, String> body = new HashMap<>();
        body.put("role", "user");
        body.put("email", email);
        body.put("password", password);
        body.put("name", name);
        body.put("phone_number", phone_number);
        body.put("address", address);

        callPostApi(context, ApiConstanta.REGISTER, null, body, null, User.class, apiListener);
    }

    public void getUserProfile(Context context, String uid, ApiListener<User> apiListener) {
        callGetApi(context, String.format(ApiConstanta.GET_PROFILE, uid), null, User.class, apiListener);
    }

    public void getUserId(Context context, String email, ApiListener<User> apiListener) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        callPostApi(context, ApiConstanta.LOGIN, null, body, null, User.class, apiListener);
    }
}

