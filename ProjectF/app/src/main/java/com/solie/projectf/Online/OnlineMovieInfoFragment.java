package com.solie.projectf.Online;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class OnlineMovieInfoFragment extends Fragment {

    public static OnlineMovieInfoFragment newInstance (int roof) {
        OnlineMovieInfoFragment fragment = new OnlineMovieInfoFragment();
        Bundle args = new Bundle();
        args.putInt("bundle",roof);
        fragment.setArguments(args);
        return fragment;
    }

}
