package com.example.myapplication2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.example.myapplication2.API.ChapterListAPI;
import com.example.myapplication2.API.doGet;
import com.example.myapplication2.DAO.DowloadDAO;
import com.example.myapplication2.DAO.ReadDAO;
import com.example.myapplication2.R;
import com.example.myapplication2.activity.Chapter;
import com.example.myapplication2.adapter.ChapterAdapter;
import com.example.myapplication2.adapter.ChapterListViewPagerAdapter;
import com.example.myapplication2.m_interface.BaseObject;
import com.example.myapplication2.model.mChapter;
import com.example.myapplication2.model.mComic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class fm_ChapterList extends Fragment implements BaseObject {
    public static ArrayList<mChapter> mChapters, chaptersTemp;
    private String api;
    GridView gridView;
    public static mComic mComic;
    ChapterAdapter chapterAdapter;
    public boolean isLoading;
    com.example.myapplication2.fragment.fm_ChapterList application;
    SwipeRefreshLayout lc_refresh;
    View view;
    DowloadDAO dl;
    ReadDAO readDAO;
    Context context;
    ChapterListViewPagerAdapter chapterListViewPagerAdapter;

    public fm_ChapterList(Context context, ChapterListViewPagerAdapter chapterListViewPagerAdapter) {
        this.mChapters = new ArrayList<>();
        this.chaptersTemp = new ArrayList<>();
        this.context = context;
        this.chapterListViewPagerAdapter = chapterListViewPagerAdapter;
        dl = new DowloadDAO(context);
        readDAO = new ReadDAO(context);
        this.application = this;
        chapterAdapter = new ChapterAdapter(context, 0, mChapters);
        if (!isLoading) {
            api = "comicapi?comic_id=" + mComic.getComic_id();
            new doGet(this, api).execute();
            isLoading = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chapter_list, container, false);
        //set control
        setControl();
        //set even
        setEvent();
        //do something
        gridView.setAdapter(chapterAdapter);
        chapterAdapter.notifyDataSetChanged();
        return view;
    }

    private void setControl() {
        gridView = view.findViewById(R.id.lc_list);
        lc_refresh = view.findViewById(R.id.lc_refresh);
    }

    private void setEvent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter.mChapter = new mChapter(mChapters.get(position));

                //screen switching
                startActivity(new Intent(requireContext(), Chapter.class));
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (!isLoading) {
                        api = "comicapi?comic_id=" + mComic.getComic_id() + "&begin=" + (chaptersTemp.size() + 1) + "&end=" + (chaptersTemp.size() + 6 + 1);
                        new doGet(fm_ChapterList.this, api).execute();
                        isLoading = true;
                    }
                }
            }
        });
        lc_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lc_refresh.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChapters.removeAll(mChapters);
                        chaptersTemp.removeAll(chaptersTemp);
                        if (!isLoading) {
                            api = "comicapi?comic_id=" + mComic.getComic_id();
                            new doGet(fm_ChapterList.this, api).execute();
                            isLoading = true;
                        }
                        lc_refresh.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void clearChapters() {
        while (mChapters.size() != 0)
            mChapters.remove(0);
    }

    private void copyChapters() {
        for (mChapter mChapter : chaptersTemp) {
            mChapters.add(mChapter);
        }
    }

    private void setData() {
//        if (isFirstLoad) {
//            isFirstLoad = false;
//        } else {
        chapterAdapter.notifyDataSetChanged();
//        }
    }

        @Override
    public void start() {

    }

    //sửa lại API get chapter list order by name desc

        @Override
    public void execGet(String data) {
        Log.e("loading chapter: ", "");
        try {
            JSONObject object = new JSONObject(data);
            JSONArray chapterArray = object.getJSONArray("chapters");
            for (int i = 0; i < chapterArray.length(); i++) {
                JSONObject jsonObject = chapterArray.getJSONObject(i);
                mChapters.add(0, new mChapter(jsonObject));
                chaptersTemp.add(0, new mChapter(jsonObject));
            }
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        end();
    }

        @Override
    public void error() {
        Toast.makeText(getActivity(), "SOMETHING WAS WRONG", Toast.LENGTH_SHORT).show();
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
        String temp;
        temp = key;
        clearChapters();
        copyChapters();
        if (!temp.replace(" ", "").equals("")) {
            for (int i = 0; i < mChapters.size(); i++) {
                if (!Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(mChapters.get(i).getName()).find()) {
                    mChapters.remove(i);
                    i--;
                }
            }
        }
        chapterAdapter.notifyDataSetChanged();
    }
}
