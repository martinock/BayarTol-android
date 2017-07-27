package com.upiki.bayartol.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.upiki.bayartol.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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

    public void callPostApi(final Context context, String url, final Map<String, String> params, final Map<String, String> body, final Map headers, final Type type, final ApiListener apiListener) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            onApiResponse(new JSONObject(response), type, apiListener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onApiError(new VolleyError(e.getMessage()), apiListener);
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
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                        }

            @Override
            public byte[] getBody() throws AuthFailureError {
                final Map<String, String> form = body == null? new HashMap<String, String>() : body;
                JSONObject jsonObject = new JSONObject(form);
                String requestBody = jsonObject.toString();

                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> form = params == null ? new HashMap<String, String>() : params;
                return form;
            }
        };

        Log.d(POST, "URL: " + url);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void callGetArrayApi(final Context context, String url, final Map headers, final Type type, final ApiListener apiListener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    onApiResponseArray(response, type, apiListener);
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
                    return headers;
                }

            }
        };

        Log.d(GET, "URL: " + url);
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void callGetApi(final Context context, String url, final Map headers, final Type type, final ApiListener apiListener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {

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
                    return headers;
                }

            }
        };

        Log.d(GET, "URL: " + url);
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


    public void onApiResponse(JSONObject respond, Type type, ApiListener apiListener) {
        try {
            Log.d("RESPONSE", respond.toString());
            T res = gson.fromJson(respond.toString(), type);
            apiListener.onApiSuccess(res, respond.toString());
        } catch (Exception exception){
            apiListener.onApiError(exception.getMessage());
        }
    }

    public void onApiResponseArray(JSONArray respond, final Type type, ApiListener apiListener) {
        try {
            Log.d("RESPONSE", respond.toString());
            T res = gson.fromJson(respond.toString(), type);
            apiListener.onApiSuccess(res, respond.toString());
        } catch (Exception exception) {
            apiListener.onApiError(exception.getMessage());
        }
    }


    public void onApiError(VolleyError error, ApiListener apiListener) {
        apiListener.onApiError(error.toString());
    }

}
