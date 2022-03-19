package com.solie.projecte.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.solie.projecte.AppHelper;
import com.solie.projecte.database.DBHelper;
import com.solie.projecte.FragmentCallback;
import com.solie.projecte.NetworkStatus;
import com.solie.projecte.R;
import com.solie.projecte.adapter.PageAdapter;
import com.solie.projecte.data.MoiveListResult;
import com.solie.projecte.data.MovieList;
import com.solie.projecte.database.DatabaseContract;
import com.solie.projecte.fragment.MovieInfoFragment;
import com.solie.projecte.fragment.MoviePageFragment;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.internal.operators.maybe.MaybeNever;

import static com.solie.projecte.fragment.MovieInfoFragment.toolbar;
import static com.solie.projecte.fragment.MovieInfoFragment.toolbarTitle;

public class MainActivity extends AppCompatActivity implements FragmentCallback {

    public static DrawerLayout drawerLayout;
    public static NavigationView navigationView;

    Drawable overFlowIcon;

    ViewPager pager;

    MoviePageFragment[] moviePageFragments;
    MovieInfoFragment[] movieInfoFragments;

    public int NUM_OF_FRAGMENTS;
    PageAdapter moviePagerAdapter;

    public static ActionBar actionBar;

    public static SQLiteDatabase database;

    Animation translateUp;
    Animation translateDown;

    LinearLayout aniLayout;
    public Button menuAniBtn, aniBtn1, aniBtn2, aniBtn3;
    boolean menuPageOpen = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        overFlowIcon = getDrawable(R.drawable.order_small);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.naviView);
        aniLayout = findViewById(R.id.animate_layout);

        translateUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        translateDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);

        menuAniBtn = findViewById(R.id.toolbarButton);
        aniBtn1 = findViewById(R.id.animate_menu1);
        aniBtn2 = findViewById(R.id.animate_menu2);
        aniBtn3 = findViewById(R.id.animate_menu3);
        MenuAnimationListener listener = new MenuAnimationListener();
        translateUp.setAnimationListener(listener);
        translateDown.setAnimationListener(listener);

        menuAniBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("##############","메뉴버튼먹힘");
                menuPageOpen = true;
                menuAnimate();
            }
        });

        setToolbar();
        DatabaseOpen();
        NetworkCheck();
    }

    public void setToolbar() {
        toolbar = findViewById(R.id.mainToolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(overFlowIcon);
        toolbarTitle.setText("영화 목록");
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
    }

    public void DatabaseOpen() {
        DBHelper helper = DBHelper.getInstance(getApplicationContext());
        database = helper.getWritableDatabase();
    }

    public void NetworkCheck() {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
            OfflineRequest();
        } else {
            StartRequest();
        }
    }

    public void menuAnimate() {
        Log.d("##############","메뉴애니메이트 넘어옴");
        aniLayout.setVisibility(View.VISIBLE);
        aniLayout.setAnimation(translateDown);
        aniBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAniBtn.setBackgroundResource(R.drawable.order11);
                Toast.makeText(getApplicationContext(), "예매일순 정렬",Toast.LENGTH_SHORT).show();
                aniLayout.setAnimation(translateUp);
                aniLayout.setVisibility(View.INVISIBLE);
            }
        });
        aniBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAniBtn.setBackgroundResource(R.drawable.order22);
                Toast.makeText(getApplicationContext(), "큐레이션 정렬",Toast.LENGTH_SHORT).show();
                aniLayout.setAnimation(translateUp);
                aniLayout.setVisibility(View.INVISIBLE);
            }
        });
        aniBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAniBtn.setBackgroundResource(R.drawable.order33);
                Toast.makeText(getApplicationContext(), "개봉일순 정렬",Toast.LENGTH_SHORT).show();
                aniLayout.setAnimation(translateUp);
                aniLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
    class MenuAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(menuPageOpen) {
                menuPageOpen = false;
            } else {
                menuPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    public void StartRequest() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        StringRequest myrequest = new StringRequest(
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
        myrequest.setShouldCache(false);
        AppHelper.requestQueue.add(myrequest);
    }

    public void StartResponse(String response) {
        Gson gson = new Gson();
        MoiveListResult movieListResult = gson.fromJson(response, MoiveListResult.class);
        if (movieListResult != null) {
            ArrayList<MovieList> result = movieListResult.result;
            if (movieListResult.code == 200) {

                NUM_OF_FRAGMENTS = movieListResult.result.size();
                moviePageFragments = new MoviePageFragment[NUM_OF_FRAGMENTS];
                movieInfoFragments = new MovieInfoFragment[NUM_OF_FRAGMENTS];

                for (int roof = 0; roof < moviePageFragments.length; roof++) {
                    moviePageFragments[roof] = MoviePageFragment.newInstance(roof);
                }
                for (int roof = 0; roof < movieInfoFragments.length; roof++) {
                    movieInfoFragments[roof] = MovieInfoFragment.newInstance(roof);
                }
                moviePagerAdapter = new PageAdapter(getSupportFragmentManager());
                for (MoviePageFragment fragment : moviePageFragments) {
                    moviePagerAdapter.addItem(fragment);
                }
                pager = findViewById(R.id.viewPager);
                pager.setOffscreenPageLimit(5);
                pager.setAdapter(moviePagerAdapter);

                DBHelper.insertPageNumData(NUM_OF_FRAGMENTS);
            }
        }
    }

    public void OfflineRequest() {
        selectPageNumData(DatabaseContract.pageNum.TABLE_NAME);
        moviePageFragments = new MoviePageFragment[NUM_OF_FRAGMENTS];
        movieInfoFragments = new MovieInfoFragment[NUM_OF_FRAGMENTS];

        for (int roof = 0; roof < moviePageFragments.length; roof++) {
            moviePageFragments[roof] = MoviePageFragment.newInstance(roof);
        }
        for (int roof = 0; roof < movieInfoFragments.length; roof++) {
            movieInfoFragments[roof] = MovieInfoFragment.newInstance(roof);
        }
        moviePagerAdapter = new PageAdapter(getSupportFragmentManager());
        for (MoviePageFragment fragment : moviePageFragments) {
            moviePagerAdapter.addItem(fragment);
        }
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(moviePagerAdapter);
    }

    public void selectPageNumData(String tableName) {
        if (database != null) {
            Cursor cursor = database.query(tableName, new String[] {DatabaseContract.pageNum.COLUMN_NAME_NUMOF}, null, null, null, null, null);
            cursor.moveToFirst();
            NUM_OF_FRAGMENTS = cursor.getInt(0);
            cursor.close();
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

    public void onFragmentChange(int index) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, movieInfoFragments[index]).commit();
    }
}