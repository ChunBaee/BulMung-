package com.solie.projectf;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.solie.projectf.Database.DatabaseHelper;
import com.solie.projectf.Online.OnlineMovieInfoFragment;
import com.solie.projectf.Online.OnlineMoviePageFragment;
import com.solie.projectf.adapter.PageAdapter;
import com.solie.projectf.data.MovieList;
import com.solie.projectf.data.MovieListResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static TextView toolbarTitle;
    DrawerLayout drawerLayout;

    OnlineMoviePageFragment[] onlineMoviePageFragments;
    OnlineMovieInfoFragment[] onlineMovieInfoFragments;
    PageAdapter moviePageAdapter;

    public int NUM_OF_FRAGMENTS;
    public static final String databaseName = "CinemaHeaven";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setNavigationBar();
        DatabaseHelper.openDatabase(getApplicationContext(),databaseName);
        NetworkCheck();

    }

    public void setToolbar() {
        toolbar = findViewById(R.id.mainToolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("영화 목록");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        Log.d("setToolbar","툴바 설정 완료");
    }

    public void setNavigationBar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationMenu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            int id = menuItem.getItemId();
            if(id == R.id.menu_list) {
                Toast.makeText(getApplicationContext(),"영화 리스트로 돌아갑니다.",Toast.LENGTH_SHORT).show();
            }
            Log.d("setNavigationBar","네비게이션바 설정 완료");
            return true;
        });
    }

    public void NetworkCheck() {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(status == NetworkStatus.TYPE_NON_CONNECTED) {
            //오프라인으로 가기
        } else {
            if(AppHelper.requestQueue == null) {
                AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
            }
            onlineStartRequest();
        }
    }

    public void onlineStartRequest() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        StartResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void StartResponse(String response) {
        Gson gson = new Gson();
        MovieListResult movieListResult = gson.fromJson(response, MovieListResult.class);
        if(movieListResult != null) {
            ArrayList<MovieList> result = movieListResult.result;
            if(movieListResult.code == 200) {
                NUM_OF_FRAGMENTS = movieListResult.result.size();
                onlineMoviePageFragments = new OnlineMoviePageFragment[NUM_OF_FRAGMENTS];
                onlineMovieInfoFragments = new OnlineMovieInfoFragment[NUM_OF_FRAGMENTS];

                for (int roof = 0; roof < onlineMoviePageFragments.length; roof++) {
                    onlineMoviePageFragments[roof] = OnlineMoviePageFragment.newInstance(roof);
                }
                for (int roof = 0; roof < onlineMovieInfoFragments.length; roof++) {
                    onlineMovieInfoFragments[roof] = new OnlineMovieInfoFragment().newInstance(roof);
                }
                moviePageAdapter = new PageAdapter(getSupportFragmentManager());
                for (OnlineMoviePageFragment fragment : onlineMoviePageFragments) {
                    moviePageAdapter.addItem(fragment);
                }
                ViewPager pager = findViewById(R.id.viewPager);
                pager.setOffscreenPageLimit(3);
                pager.setAdapter(moviePageAdapter);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}