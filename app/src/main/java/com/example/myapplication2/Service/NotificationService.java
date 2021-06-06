package com.example.myapplication2.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.myapplication2.DAO.PropertyDAO;
import com.example.myapplication2.R;
import com.example.myapplication2.activity.Home;
import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.model.Property;
import com.example.myapplication2.model.mComic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationService extends BroadcastReceiver implements BaseObject {
    Context context;
    private static boolean isLoading = false;
    private static int NotificationID = 121221323;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
//        Toast.makeText(context, "Awesome", Toast.LENGTH_SHORT).show();
        String api = "homeapi?key=0&begin=1&end=40";
        if (!isLoading) {
            isLoading = true;
            new doGet(NotificationService.this, api).execute();
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void execGet(String data) {
        try {
            JSONArray array = new JSONArray(data);
            Date lastTime = new SimpleDateFormat(Home.patternSimpleDateFormat).parse(new PropertyDAO(context).select(PropertyDAO.property_ID).getUpdate_time());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                mComic mComic = new mComic(object);
                if (lastTime.compareTo(mComic.getmChapters().get(0).getUpdate_time()) < 0) {
                    makeNotification(context, mComic.getName() + " có Chap mới, Chap " + mComic.getmChapters().get(0).getName());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        end();
    }

    @Override
    public void error() {
        end();
    }

    @Override
    public void end() {
        String currentTime = new SimpleDateFormat(Home.patternSimpleDateFormat).format(new Date());
        Property property = new PropertyDAO(context).select(PropertyDAO.property_ID);
        property.setUpdate_time(currentTime);
        new PropertyDAO(context).edit(property);
        isLoading = false;
    }

    @Override
    public void execPost(String data) {

    }

    public static void makeNotification(Context context, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.bell);
        builder.setContentTitle("THÔNG BÁO!");
        builder.setContentText(message);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationID++, builder.build());
    }
}
