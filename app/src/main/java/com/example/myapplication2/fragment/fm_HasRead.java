package com.example.myapplication2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication2.DAO.ReadDAO;
import com.example.myapplication2.R;
import com.example.myapplication2.activity.Chapter;
import com.example.myapplication2.adapter.ChapterAdapter;
import com.example.myapplication2.model.mChapter;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class fm_HasRead extends Fragment {
    View view;
    ReadDAO readDAO;
    public static ArrayList<mChapter> mChapters, chaptersTemp;
    public ChapterAdapter adapter;
    GridView gridView;

    public fm_HasRead(Context context) {
        readDAO = new ReadDAO(context);
        initData();
//        mChapters = readDAO.selectAll(fm_ChapterList.mComic.getComic_id());
//        chaptersTemp = new ArrayList<>(mChapters);
    }

    private void initData() {
        if (mChapters == null) {
            mChapters = readDAO. selectAll(fm_ChapterList.mComic.getComic_id());
        } else {
            mChapters.clear();
            for (mChapter c : readDAO.selectAll(fm_ChapterList.mComic.getComic_id())) {
                mChapters.add(c);
            }
        }
        chaptersTemp = new ArrayList<>(mChapters);
    }

//    public static void refeshData(Context context){
//        clearChapters();
//        for (mChapter c: new ReadDAO(context).selectAll(fm_ChapterList.mComic.getComic_id()) ) {
//            fm_HasRead.mChapters.add(c);
//        }
//        fm_HasRead.chaptersTemp = new ArrayList<>(fm_HasRead.mChapters);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.have_read, container, false);


        //set control
        setControl();
        //set even
        setEvent();
        //set data
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
                Chapter.mChapter = mChapters.get(position);
                Intent intent = new Intent(requireContext(), Chapter.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        gridView = view.findViewById(R.id.r_GridView);
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

    public static void clearChapters() {
        while (mChapters.size() != 0)
            mChapters.remove(0);
    }

    private static void copyChapters() {
        for (mChapter mChapter : chaptersTemp) {
            mChapters.add(mChapter);
        }
    }
}
