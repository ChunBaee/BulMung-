package com.solie.mrio.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solie.mrio.MainActivity;
import com.solie.mrio.R;
import com.solie.mrio.activity.ChattingRoomActivity;
import com.solie.mrio.adapter.ChatListAdapter;
import com.solie.mrio.data.ChatListData;

public class ChatListFragment extends Fragment {

    RecyclerView chatList;
    ChatListAdapter chatListAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity.toolbarText.setText("");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.chatlist_fragment, container, false);

        chatList = rootView.findViewById(R.id.chatList_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(layoutManager);
        chatListAdapter = new ChatListAdapter(getContext());

        for (int i = 0; i < 10; i++) {
            chatListAdapter.addItem(new ChatListData(R.drawable.ic_launcher_foreground, "이름", "시간", "대화내용"));
        }

        chatList.setAdapter(chatListAdapter);

        chatListAdapter.setOnItemClickListener(new ChatListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChatListAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), ChattingRoomActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
