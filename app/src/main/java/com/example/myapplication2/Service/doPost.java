package com.example.myapplication2.Service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.m_interface.Service;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class doPost extends AsyncTask<Void, Void, Void> {
    BaseObject baseObject;
    String data;
    String[] paras, values;
    String api;

    public doPost(BaseObject baseObject, String[] paras, String[] values, String api) {
        this.baseObject = baseObject;
        this.paras = paras;
        this.values = values;
        this.api = api;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (int i = 0; i < paras.length; i++) {
            formEncodingBuilder.add(paras[i], values[i]);
        }
        RequestBody requestBody = formEncodingBuilder.build();
        Request request = new Request.Builder().url(Service.BASE_URL + api).post(requestBody).build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            data = response.body().string();
            Log.e("response", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (data != null)
            baseObject.execPost(data);
        else
            baseObject.error();
    }
}
