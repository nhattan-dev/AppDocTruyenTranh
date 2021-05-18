package com.example.myapplication2.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication2.R;
import com.example.myapplication2.adapter.ChapterListViewPagerAdapter;
import com.example.myapplication2.fragment.fm_Downloaded;
import com.example.myapplication2.fragment.fm_HasRead;
import com.google.android.material.tabs.TabLayout;

public class ChapterList extends AppCompatActivity {
    GridView gridView;
    ImageView lc_back;
    TextView lc_search;
    ChapterListViewPagerAdapter adapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        setControl();
        setData();
        setEvent();
    }

    private void setEvent() {
        lc_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lc_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = lc_search.getText().toString();
                adapter.sortByName(str);
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getPosition() == 1 && adapter.fmHaveRead.adapter != null){
////                    Toast.makeText(ChapterList.this, ""+tab.getPosition(), Toast.LENGTH_SHORT).show();
//                    fm_HasRead.refeshData(com.example.myapplication2.activity.ChapterList.this);
//                    adapter.fmHaveRead.adapter.notifyDataSetChanged();
//                }
//                if (tab.getPosition() == 2 && adapter.fmDownloaded.adapter != null){
//                    fm_Downloaded.refeshData(com.example.myapplication2.activity.ChapterList.this);
//                    adapter.fmDownloaded.adapter.notifyDataSetChanged();
//                }

                String str = lc_search.getText().toString();
                adapter.sortByName(str);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setData() {
        adapter = new ChapterListViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setControl() {
        gridView = findViewById(R.id.lc_list);
        lc_back = findViewById(R.id.lc_back);
        lc_search = findViewById(R.id.lc_search);
        mTabLayout = findViewById(R.id.acl_TabLayout);
        mViewPager = findViewById(R.id.acl_ViewPager);
    }
}