package com.solie.mrio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.solie.mrio.MainActivity;
import com.solie.mrio.OnClickChange;
import com.solie.mrio.R;
import com.solie.mrio.adapter.AcceptListAdapter;
import com.solie.mrio.data.AcceptListData;

public class AcceptFragment extends Fragment {

    TabLayout tabLayout;
    RecyclerView acceptRecycler;
    AcceptListAdapter acceptListAdapter;

    OnClickChange callback;

    public static AcceptFragment newInstance() {
        AcceptFragment fragment = new AcceptFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.accept_fragment, container, false);

        tabLayout = rootView.findViewById(R.id.accept_tab);
        acceptRecycler = rootView.findViewById(R.id.accept_recycler);

        setTabs();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        acceptRecycler.setLayoutManager(layoutManager);
        acceptListAdapter = new AcceptListAdapter(getContext());
        for(int i=0; i<10; i++) {
            acceptListAdapter.addItem(new AcceptListData(R.drawable.ic_launcher_foreground, "이름"));
        }
        acceptRecycler.setAdapter(acceptListAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int id = tab.getPosition();
                if(id == 0) {
                    Toast.makeText(getContext(), "요청온거",Toast.LENGTH_SHORT).show();
                } else if (id == 1) {
                    Toast.makeText(getContext(),"요청보낸거",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),"매칭된거",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       acceptListAdapter.setOnItemClickListener(new AcceptListAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(AcceptListAdapter.ViewHolder holder, View view, int position) {
               Toast.makeText(getContext(),position+"클릭됨",Toast.LENGTH_SHORT).show();
               if(callback != null) {
                   callback.onClickChange(0);
                   MainActivity.acceptOrNot = true;
               }

           }
       });

        return rootView;
    }

    public void setTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("온거"));
        tabLayout.addTab(tabLayout.newTab().setText("보낸거"));
        tabLayout.addTab(tabLayout.newTab().setText("된거"));
    }
}
