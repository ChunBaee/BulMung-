package com.solie.projectf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.solie.projectf.ImageLoadTask;
import com.solie.projectf.R;
import com.solie.projectf.data.ReviewParcelable;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {
    Context context;
    ImageView profile;
    ArrayList<ReviewParcelable> reviewParcelables = new ArrayList<>();

    public ReviewAdapter(Context context, ArrayList<ReviewParcelable> reviewParcelables) {
        this.context = context;
        this.reviewParcelables = reviewParcelables;
    }

    @Override
    public int getCount() {
        return reviewParcelables.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewParcelables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_items, parent, false);
        }
        TextView name = convertView.findViewById(R.id.reviewerName);
        TextView time = convertView.findViewById(R.id.reviewTime);
        TextView content = convertView.findViewById(R.id.reviewContent);
        TextView recommend = convertView.findViewById(R.id.chuchonCount);
        RatingBar ratingBar = convertView.findViewById(R.id.reviewRating);
        profile = convertView.findViewById(R.id.reviewImage);

        ReviewParcelable reviewParcelable = reviewParcelables.get(position);

        name.setText(reviewParcelable.getId());
        time.setText(reviewParcelable.getTime());
        content.setText(reviewParcelable.getReview());
        recommend.setText(Integer.toString(reviewParcelable.getRecommend()));
        ratingBar.setRating(reviewParcelable.getRating());
        if(reviewParcelable.getProfile() != null) {
            ImageRequest(reviewParcelable.getProfile());
        } else {
            profile.setImageResource(R.drawable.user1);
        }
        return convertView;
    }

    public void ImageRequest (String url) {
        ImageLoadTask imageLoadTask = new ImageLoadTask(url, profile);
        imageLoadTask.execute();
    }

}
