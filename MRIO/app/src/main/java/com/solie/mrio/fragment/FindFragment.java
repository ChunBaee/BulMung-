package com.solie.mrio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.solie.mrio.MainActivity;
import com.solie.mrio.OnClickChange;
import com.solie.mrio.R;
import com.solie.mrio.adapter.PagerAdapter;

public class FindFragment extends Fragment implements View.OnClickListener{

    ViewPager photos;
    Button yes, no;

    OnClickChange callback;

    PagerAdapter pagerAdapter;
    PhotoFragment[] photoFragment;

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnClickChange) {
            callback = (OnClickChange) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.find_fragment,container,false);

        photos = rootView.findViewById(R.id.find_pager);
        yes = rootView.findViewById(R.id.find_button_yes);
        no = rootView.findViewById(R.id.find_button_no);

        photoFragment = new PhotoFragment[5];

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        for(int roof=0; roof<5; roof++) {
            photoFragment[roof] = PhotoFragment.newInstance();
        }

        for(PhotoFragment fragment : photoFragment) {
            pagerAdapter.addItem(fragment);
        }

        photos.setOffscreenPageLimit(0);
        photos.setAdapter(pagerAdapter);


        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_button_yes:
                Toast.makeText(getContext(), "신청완료",Toast.LENGTH_SHORT).show();
                if(MainActivity.acceptOrNot) {
                    if(callback != null) {
                        callback.onClickChange(1);
                    }
                }
                MainActivity.acceptOrNot = false;
                break;

            case R.id.find_button_no:
                Toast.makeText(getContext(),"거절완료",Toast.LENGTH_SHORT).show();
                if(MainActivity.acceptOrNot) {
                    if(callback != null) {
                        callback.onClickChange(1);
                    }
                }
                MainActivity.acceptOrNot = false;
                break;

            default:
                break;

        }
    }
}
