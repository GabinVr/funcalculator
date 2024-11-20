package com.example.funcalculator.data.remote.apis.evaluator;
import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EvaluatorClient {
    private static final String _BASE_URL = "http://127.0.0.1:3333/evaluate";
    private static final String _api_key = "your-secret-token";
    private final OkHttpClient _client;
    private String _response = "";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    public EvaluatorClient(){
        _client = new OkHttpClient();
    }
    
    public String getResponse(){
        return _response;
    }

    public void callEvaluator(String expression) {
        RequestBody body = RequestBody.create(expression, JSON);
        Request request = new Request.Builder()
                .url(_BASE_URL)
                .header("Authorization", "" + _api_key)
                .post(body)
                .build();
        _client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.getJSONObject("error").toString().equals("null")) {
                        _response = jsonObject.getJSONObject("error").toString();
                    } else {
                        _response = jsonObject.getJSONObject("result").toString();
                    }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Error: " + response.body().string());
                    _response = "Error";
                }
            }
        });
    }
}

