package com.solie.myproject;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

public class MainActivity extends ToolbarMain implements FragmentCallback{

    int NUM_OF_FRAGMENTS = 5;
    int i;

    MoviePagerFragment[] moviePagerFragments = new MoviePagerFragment[NUM_OF_FRAGMENTS];
    MovieInfo[] movieInfos = new MovieInfo[NUM_OF_FRAGMENTS];
    ListViewFragments[] listViewFragments = new ListViewFragments[NUM_OF_FRAGMENTS];
    PageAdapter moviePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(i = 0; i < moviePagerFragments.length; i++) {
            moviePagerFragments[i] = MoviePagerFragment.newInstance(i);
        }
        for(i = 0; i < movieInfos.length; i++) {
            movieInfos[i] = MovieInfo.newInstance(i);
        }
        for(i = 0; i < listViewFragments.length; i++) {
            listViewFragments[i] = ListViewFragments.newInstance(i);
        }
        moviePagerAdapter = new PageAdapter(getSupportFragmentManager());
        for (MoviePagerFragment fragment : moviePagerFragments) {
            moviePagerAdapter.addItem(fragment);
        }
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(moviePagerAdapter);
    }
    public void onFragmentChange(int index) {
        getSupportFragmentManager().beginTransaction().replace(R.id.tbSubContainer,movieInfos[index]).commit();
    }
}