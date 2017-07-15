package com.upiki.bayartol.api;

import android.content.Context;

import com.upiki.bayartol.api.ApiClass.User;

import java.util.HashMap;
import java.util.Map;

public class UserApi extends Api {

    public void postRegisterUser(Context context, String email, String password, String name, String phone_number, String address, ApiListener<User> apiListener) {
        Map<String, String> form = new HashMap<>();
        form.put("role", "user");
        form.put("email", email);
        form.put("password", password);
        form.put("name", name);
        form.put("phone_number", phone_number);
        form.put("address", address);

        callPostApi(context, ApiConstanta.REGISTER, form, null, User.class, apiListener);
    }

    public void getUserProfile(Context context, ApiListener<User> apiListener) {

        callPostApi(context, ApiConstanta.GET_PROFILE, null, getHeaders(context), User.class, apiListener);
    }



}

