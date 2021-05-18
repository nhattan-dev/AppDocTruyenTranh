package com.example.myapplication2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.example.myapplication2.API.MostViewAPI;
import com.example.myapplication2.API.doGet;
import com.example.myapplication2.R;
import com.example.myapplication2.activity.Comic;
import com.example.myapplication2.activity.Home;
import com.example.myapplication2.adapter.ComicAdapter;
import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.model.mComic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MostView extends Fragment implements BaseObject {
    public GridView mGridView;
    EditText txt_Search;
    public static ArrayList<mComic> mComics, mComicTemp;
    public ComicAdapter adapter;
    public boolean isLoading = false;
    com.example.myapplication2.fragment.MostView application;
    SwipeRefreshLayout mv_SwipeRefreshLayout;
    private String api;

    public MostView() {
        initData();
        this.application = this;
    }

    private void initData() {
        this.mComics = new ArrayList<>();
        this.mComicTemp = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.most_view_fragment, container, false);
        mGridView = view.findViewById(R.id.gv_Most_View);
        mv_SwipeRefreshLayout = view.findViewById(R.id.mv_SwipeRefreshLayout);
        txt_Search = view.findViewById(R.id.txt_search);
        if (!isLoading) {
            api = "homeapi?key=1";
            new doGet(this, api).execute();
            isLoading = true;
        }
        setEvent();
        return view;
    }

    public void setEvent() {
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (!isLoading) {
                        api = "homeapi?key=1&begin=" + (mComicTemp.size() + 1) + "&end=" + (mComicTemp.size() + 6 + 1);
                        new doGet(MostView.this, api).execute();
                        isLoading = true;
                    }
                }
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comic.mComic = mComics.get(position);
                Intent intent = new Intent(requireContext(), Comic.class);
                startActivity(intent);
            }
        });
        mv_SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mv_SwipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        if (!isLoading) {
                            api = "homeapi?key=1";
                            new doGet(MostView.this, api).execute();
                            isLoading = true;
                        }
                        mv_SwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void execGet(String data) {
        try {
            JSONArray array = new JSONArray(data);
            boolean hasData = false;
            for (int i = 0; i < array.length() && i < 9; i++) {
                hasData = true;
                JSONObject object = array.getJSONObject(i);
                mComics.add(new mComic(object));
                mComicTemp.add(new mComic(object));
            }
            if (hasData)
                setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        end();
        Log.e("lastupdate ", "loading");
    }

    private void setData() {
        if (adapter == null) {
            adapter = new ComicAdapter(getActivity(), 0, mComics);
            mGridView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            sortByName(Home.txt_Search.getText().toString());
        }
    }

    @Override
    public void error() {
        end();
    }

    @Override
    public void end() {
        isLoading = false;
    }

    @Override
    public void execPost(String data) {

    }

    public void sortByName(String key) {
        String temp = key;
        clearComics();
        copyComics();
        if (!temp.replace(" ", "").equals("")) {
            for (int i = 0; i < mComics.size(); i++) {
                if (!Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(mComics.get(i).getName()).find()) {
                    mComics.remove(i);
                    i--;
                }
            }
        }
        Log.e("key", " " + key);
        adapter.notifyDataSetChanged();
    }

    private void clearComics() {
        while (mComics.size() != 0)
            mComics.remove(0);
    }

    private void copyComics() {
        for (mComic mComic : mComicTemp) {
            mComics.add(mComic);
        }
    }
}
