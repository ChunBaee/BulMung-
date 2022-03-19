package com.solie.projecte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.solie.projecte.R;
import com.solie.projecte.data.ReviewParcelable;

import java.util.ArrayList;

public class AllReviewAdapter extends BaseAdapter {
    Context context;
    ArrayList<ReviewParcelable> list = new ArrayList<>();
    ImageView profile;

    public AllReviewAdapter(Context context, ArrayList<ReviewParcelable> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate (R.layout.review_items,parent,false);
        }

        TextView name = convertView.findViewById(R.id.reviewerName);
        TextView time = convertView.findViewById(R.id.reviewTime);
        TextView content = convertView.findViewById(R.id.reviewContent);
        TextView recommend = convertView.findViewById(R.id.chuchonCount);
        RatingBar ratingBar = convertView.findViewById(R.id.reviewRating);
        profile = convertView.findViewById(R.id.reviewImage);

        ReviewParcelable reviewParcelable2 = list.get(position);

        name.setText(reviewParcelable2.getId());
        time.setText(reviewParcelable2.getTime());
        content.setText(reviewParcelable2.getReview());
        recommend.setText(Integer.toString(reviewParcelable2.getRecommend()));
        ratingBar.setRating(reviewParcelable2.getRating());
        Glide.with(context).load(reviewParcelable2.getProfile()).into(profile);

        return convertView;
    }
}
