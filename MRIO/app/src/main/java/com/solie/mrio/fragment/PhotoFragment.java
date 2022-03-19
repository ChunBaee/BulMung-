package com.solie.mrio.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.solie.mrio.R;

public class PhotoFragment extends Fragment implements View.OnClickListener{

    ImageView photo;
    CardView information;


    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.photo_fragment,container,false);

        photo = rootView.findViewById(R.id.find_photo);
        information = rootView.findViewById(R.id.information_View);
        photo.setImageResource(R.drawable.ic_launcher_foreground);

        photo.setOnClickListener(this);
        information.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_photo:
                if(information.getVisibility() == View.INVISIBLE) {
                    information.setVisibility(View.VISIBLE);
                    information.setClickable(true);
                } else {
                    information.setVisibility(View.INVISIBLE);
                    information.setClickable(false);
                }
                break;

            case R.id.information_View:
                if(information.getVisibility() == View.VISIBLE) {
                    information.setVisibility(View.INVISIBLE);
                    information.setClickable(false);
                }
                break;
        }

    }
}
