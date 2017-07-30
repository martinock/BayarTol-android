package com.upiki.bayartol.api;

import android.content.Context;

import com.upiki.bayartol.api.ApiClass.DataResponse;
import com.upiki.bayartol.api.ApiClass.Organization;
import com.upiki.bayartol.api.ApiClass.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taufic on 7/30/2017.
 */

public class OrganizationApi extends Api {

    public void postAddMember(Context context, String uid, String email, ApiListener<DataMember> apiListener) {
        Map<String, String> body = new HashMap<>();
        body.put("uid", uid);
        body.put("email", email);

        callPostApi(context, ApiConstanta.ADD_MEMBER_URL, null, body, null, DataMember.class, apiListener);

    }

    public void getOrganizationData(Context context, String uid, ApiListener<DataOrganization> apiListener) {
        callGetApi(context, ApiConstanta.GET_ORGANIZATION_URL, null, DataOrganization.class, apiListener );
    }

//    public void postRegisterOrganization(Context context, String , ApiListener<>)
    public class DataMember extends DataResponse<List<User>> {}
    public class DataOrganization extends DataResponse<Organization> {}
}