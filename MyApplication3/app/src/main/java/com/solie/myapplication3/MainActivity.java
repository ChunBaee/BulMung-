package com.solie.myapplication3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.solie.myapplication3.fragment.AcceptFragment;
import com.solie.myapplication3.fragment.ChatListFragment;
import com.solie.myapplication3.fragment.FindFragment;
import com.solie.myapplication3.fragment.SettingFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    TabLayout tabLayout;

    ViewPager2 mainViewPager;

    FindFragment findFragment;
    AcceptFragment acceptFragment;
    ChatListFragment chatListFragment;
    SettingFragment settingFragment;

    ArrayList<Integer> tabIconList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        setViewPager();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.MainToolBar);
        this.setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View viewToolbar = getLayoutInflater().inflate(R.layout.toolbar_view, null);
        actionBar.setCustomView(viewToolbar,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
    }

    private void setViewPager() {
        mainViewPager = findViewById(R.id.MainViewPager);

    }

}