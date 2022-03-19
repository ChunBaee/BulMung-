package com.solie.audiorecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static String url = "https://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaRecorder recorder;
    String filename;

    MediaPlayer player;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "recorded.mp4");
        filename = file.getAbsolutePath();
        Log.d("MainActiity", "저장할 파일명 : " + filename);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAudio();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeAudio();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAudio();
            }
        });

        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });
    }

    public void recordAudio() {
        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(filename);

        try {
            recorder.prepare();
            recorder.start();
            Toast.makeText(this,"녹음 시작됨",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;

            Toast.makeText(this,"녹음 중지됨",Toast.LENGTH_SHORT).show();
        }
    }

    public void playAudio() {
        try {
            closePlayer();

            player = new MediaPlayer();
            player.setDataSource(filename);
            //player.setDataSource(url);
            // player.prepare();
            player.start();

            Toast.makeText(this, "재생 시작됨", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseAudio () {
        if(player != null) {
            position = player.getCurrentPosition();
            player.pause();
            Toast.makeText(this, "일시 정지됨", Toast.LENGTH_SHORT).show();
        }
    }

    public void resumeAudio() {
        if(player != null && !player.isPlaying()) {
            player.seekTo(position);
            player.start();
        }
        Toast.makeText(this, "재시작됨", Toast.LENGTH_SHORT).show();
    }

    public void stopAudio() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
        Toast.makeText(this, "재생 종료됨", Toast.LENGTH_SHORT).show();

    }

    public void closePlayer() {
        if(player != null) {
            player.release();
            player = null;
        }
    }


}