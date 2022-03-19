package com.solie.mrio.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solie.mrio.MainActivity;
import com.solie.mrio.R;
import com.solie.mrio.adapter.ChattingRoomAdapter;
import com.solie.mrio.data.ChattingRoomData;

public class ChattingRoomActivity extends AppCompatActivity {

    RecyclerView chatting;
    ChattingRoomAdapter chattingRoomAdapter;

    EditText inputText;
    Button sendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_activity);

        setToolbar();
        chatting = findViewById(R.id.chattingRoom_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        chatting.setLayoutManager(layoutManager);
        chattingRoomAdapter = new ChattingRoomAdapter(getApplicationContext());

        chatting.setAdapter(chattingRoomAdapter);

        inputText = findViewById(R.id.chattingRoom_input_text);
        sendButton = findViewById(R.id.chattingRoom_input_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();
                chattingRoomAdapter.addItem(new ChattingRoomData(text));
                chattingRoomAdapter.notifyDataSetChanged();
                inputText.setText("");
            }
        });


    }
    public void setToolbar() {
        MainActivity.toolbarImage.setImageResource(R.drawable.ic_launcher_foreground);
        MainActivity.toolbarText.setText("채팅방이름");
    }
}
