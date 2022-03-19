package com.solie.mrio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solie.mrio.R;
import com.solie.mrio.data.ChattingRoomData;

import java.util.ArrayList;

public class ChattingRoomAdapter extends RecyclerView.Adapter<ChattingRoomAdapter.ViewHolder> {

    Context context;
    ArrayList<ChattingRoomData> items = new ArrayList<>();
    OnItemClickListener listener;
    String text;

    public static interface OnItemClickListener {
        public void onItemClick (ViewHolder holder, View view, int position);
    }

    public ChattingRoomAdapter(Context context) {
        this.context = context;
    }

    public void addItem (ChattingRoomData item) {
        items.add(item);
    }

    public ChattingRoomData getItem (int position) {
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
        View itemView = inflater.inflate(R.layout.chatting_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChattingRoomData item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(listener);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView outputText;
        EditText inputText;
        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            inputText = itemView.findViewById(R.id.chattingRoom_input_text);
            outputText = itemView.findViewById(R.id.chattingRoom_output_text);

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
        public void setItem (ChattingRoomData item) {
            outputText.setText(item.getMessage());

        }
        public void setOnItemClickListener (OnItemClickListener listener) {
            this.listener = listener;
        }
    }
}
