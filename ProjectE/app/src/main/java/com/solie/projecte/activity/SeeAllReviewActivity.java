package com.solie.projecte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.solie.projecte.AppHelper;
import com.solie.projecte.R;
import com.solie.projecte.adapter.AllReviewAdapter;
import com.solie.projecte.data.CommentList;
import com.solie.projecte.data.CommentListResult;
import com.solie.projecte.data.ReviewParcelable;

import java.util.ArrayList;

public class SeeAllReviewActivity extends AppCompatActivity {

    ArrayList<ReviewParcelable> reviews = new ArrayList<>();

    TextView seeReviewName;
    ImageView seeReviewGrade;
    RatingBar seeReviewRating;
    TextView seeReviewRatingText;
    Button seeReviewWrite;

    ListView listView;
    AllReviewAdapter reviewAdapter;

    int b, grade;
    int WRITE_CODE = 101;
    public static String userProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_review);

        seeReviewName = findViewById(R.id.seeReviewName);
        seeReviewGrade = findViewById(R.id.seeReviewGrade);
        seeReviewRating = findViewById(R.id.seeReviewRating);
        seeReviewRatingText = findViewById(R.id.peopleCount);
        seeReviewWrite = findViewById(R.id.seeReviewButton);

        listView = findViewById(R.id.listViews);
        reviewAdapter = new AllReviewAdapter(this, reviews);

        if (getIntent() != null) {
            String name = getIntent().getExtras().getString("movieName");
            seeReviewName.setText(name);
            grade = getIntent().getExtras().getInt("movieGrade");
            float rating = getIntent().getExtras().getFloat("movieRating");
            seeReviewRating.setRating(rating);
            int ratingcount = getIntent().getExtras().getInt("movieTotalCount");
            seeReviewRatingText.setText(rating + "(" + ratingcount + "명 참여)");
            b = getIntent().getExtras().getInt("movieId");
            switch (grade) {
                case 12:
                    seeReviewGrade.setImageResource(R.drawable.ic_12);
                    break;
                case 15:
                    seeReviewGrade.setImageResource(R.drawable.ic_15);
                    break;
                case 19:
                    seeReviewGrade.setImageResource(R.drawable.ic_19);
                    break;
            }
        }
        seeReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeAllReviewActivity.this, WriteReviewActivity.class);
                intent.putExtra("movieName", seeReviewName.getText().toString());
                intent.putExtra("movieGrade", grade);
                intent.putExtra("movieNumber", b);

                startActivityForResult(intent, WRITE_CODE);
            }
        });

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        RequestSeeReview();
    }

    public void RequestSeeReview() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + b;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SeeReviewResponse(response);
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

    public void SeeReviewResponse(String response) {
        Gson gson = new Gson();
        CommentListResult commentListResult = gson.fromJson(response, CommentListResult.class);
        if (commentListResult != null) {
            ArrayList<CommentList> commentLists = commentListResult.result;
            if (commentListResult.code == 200) {
                for (int i = 0; i < commentLists.size(); i++) {
                    CommentList commentList = commentLists.get(i);
                    userProfile = commentList.writer_image;
                    reviews.add(new ReviewParcelable(commentList.writer_image, commentList.writer, commentList.time, commentList.contents, commentList.recommend, commentList.rating));
                    listView.setAdapter(reviewAdapter);
                }

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == WRITE_CODE) {
                String content = data.getStringExtra("contents");
                String writer = data.getStringExtra("writer");
                String time = data.getStringExtra("time");
                float rating = data.getFloatExtra("rating",0);
                reviews.add(new ReviewParcelable(userProfile, writer,time, content,1, rating));

            }
        }
        reviewAdapter.notifyDataSetChanged();
    }
}
