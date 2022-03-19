package com.solie.myproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.solie.myproject.data.MovieInformation;
import com.solie.myproject.data.ResponseInfo;

import java.util.ArrayList;

public class MovieInfo extends Fragment {


    ImageView poster, thumbUp, thumbDown, grade;
    TextView name, date, genre, goodCountView, badCountView, bookRank, rate, viewerCount, synopsis, director, actor;
    Button writeReview1, writeReview2, seeReviews;
    ListView listView;
    View fragment;

    RatingBar ratingBar;
    // public static ReviewAdapter reviewAdapter;

    int a, b, goodCount, badCount;
    boolean thumbUpState = false;
    boolean thumbDownState = false;

    public static MovieInfo newInstance(int i) {
        MovieInfo fragment = new MovieInfo();
        Bundle args = new Bundle();
        args.putInt("bundle", i);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            a = getArguments().getInt("bundle");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_info,container,false);

        ToolbarMain.toolbarTitle.setText("영화 상세");

        poster = rootView.findViewById(R.id.movieInfoPoster);
        name = rootView.findViewById(R.id.movieInfoName);
        date = rootView.findViewById(R.id.movieInfoDate);
        genre = rootView.findViewById(R.id.movieInfoGenre);
        bookRank = rootView.findViewById(R.id.movieInfoBookRank);
        rate = rootView.findViewById(R.id.movieInfoReviewRateCount);
        viewerCount = rootView.findViewById(R.id.movieInfoViewerCount);
        synopsis = rootView.findViewById(R.id.movieInfoSynopsis);
        director = rootView.findViewById(R.id.movieInfoDirector);
        actor = rootView.findViewById(R.id.movieInfoActor);
        ratingBar = rootView.findViewById(R.id.movieInfoReviewRatingBar);
        grade = rootView.findViewById(R.id.movieInfoGrade);


        thumbUp = rootView.findViewById(R.id.thumbUp);
        thumbDown = rootView.findViewById(R.id.thumbDown);
        goodCountView = rootView.findViewById(R.id.goodCount);
        badCountView = rootView.findViewById(R.id.badCount);
        writeReview1 = rootView.findViewById(R.id.gotoWrite1);
        writeReview2 = rootView.findViewById(R.id.gotoWrite2);
        seeReviews = rootView.findViewById(R.id.seeAllReview);

        listView = rootView.findViewById(R.id.listView);

       // reviewAdapter = new ReviewAdapter();
       // listView.setAdapter(reviewAdapter);

        thumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbUpClick();
            }
        });
        thumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbDownClick();
            }
        });

        writeReview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeReview();
            }
        });
        writeReview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeReview();
            }
        });

        seeReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeAllReviews();
            }
        });


        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }

        RequestMovieInfo();

        return rootView;
    }

    public void thumbUpClick() {
        if (thumbDownState) {
            thumbDownState = false;
            badCount -= 1;
            thumbDown.setImageResource(R.drawable.ic_thumb_down);
            badCountView.setText(String.valueOf(badCount));
        } else if(!thumbDownState && !thumbUpState) {
            thumbUpState = true;
            goodCount += 1;
            thumbUp.setImageResource(R.drawable.ic_thumb_up_selected);
            goodCountView.setText(String.valueOf(goodCount));
        } else if(thumbUpState) {

        }
    }
    public void thumbDownClick() {
        if(thumbUpState) {
            thumbUpState = false;
            goodCount -= 1;
            thumbUp.setImageResource(R.drawable.ic_thumb_up);
            goodCountView.setText(String.valueOf(goodCount));
        } else if(!thumbUpState && !thumbDownState) {
            thumbDownState = true;
            badCount += 1;
            thumbDown.setImageResource(R.drawable.ic_thumb_down_selected);
            badCountView.setText(String.valueOf(badCount));
        } else if(thumbDownState) {

        }
    }

    public void writeReview() {
        Toast.makeText(getActivity(),"한줄평 작성 화면으로 이동하겠습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), WriteReview.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, 101);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101) {
            if(resultCode == 101) {
                String reviews = data.getStringExtra("save");
                Toast.makeText(getActivity(),reviews,Toast.LENGTH_SHORT).show();

            }
        }
    }


    public void seeAllReviews() {
        Toast.makeText(getActivity(),"한줄평 목록 화면으로 이동하겠습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),SeeAllReview.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void RequestMovieInfo() {
        b = a + 1;
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
        url += "?" + "id=" + b;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProcessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void ProcessResponse (String response) {
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200) {
            ArrayList<MovieInformation> movieInfo = info.result;

            if(info.message.equals("movie readMovie 성공")) {
                MovieInformation movieInformation = movieInfo.get(0);

                Glide.with(getActivity()).load(movieInformation.thumb).into(poster);
                name.setText(movieInformation.title);
                date.setText(movieInformation.date);
                genre.setText(movieInformation.genre);
                goodCount = movieInformation.like;
                goodCountView.setText(Integer.toString(movieInformation.like));
                badCount = movieInformation.dislike;
                badCountView.setText(Integer.toString(movieInformation.dislike));
                bookRank.setText(movieInformation.reservation_grade + "위" + movieInformation.reservation_rate + "%");
                rate.setText(Float.toString(movieInformation.user_rating));
                ratingBar.setRating(movieInformation.user_rating);
                viewerCount.setText(Integer.toString(movieInformation.audience));
                synopsis.setText(movieInformation.synopsis);
                director.setText(movieInformation.director);
                actor.setText(movieInformation.actor);

                switch (movieInformation.grade) {
                    case 12:
                        grade.setImageResource(R.drawable.ic_12);
                        break;

                    case 15:
                        grade.setImageResource(R.drawable.ic_15);
                        break;

                    case 19:
                        grade.setImageResource(R.drawable.ic_19);
                        break;
                }
            }

        }
    }

}
