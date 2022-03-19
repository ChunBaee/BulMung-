package com.solie.mydatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    TextView textView;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        editText2 = findViewById(R.id.editTextTextPersonName2);
        editText3 = findViewById(R.id.editTextTextPersonName3);
        editText4 = findViewById(R.id.editTextTextPersonName4);
        editText5 = findViewById(R.id.editTextTextPersonName5);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String databaseName = editText.getText().toString();
                openDatabase(databaseName);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();
                createTable(tableName);
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText3.getText().toString().trim();
                String agestr = editText4.getText().toString().trim();
                String mobile = editText5.getText().toString().trim();

                int age = -1;
                try {
                    age = Integer.parseInt(agestr);
                } catch (Exception e) {

                }

                insertData(name, age, mobile);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();
                selectData(tableName);
            }
        });
    }

    public void openDatabase(String databaseName) {

        textView.append("openDatabase() 호출됨\n");
       /* database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        if (database != null) {
            textView.append("데이터베이스 오픈됨\n");
        } */

        DatabaseHelper helper = new DatabaseHelper(this, databaseName, null, 2);
        database = helper.getWritableDatabase();
        textView.append("여기까지 들어오네요");

    }

    public void createTable(String tableName) {

        textView.append("createTable() 호출됨\n");

        if (database != null) {

            String sql = "create table if not exists " + tableName + "(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)";
            //String sql = "drop table if exists " + tableName;
            database.execSQL(sql);

            textView.append("테이블 생성됨\n");
        } else {
            textView.append("먼저 데이터베이스를 오픈하세요\n");
        }
    }

    public void insertData(String name, int age, String mobile) {
        textView.append("insertData() 호출됨\n");
        if (database != null) {
            String sql = "insert into customer(name, age, mobile) values(?, ?, ?)";
            Object[] params = {name, age, mobile};

            database.execSQL(sql, params);
            textView.append("데이터 추가함\n");
        } else {
            textView.append("먼저 데이터베이스를 오픈하세요\n");
        }
    }

    public void selectData(String tableName) {
        textView.append("selectData() 호출됨\n");

        if (database != null) {
            String sql = "select name, age, mobile from " + tableName;
            Cursor cursor = database.rawQuery(sql, null);
            textView.append("조회된 데이터 개수 : " + cursor.getCount() + "\n");

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                textView.append("#" + i + " -> " + name + "," + age + "," + mobile + "\n");
            }

            cursor.close();
        }
    }

    class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            onCreate(database);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            textView.append("onCreate() 호출됨\n");
            String tableName = "customer";
            if (db != null) {

                String sql = "create table if not exists " + tableName + "(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)";
                db.execSQL(sql);

                textView.append("테이블 생성됨\n");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            textView.append("onUpgrade() 호출됨 : old : " + oldVersion + " , new : " + newVersion + "\n");

            if(newVersion > 1) {
                String tableName = "customer";
                db.execSQL("drop table if exists " + tableName);

                textView.append("테이블 삭제됨\n");

                String sql = "create table " + tableName + "(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)";
                database.execSQL(sql);

                textView.append("테이블 생성됨\n");
            }


        }
    }

}