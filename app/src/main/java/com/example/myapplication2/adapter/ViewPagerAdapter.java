package com.example.myapplication2.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication2.fragment.LastUpdate;
import com.example.myapplication2.fragment.MostView;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public LastUpdate lastUpdate;
    public MostView mostView;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                mostView = new MostView();
                return mostView;
            default:
                lastUpdate = new LastUpdate();
                return lastUpdate;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "TRUYỆN MỚI CẬP NHẬT";
                break;
            case 1:
                title = "XEM NHIỀU NHẤT TUẦN";
                break;
            default:
                title = "XEM NHIỀU NHẤT TUẦN";
                break;
        }
        return title;
    }

    public void sortByName(String str) {
        mostView.sortByName(str);
        lastUpdate.sortByName(str);
    }
}
