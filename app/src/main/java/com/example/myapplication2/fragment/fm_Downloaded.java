package com.example.myapplication2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication2.DAO.DowloadDAO;
import com.example.myapplication2.R;
import com.example.myapplication2.activity.ChapterDownloaded;
import com.example.myapplication2.adapter.ChapterAdapter;
import com.example.myapplication2.model.mChapter;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class fm_Downloaded extends Fragment {
    View view;
    DowloadDAO dowloadDAO;
    public static ArrayList<mChapter> mChapters, chaptersTemp;
    public ChapterAdapter adapter;
    GridView gridView;
    SwipeRefreshLayout dl_refresh;

    public fm_Downloaded(Context context) {
        dowloadDAO = new DowloadDAO(context);
        initData();
    }

//    public static void refeshData(Context context) {
//        clearChapters();
//        for (mChapter c: new DowloadDAO(context).selectAll(fm_ChapterList.mComic.getComic_id()) ) {
//            com.example.myapplication2.fragment.fm_Downloaded.mChapters.add(c);
//        }
//        com.example.myapplication2.fragment.fm_Downloaded.chaptersTemp = new ArrayList<>(com.example.myapplication2.fragment.fm_Downloaded.mChapters);
//    }

    @Override
    public void onResume() {
        super.onResume();

        refreshData();
    }

    private void refreshData() {
        initData();
        setData();
    }

    private void initData() {
        if (mChapters == null)
            mChapters = dowloadDAO.selectAll(fm_ChapterList.mComic.getComic_id());
        else {
            mChapters.clear();
            for (mChapter c: dowloadDAO.selectAll(fm_ChapterList.mComic.getComic_id()) ) {
                mChapters.add(c);
            }
        }
        chaptersTemp = new ArrayList<>(mChapters);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.downloaded, container, false);

        //set control
        setControl();
        //set even
        setEvent();
        //set data
        initData();
        setData();
        //do something
        //set control here

        //set event here

        return view;
    }

    private void setData() {
        adapter = new ChapterAdapter(getActivity(), 0, mChapters);
        gridView.setAdapter(adapter);
    }

    private void setEvent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mChapter chap=new mChapter(mChapters.get(position));
                ChapterDownloaded.mChapter = chap;
                Intent intent = new Intent(requireContext(), ChapterDownloaded.class);
                startActivity(intent);
            }
        });
        dl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                setData();
                dl_refresh.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dl_refresh.setRefreshing(false);

                    }
                },1000);
            }
        });
    }

    private void setControl() {
        gridView = view.findViewById(R.id.dl_GridView);
        dl_refresh = view.findViewById(R.id.dl_refresh);
    }

    public void sortByName(String key) {
        String temp;
        temp = key;
        clearChapters();
        copyChapters();
        if (adapter != null) {
            if (!temp.replace(" ", "").equals("")) {
                for (int i = 0; i < mChapters.size(); i++) {
                    if (!Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(mChapters.get(i).getName()).find()) {
                        mChapters.remove(i);
                        i--;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private static void clearChapters() {
        while (mChapters.size() != 0)
            mChapters.remove(0);
    }

    private static void copyChapters() {
        for (mChapter mChapter : chaptersTemp) {
            mChapters.add(mChapter);
        }
    }
}
