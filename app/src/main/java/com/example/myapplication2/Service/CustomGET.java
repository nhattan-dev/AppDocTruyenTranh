package com.example.myapplication2.Service;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.myapplication2.m_interface.Service;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class CustomGET extends AsyncTask<Void, Void, Void> {
    TextView textView;
    String api, data;

    public CustomGET(TextView textView, String api) {
        this.textView = textView;
        this.api = api;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request;
        request = new Request.Builder().url(Service.BASE_URL + api).build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            data = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (data != null) {
            try {
                int number = Integer.parseInt(data);
            } catch (Exception e) {
                data = data.split(",")[1].split(":")[1].replace('"', ' ');
            }
            textView.setText(data);
        }
    }
}
