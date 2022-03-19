package com.solie.myproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> movies = new ArrayList<>();

    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment movie) {
        movies.add(movie);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return movies.get(position);
    }

    @Override
    public int getCount() {
        return movies.size();
    }
}
