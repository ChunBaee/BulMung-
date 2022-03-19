package com.solie.firebaseexam.realtimedb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solie.firebaseexam.R;

import java.util.ArrayList;
import java.util.Random;

public class MemoActivity extends AppCompatActivity implements View.OnClickListener  , MemoViewListener{

    private ArrayList<MemoItem> memoItems = null;
    private  MemoAdapter memoAdapter = null;
    private String userName = null;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        init();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        addChildEvent();
        addValueEventListener();
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.memoBtn:
                regMemo();
                break;

            case R.id.regUser:
                WriteNewUser();
                break;
        }
    }

    private void init() {
        memoItems = new ArrayList<>();
        userName = "user_" + new Random().nextInt(1000);
    }

    private void initView() {
        Button regBtn = findViewById(R.id.memoBtn);
        regBtn.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.memoList);
        memoAdapter = new MemoAdapter(this,memoItems, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memoAdapter);

        Button userBtn = findViewById(R.id.regUser);
        userBtn.setOnClickListener(this);
    }

    private void regMemo() {
        EditText titleEdit = findViewById(R.id.memoTitle);
        EditText contentsEdit = findViewById(R.id.memoContents);

        if(titleEdit.getText().toString().length() == 0 || contentsEdit.getText().toString().length() == 0) {
            Toast.makeText(this,"메모 제목 또는 메모 내용이 입력되지 않았습니다. 입력 후 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        MemoItem item = new MemoItem();
        item.setUser(this.userName);
        item.setMemoTitle(titleEdit.getText().toString());
        item.setMemoContents(contentsEdit.getText().toString());

        databaseReference.child("memo").push().setValue(item);
    }

   private void addChildEvent() {
        databaseReference.child("memo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MemoItem item = snapshot.getValue(MemoItem.class);

                memoItems.add(item);
                memoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void WriteNewUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserPwd("12345");
        userInfo.setUserName("홍길순");
        userInfo.setEmailAddr("userEmail1@mail.com");

        databaseReference.child("users").child("user").setValue(userInfo);
    }

    private void addValueEventListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Database","Value = " + snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}