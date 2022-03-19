package com.solie.firebaseexam.realtimedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solie.firebaseexam.R;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter <MemoAdapter.ViewHolder> {

    private Context context = null;
    private ArrayList<MemoItem> memoItems = null;
    private MemoViewListener memoViewListener = null;

    public MemoAdapter(Context context, ArrayList<MemoItem> memoItems, MemoViewListener memoViewListener) {
        this.context = context;
        this.memoItems = memoItems;
        this.memoViewListener = memoViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item_list, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        holder.titleView.setText(memoItems.get(position).getMemoTitle());
        holder.contentsView.setText(memoItems.get(position).getMemoContents());
    }

    @Override
    public int getItemCount() {
        return memoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleView = null;
        public TextView contentsView = null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.memoTitle);
            contentsView = itemView.findViewById(R.id.memoContents);
        }

        @Override
        public void onClick(View v) {
            memoViewListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
