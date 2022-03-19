package com.solie.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SingerAdapter(getApplicationContext());

        adapter.addItem(new SingerItem("걸그룹1", "000-0000-0000"));
        adapter.addItem(new SingerItem("걸그룹2", "111-1111-1111"));
        adapter.addItem(new SingerItem("걸그룹3", "222-2222-2222"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SingerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SingerAdapter.ViewHolder holder, View view, int position) {
                SingerItem item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "item 선택됨" + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}