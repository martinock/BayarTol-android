package com.upiki.bayartol.api;

import android.content.Context;

import com.upiki.bayartol.api.ApiClass.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taufic on 7/15/2017.
 */

public class UserApi extends Api {

    public void postUserUid(Context context, ApiListener<User> apiListener) {
        Map<String, String> form = new HashMap<>();
        String url = "";
        callGetApi(context, url, form, null,User.class, apiListener);
    }

    public class UserDao extends Api<User> {}
}

