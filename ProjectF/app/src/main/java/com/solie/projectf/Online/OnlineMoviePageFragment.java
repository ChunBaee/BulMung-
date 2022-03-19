package com.solie.projectf.Online;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.solie.projectf.AppHelper;
import com.solie.projectf.ImageLoadTask;
import com.solie.projectf.R;
import com.solie.projectf.data.MovieList;
import com.solie.projectf.data.MovieListResult;

public class OnlineMoviePageFragment extends Fragment {

    int roof;

    ImageView moviePoster;
    TextView movieName;
    TextView movieInfo;
    Button button;

    public static OnlineMoviePageFragment newInstance (int roof) {
        OnlineMoviePageFragment fragment = new OnlineMoviePageFragment();
        Bundle args = new Bundle();
        args.putInt("bundle",roof);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(getArguments() != null) {
            roof = getArguments().getInt("bundle");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_pager_fragment,container,false);

        moviePoster = rootView.findViewById(R.id.moviePagerFragmentPoster);
        movieName = rootView.findViewById(R.id.moviePagerFragmentName);
        movieInfo = rootView.findViewById(R.id.moviePagerFragmentMain);

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }
        RequestPageFragment();
        return rootView;
    }

    public void RequestPageFragment() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MoviePageResponse(response);
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

    public void MoviePageResponse (String response) {
        Gson gson = new Gson();
        MovieListResult movieListResult = gson.fromJson(response, MovieListResult.class);
        if(movieListResult != null) {
            MovieList movieList = movieListResult.result.get(roof);
            ImageRequest(movieList.image);
            movieName.setText(movieList.title);
            movieInfo.setText("예매율 " + movieList.reservation_rate + "% | " + movieList.grade + " 세 관람가");
        }
    }

    public void ImageRequest (String url) {
        ImageLoadTask imageLoadTask = new ImageLoadTask(url, moviePoster);
        imageLoadTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
