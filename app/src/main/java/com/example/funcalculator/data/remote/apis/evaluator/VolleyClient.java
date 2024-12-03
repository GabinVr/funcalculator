package com.example.funcalculator.data.remote.apis.evaluator;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.funcalculator.model.expression.Expression;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VolleyClient {

    private String _base_url = "10.0.0.2"; // Alias for 127.0.0.1 in emulator (https://stackoverflow.com/questions/5528850/how-do-you-connect-localhost-in-the-android-emulator)
    private String _API_KEY = "your-secret-token";
    private String _port = "3333";
    private String _endpoint;
    private String _url;
    private Context _context;
    
    public VolleyClient(String endpoint, Context context) {
        _endpoint = endpoint;
        _url = "http://" + _base_url + ":" + _port + "/" + _endpoint;
        _context = context;
    }

    public void get(Expression expression) {
    
        StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.e("Your Array Response", response);
                    /* We reconstruct the given expression with response */
                    expression.setExpression(response);
                } else {
                    Log.e("Your Array Response", "Data Null");
                    /* We handle the empty response */
                    expression.setExpression("0");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
            }
        }) {    
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("token", _API_KEY);
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    String jsonBody = expression.toJson();
                    return jsonBody.getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    Log.e("Volley Error", "Error while encoding JSON body", e);
                    return null;
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(_context);
        queue.add(request);
    }

}