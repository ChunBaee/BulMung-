package com.solie.projecte.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.solie.projecte.R;
import com.solie.projecte.activity.GalleryImage;
import com.solie.projecte.data.ThumbnailItem;
import com.solie.projecte.fragment.MovieInfoFragment;
import com.solie.projecte.fragment.MoviePageFragment;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.ViewHolder> {

    Context context;

    ArrayList<ThumbnailItem> items = new ArrayList<>();

    OnItemClickListener listener;

    public static interface OnItemClickListener {
        public void onItemClick (ViewHolder holder, View view, int position);
    }

    public RecyclerAdapter (Context context) {
        this.context = context;
    }

    public void addItem (ThumbnailItem item) {
        items.add(item);
    }
    public void addItems (ArrayList<ThumbnailItem> items) {
        this.items = items;
    }

    public ThumbnailItem getItem (int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_items, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThumbnailItem item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(listener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, playLogo;
        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recycler_thumbnails);
            playLogo = itemView.findViewById(R.id.playLogo_thumbnails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);

                    }
                }
            });
        }
        public void setItem(ThumbnailItem item) {
            Glide.with(context).load(item.getThumbImage()).into(imageView);
            if(item.isPhotoOrVideo()) {
                playLogo.setVisibility(View.GONE);
            } else {
                playLogo.setVisibility(View.VISIBLE);
            }

        }
        public void setOnItemClickListener (OnItemClickListener listener) {
            this.listener = listener;
        }
    }



}
