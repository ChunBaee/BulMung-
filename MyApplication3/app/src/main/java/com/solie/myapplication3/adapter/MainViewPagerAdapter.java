package com.solie.myapplication3.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_OF_FRAG = 4;

    public MainViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return NUM_OF_FRAG;
    }
}
