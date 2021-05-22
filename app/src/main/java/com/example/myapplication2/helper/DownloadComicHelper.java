package com.example.myapplication2.helper;

import android.os.AsyncTask;

import com.example.myapplication2.m_interface.ByteArrayBaseObject;
import com.example.myapplication2.m_interface.Service;
import com.example.myapplication2.model.mChapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DownloadComicHelper extends AsyncTask<Void, Void, Void> {
    ArrayList<byte[]> blobs;
    mChapter mChapter;
    ByteArrayBaseObject byteArrayBaseObject;
    ArrayList<String> imageIDs = new ArrayList<>();

    public DownloadComicHelper(com.example.myapplication2.model.mChapter mChapter, ByteArrayBaseObject byteArrayBaseObject) {
        this.mChapter = mChapter;
        this.byteArrayBaseObject = byteArrayBaseObject;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String data, api;
        api = "ChapterApi?chapter_id=" + mChapter.getChapter_id();

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
        if (data != null){
            try {
                JSONObject object = new JSONObject(data);
                String str = object.getString("link");
                String[] arr = str.replace('"', '[')
                        .replace("[", "")
                        .replace("]", "").split(",");
                for (String s : arr) {
                    imageIDs.add(s);
                }
                blobs = ImageHelper.toBlobs(imageIDs);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (blobs.size() < 1){
            byteArrayBaseObject.errorByteArrayAsyncTask();
        }else {
            byteArrayBaseObject.execByteArrayAsyncTask(blobs);
        }
    }
}
