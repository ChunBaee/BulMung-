package com.solie.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Drawable> imageList = new ArrayList<>();
    ImageView imageView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        imageList.add(res.getDrawable(android.R.drawable.star_big_off));
        imageList.add(res.getDrawable(android.R.drawable.star_big_on));

        imageView = findViewById(R.id.imageView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimThread thread = new AnimThread();
                thread.start();
            }
        });
    }

    class AnimThread extends Thread {
        public void run() {
            int index = 0;
            for (int i=0; i < 100; i++) {
                index = i % 2;
                final Drawable drawable = imageList.get(index);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageDrawable(drawable);
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}