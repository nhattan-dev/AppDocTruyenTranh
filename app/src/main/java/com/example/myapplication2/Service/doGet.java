package com.example.myapplication2.Service;

import android.os.AsyncTask;

import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.m_interface.Service;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class doGet extends AsyncTask<Void, Void, Void> {
    String data;
    BaseObject baseObject;
    String api;

    public doGet(BaseObject baseObject, String api) {
        this.baseObject = baseObject;
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
        if (data == null)
            baseObject.error();
        else
            baseObject.execGet(data);
    }
}
