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
import com.solie.mrio.data.AcceptListData;

import java.util.ArrayList;

public class AcceptListAdapter extends RecyclerView.Adapter<AcceptListAdapter.ViewHolder> {

    Context context;
    ArrayList<AcceptListData> items = new ArrayList<>();

   OnItemClickListener listener;

   public static interface OnItemClickListener {
       public void onItemClick (ViewHolder holder, View view, int position);
   }

    public AcceptListAdapter(Context context) {
        this.context = context;
    }

    public void addItem (AcceptListData item) {
       items.add(item);
    }

    public AcceptListData getItem(int position) {
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
        View itemView = inflater.inflate(R.layout.accept_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       AcceptListData item = items.get(position);
       holder.setItem(item);

       holder.setOnItemClickListener(listener);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       ImageView acceptImage;
       TextView acceptName;
       OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            acceptImage = itemView.findViewById(R.id.accept_profile);
            acceptName = itemView.findViewById(R.id.accept_name);

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
        public void setItem(AcceptListData item) {
            acceptImage.setImageResource(item.getListImage());
            acceptName.setText(item.getListName());

        }
        public void setOnItemClickListener (OnItemClickListener listener) {
            this.listener = listener;
        }
    }


}
