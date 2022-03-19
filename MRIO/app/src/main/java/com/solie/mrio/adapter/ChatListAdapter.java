package com.solie.mrio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solie.mrio.R;
import com.solie.mrio.data.ChatListData;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter <ChatListAdapter.ViewHolder> {

    Context context;
    ArrayList<ChatListData> items = new ArrayList<>();

    OnItemClickListener listener;

    public ChatListAdapter(Context context) {
        this.context = context;
    }

    public static interface OnItemClickListener {
        public void onItemClick (ViewHolder holder, View view, int position);
    }

    public void addItem (ChatListData item) {
        items.add(item);
    }

    public ChatListData getItem (int position) {
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
        View itemView = inflater.inflate(R.layout.chatlist_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatListData item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(listener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView listProfile;
        TextView listName, listTime, listContent;
        OnItemClickListener listener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listProfile = itemView.findViewById(R.id.chatListProfile);
            listName = itemView.findViewById(R.id.chatListName);
            listTime = itemView.findViewById(R.id.chatListTime);
            listContent = itemView.findViewById(R.id.chatListContent);

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

        public void setItem(ChatListData item) {
            listProfile.setImageResource(item.getListImage());
            listName.setText(item.getListName());
            listTime.setText(item.getListTime());
            listContent.setText(item.getListContent());
        }

        public void setOnItemClickListener (OnItemClickListener listener) {
            this.listener = listener;
        }
    }
}
