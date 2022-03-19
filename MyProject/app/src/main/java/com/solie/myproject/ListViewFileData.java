package com.solie.myproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class ListViewFileData extends LinearLayout {

    ImageView ivreviewImage;
    TextView tvreviewName;
    TextView tvreviewTime;
    RatingBar rbreviewRating;
    TextView tvreviewMain;
    TextView tvrecommend;

    public ListViewFileData(Context context) {
        super(context);
        init(context);
    }

    public ListViewFileData(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.review_items,this,true);

        ivreviewImage = findViewById(R.id.reviewImage);
        tvreviewName = findViewById(R.id.reviewerName);
        tvreviewTime = findViewById(R.id.reviewTime);
        rbreviewRating = findViewById(R.id.reviewRating);
        tvreviewMain = findViewById(R.id.reviewMain);
        tvrecommend = findViewById(R.id.chuchonCount);

    }

    public void setReviewImage(String reviewImage) {
        Glide.with(getContext()).load(reviewImage).into(ivreviewImage);
    }

    public void setReviewName(String reviewName) {
        tvreviewName.setText(reviewName);
    }

    public void setReviewTime(String reviewTime) {
        tvreviewTime.setText(reviewTime);
    }

    public void setReviewRating(Float reviewRating) {
        rbreviewRating.setRating(reviewRating);
    }

    public void setReviewMain(String reviewMain) {
        tvreviewMain.setText(reviewMain);
    }

    public void setRecommend(int recommend) {
        tvrecommend.setText(recommend);
    }
}
