package com.solie.mrio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.solie.mrio.R;
import com.solie.mrio.data.AcceptListData;
import com.solie.mrio.data.ProfileSettingData;

import java.util.ArrayList;

public class ProfileSettingAdapter extends RecyclerView.Adapter<ProfileSettingAdapter.ViewHolder> {

    Context context;
    ArrayList<ProfileSettingData> images = new ArrayList<>();

    OnItemClickListener listener;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public ProfileSettingAdapter(Context context) {
        this.context = context;
    }

    public void addItem (ProfileSettingData image) {
        images.add(image);
    }

    public void changeItem (int position, ProfileSettingData image) {
        images.remove(position);
        images.add(position, image);
    }

    public Bitmap setProfileImage (int position) {
        return images.get(position).getImage();
    }

    public ProfileSettingData getItem(int position) {
        return images.get(position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.setting_profile_photo_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfileSettingData image = images.get(position);
        holder.setItem(image);

        holder.setOnItemClickListener(listener);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImage;
        LayerDrawable drawable;
        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profile_photo);
            drawable = (LayerDrawable) ContextCompat.getDrawable(context,R.drawable.chatlist_border);
            profileImage.setBackground(drawable);
            profileImage.setClipToOutline(true);
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
        public void setItem(ProfileSettingData image) {
            Glide.with(context).load(image.getImage()).into(profileImage);
        }
        public void setOnItemClickListener (OnItemClickListener listener) {
            this.listener = listener;
        }
    }


}
