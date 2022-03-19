package com.solie.projecte.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.solie.projecte.AppHelper;
import com.solie.projecte.FragmentCallback;
import com.solie.projecte.NetworkStatus;
import com.solie.projecte.R;
import com.solie.projecte.activity.MainActivity;
import com.solie.projecte.data.MoiveListResult;
import com.solie.projecte.data.MovieList;
import com.solie.projecte.database.DBHelper;
import com.solie.projecte.database.DatabaseContract;

import static com.solie.projecte.activity.MainActivity.database;

public class MoviePageFragment extends Fragment {

    FragmentCallback callback;

    int roof;
    String type;

    ImageView moviePoster;
    TextView movieName;
    TextView movieInfo;
    Button button;

    String pageName;
    float pageRate;
    int pageGrade;

    public static MoviePageFragment newInstance(int roof) {
        MoviePageFragment fragment = new MoviePageFragment();
        Bundle args = new Bundle();
        args.putInt("bundle", roof);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            roof = getArguments().getInt("bundle");
        }
        if (context instanceof FragmentCallback) {
            callback = (FragmentCallback) context;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_page_fragment, container, false);

        moviePoster = rootView.findViewById(R.id.moviePageFragmentPoster);
        movieName = rootView.findViewById(R.id.moviePageFragmentName);
        movieInfo = rootView.findViewById(R.id.moviePageFragmentMain);
        button = rootView.findViewById(R.id.moviePageFragmentButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onFragmentChange(roof);
                }
            }
        });

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }
        NetworkCheck();


        return rootView;
    }

    public void NetworkCheck() {
        int status = NetworkStatus.getConnectivityStatus(getActivity());
        if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
            selectPageData(DatabaseContract.pageData.TABLE_NAME, roof);
            Toast.makeText(getContext(), "인터넷에 연결되지 않아 댓글 내용을 받아올 수 없습니다.", Toast.LENGTH_LONG).show();
        } else {
            RequestPageFragment();
        }
    }

    public void selectPageData(String tableName, int roof) {
        if (MainActivity.database != null) {

            Cursor cursor = database.query(tableName, new String[]{
                    DatabaseContract.pageData.COLUMN_NAME_pageImage,
                    DatabaseContract.pageData.COLUMN_NAME_pageName,
                    DatabaseContract.pageData.COLUMN_NAME_pageRate,
                    DatabaseContract.pageData.COLUMN_NAME_pageGrade}, null, null, null, null, null);
            cursor.moveToPosition(roof);
            Glide.with(this).load(cursor.getString(0)).into(moviePoster);
            movieName.setText(cursor.getString(1));
            movieInfo.setText("예매율 " + cursor.getFloat(2) + "% | " + cursor.getInt(3) + "세 관람가");

        }
    }

    public void RequestPageFragment() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=" + type;
        Log.d("@@@@@@@@@@@@@@@@@@@@@@", type + "타입번호");

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProcessResponse(response);
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

    public void ProcessResponse(String response) {
        Gson gson = new Gson();
        MoiveListResult moiveListResult = gson.fromJson(response, MoiveListResult.class);
        if (moiveListResult != null) {
            MovieList movieList = moiveListResult.result.get(roof);
            Glide.with(this).load(movieList.image).into(moviePoster);
            movieName.setText(movieList.title);
            movieInfo.setText("예매율 " + movieList.reservation_rate + "% | " + movieList.grade + "세 관람가");
            pageName = movieList.image;
            pageRate = movieList.reservation_rate;
            pageGrade = movieList.grade;

            DBHelper.insertPageData(movieList.image, movieList.title, movieList.reservation_rate, movieList.grade);
        }
    }




    @Override
    public void onDetach() {
        super.onDetach();
        if (callback != null) {
            callback = null;
        }
    }


}
