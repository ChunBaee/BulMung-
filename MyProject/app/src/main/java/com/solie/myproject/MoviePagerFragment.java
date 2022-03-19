package com.solie.myproject;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.solie.myproject.data.MovieInformation;
import com.solie.myproject.data.ResponseInfo;

public class MoviePagerFragment extends Fragment {

    FragmentCallback callback;

    ImageView imageView;
    TextView textView;
    TextView textView2;
    Button button;

    int a;

    public static MoviePagerFragment newInstance(int i) {

        MoviePagerFragment fragment = new MoviePagerFragment();
        Bundle args = new Bundle();
        args.putInt("bundle",i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(getArguments() != null) {
            a = getArguments().getInt("bundle");
        }

        if(context instanceof FragmentCallback) {
            callback = (FragmentCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_pager,container,false);

         button = rootView.findViewById(R.id.button1);

         imageView = rootView.findViewById(R.id.moviePoster);
         textView = rootView.findViewById(R.id.movieName);
         textView2 = rootView.findViewById(R.id.movieInfo);

         if(AppHelper.requestQueue == null) {
             AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
         }

         RequestMovieList();

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(callback != null) {
                     callback.onFragmentChange(a);
                 }
             }
         });

        return rootView;
    }
    public void RequestMovieList() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=1";

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
        ResponseInfo info = gson.fromJson(response,ResponseInfo.class);
        if(info.code == 200) {
            MovieInformation movieInfo = info.result.get(a);
            Glide.with(getActivity()).load(movieInfo.image).into(imageView);
            textView.setText(movieInfo.title);
            textView2.setText("예매율 " + movieInfo.reservation_rate + "% | " + movieInfo.grade + "세 관람가");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(callback != null) {
            callback = null;
        }
    }
}
