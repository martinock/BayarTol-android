package com.upiki.bayartol.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.upiki.bayartol.util.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Api<T> {

    private static final String GET = "GET";
    private static final String POST = "POST";

    public interface ApiListener<T> {
        public void onApiSuccess(T result, String rawJson);

        public void onApiError(String errorMessage);
    }

    public static Gson gson = new Gson();

    public static Map<String, Object> getHeaders(Context context) {
        Map<String, Object> headers = new HashMap<>();
//        headers.put("Uid",);
        return headers;
    }

//    public void callPostApi(Context context, String url, final Map<String, String> params, final Map headers, Response.Listener<String> apiListener, Response.ErrorListener errorListener) {
    public void callPostApi(Context context, String url, final Map<String, String> params, final Map headers, final Type type, final ApiListener apiListener) {

        final Map<String, String> form = params == null? new HashMap<String, String>() : params;

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, apiListener, errorListener)
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            onApiResponse(new JSONObject(response), type, apiListener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onApiError(error, apiListener);
                    }
                }) {
                        @Override
                        public Map<String, String> getHeaders() {
                            if (headers != null) {
                                return headers;
                            } else {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json");
                                headers.put("charset", "utf-8");
                                return headers;
                            }

                        }
                        @Override
                        protected Map<String, String> getParams() {
                            return form;
                        }
                    };

        Log.d(POST, "URL: " + url);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void callGetApi(Context context, String url, final Map<String, String> params, final Map headers, final Type type, final ApiListener apiListener) {

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onApiResponse(response, type, apiListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onApiError(error, apiListener);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                if (headers != null) {
                    return headers;
                } else {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("charset", "utf-8");
                    return headers;
                }

            }
        };

        Log.d(GET, "URL: " + url);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void onApiResponse(JSONObject respond, Type type, ApiListener apiListener) {
        try {
            T res = gson.fromJson(respond.toString(), type);
            apiListener.onApiSuccess(res, respond.toString());
        } catch (Exception exception){
            apiListener.onApiError(exception.getMessage());
        }
    }

    public void onApiError(VolleyError error, ApiListener apiListener) {
        apiListener.onApiError(error.toString());
    }

}
