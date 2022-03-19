package com.solie.firebaseexam.cloudstorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.$Gson$Preconditions;
import com.solie.firebaseexam.R;

import java.util.HashMap;
import java.util.Map;

public class FireStoreActivity extends AppCompatActivity implements View.OnClickListener {

    String uname = "pN5SfhsxmjSo6EkAn5hT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_store);

        Button addDataBtn = findViewById(R.id.fireStoreAddDataBtn);
        addDataBtn.setOnClickListener(this);

        Button setDataBtn = findViewById(R.id.fireStoreSetDataBtn);
        setDataBtn.setOnClickListener(this);

        Button deleteDocButton = findViewById(R.id.fireStoreDeleteDocBtn);
        deleteDocButton.setOnClickListener(this);

        Button deleteFieldBtn = findViewById(R.id.fireStoreDeleteFieldBtn);
        deleteFieldBtn.setOnClickListener(this);

        Button selectDocBtn = findViewById(R.id.fireStoreSelectDataBtn);
        selectDocBtn.setOnClickListener(this);

        Button selectWhereDocBtn = findViewById(R.id.fireStoreSelWhereDataBtn);
        selectWhereDocBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fireStoreAddDataBtn:
                addData();
                break;

            case R.id.fireStoreSetDataBtn:
                setData();
                break;

            case R.id.fireStoreDeleteDocBtn:
                deleteDoc();

            case R.id.fireStoreDeleteFieldBtn:
                deleteField();

            case R.id.fireStoreSelectDataBtn:
                selectDoc();

            case R.id.fireStoreSelWhereDataBtn:
                selectWhereDoc();

            default:
                break;
        }
    }

    private void addData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> member = new HashMap<>();
        member.put("name", "홍길동");
        member.put("address", "수원시");
        member.put("age", 25);
        member.put("id", "hong");
        member.put("pwd", "hello!");

        db.collection("users")
                .add(member)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Document ID = " + documentReference.get(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Document Error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> member = new HashMap<>();
        member.put("name", "나야나");
        member.put("address", "경기도");
        member.put("age", 25);
        member.put("id", "my");
        member.put("pwd", "hello2");

        db.collection("users")
                .document("userInfo")
                .set(member)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "DocumentSnapshot successfully Written", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Document Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDoc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document("userInfo")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "DocumentSnapshot Successfully Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error deleting Document", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteField() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document("userInfo");

        Map<String, Object> updates = new HashMap<>();
        updates.put("address", FieldValue.delete());

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "DocumentSnapshot Successfully Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectDoc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uname);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getApplicationContext(), "DocumentSnapshot data" + document.getData(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No such Document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Get Failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectWhereDoc() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("age", 30)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "결과는 "+task.getResult().toString()+"입니다.", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(getApplicationContext(), document.getId() + " = > " + document.getData(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error getting Documents : " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}