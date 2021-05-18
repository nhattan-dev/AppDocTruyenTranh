package com.example.myapplication2.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication2.fragment.fm_ChapterList;
import com.example.myapplication2.fragment.fm_Downloaded;
import com.example.myapplication2.fragment.fm_HasRead;

public class ChapterListViewPagerAdapter extends FragmentStatePagerAdapter {
    public fm_ChapterList fmChapterList;
    public fm_Downloaded fmDownloaded;
    public fm_HasRead fmHaveRead;
    private Context context;

    public ChapterListViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
        fmHaveRead = new fm_HasRead(context);
        fmDownloaded = new fm_Downloaded(context);
        fmChapterList = new fm_ChapterList(context, this);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fmChapterList;
            case 1:
                return fmHaveRead;
            case 2:
                return fmDownloaded;
            default:
                return fmChapterList;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "DS chương";
                break;
            case 1:
                title = "Đã đọc";
                break;
            case 2:
                title = "Đã tải";
                break;
            default:
                title = "DS chương";
                break;
        }
        return title;
    }

    public void sortByName(String key) {
        fmDownloaded.sortByName(key);
        fmHaveRead.sortByName(key);
        fmChapterList.sortByName(key);
    }
}
