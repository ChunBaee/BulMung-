package com.solie.mrio.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> photos = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void addItem(Fragment photo) {
        photos.add(photo);
    }
    public void deleteItem (Fragment photo) {
        photos.remove(photo);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return photos.get(position);
    }

    @Override
    public int getCount() {
        return photos.size();
    }
}
