package com.example.funcalculator.data.remote.apis.evaluator;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.funcalculator.model.expression.Expression;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VolleyClient {

    private String _base_url = "10.0.2.2"; // Alias for 127.0.0.1 in emulator (https://stackoverflow.com/questions/5528850/how-do-you-connect-localhost-in-the-android-emulator)
    private String _API_KEY = "your-secret-token";
    private String _port = "3333";
    private String _endpoint;
    private String _url;
    private static RequestQueue _requestQueue;
    
    public VolleyClient(String endpoint, RequestQueue requestQueue) {
        _endpoint = endpoint;
        _url = "http://" + _base_url + ":" + _port + "/" + _endpoint;
        _requestQueue = requestQueue;
    }

    public void postExpression(Expression expression, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("expression", expression.getValue());
        } catch (Exception e) {
            Log.e("Volley Error", "Error while creating JSON body", e);
            return;
        }
        Log.d("VolleyClient", "Sending POST request to " + _url);
        Log.d("VolleyClient", "Request body: " + jsonBody.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, _url, jsonBody, successListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", _API_KEY);
                return params;
            }
        };
        _requestQueue.add(request);
    }
}