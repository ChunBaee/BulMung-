package com.solie.mrio.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.solie.mrio.MainActivity;
import com.solie.mrio.R;
import com.solie.mrio.activity.ProfileSettingActivity;

public class SettingFragment extends Fragment implements View.OnClickListener {

    RelativeLayout profileSetting, notifySetting, license, logistics, shop, logout;
    ImageView mainProfileImg;

    private static final int PROFILE_CHANGE = 1500;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_fragment, container, false);

        profileSetting = rootView.findViewById(R.id.profile_setting);
        profileSetting.setOnClickListener(this);

        notifySetting = rootView.findViewById(R.id.notification_setting);
        notifySetting.setOnClickListener(this);

        license = rootView.findViewById(R.id.license_setting);
        license.setOnClickListener(this);

        shop = rootView.findViewById(R.id.shop_setting);
        shop.setOnClickListener(this);

        logistics = rootView.findViewById(R.id.qna_setting);
        logistics.setOnClickListener(this);

        logout = rootView.findViewById(R.id.logout_setting);
        logout.setOnClickListener(this);

        mainProfileImg = rootView.findViewById(R.id.mainProfileImage);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_setting:
                Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                startActivityForResult(intent, PROFILE_CHANGE);
                break;

            case R.id.notification_setting:
                //알림설정
                break;

            case R.id.license_setting:
                //라이센스
                break;

            case R.id.shop_setting:
                //상점
                break;

            case R.id.qna_setting:
                //고객센터
                break;

            case R.id.logout_setting:
                logOut();
        }
    }

    private void logOut() {
        AuthUI.getInstance().signOut(getContext());
        ActivityCompat.finishAffinity(getActivity());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PROFILE_CHANGE) {
            Bitmap ProfileImage = data.getParcelableExtra("MainProfilePic");
            Glide.with(this).load(ProfileImage).into(mainProfileImg);
        }
    }
}
