package com.upiki.bayartol.api;

import android.content.Context;

import com.upiki.bayartol.api.ApiClass.User;

import java.util.HashMap;
import java.util.Map;

public class UserApi extends Api {

    public void postRegisterUid(Context context, String email, String password, String name, String phone_number, String address, ApiListener<User> apiListener) {
        Map<String, String> form = new HashMap<>();
        form.put("role", "user");
        form.put("email", email);
        form.put("email", email);
        form.put("email", email);
        form.put("email", email);
        String url = "http://us-central1-upiki-77460.cloudfunctions.net/register";

        callGetApi(context, url, form, null, User.class, apiListener);
    }

    public class UserDao extends Api<User> {}
}

