package com.solie.myexampleiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.solie.myexampleiot.helper.NetworkStatus;

public class MainActivity extends AppCompatActivity {

    ImageButton powerButton;
    boolean powerOnAndOff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerButton = findViewById(R.id.imageButton);

        NetworkCheck();
    }

    public void NetworkCheck() {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if (status == NetworkStatus.TYPE_NON_CONNECTED) {
            Toast.makeText(getApplicationContext(),"인터넷에 연결되지 않았습니다. 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            powerButton.setClickable(false);
        } else {
            powerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!powerOnAndOff) {
                        powerOnAndOff = true;
                        powerButton.setImageResource(R.drawable.power_on_icon);
                        Toast.makeText(getApplicationContext(),"Power On", Toast.LENGTH_SHORT).show();
                    } else if (powerOnAndOff) {
                        powerOnAndOff = false;
                        powerButton.setImageResource(R.drawable.power_off_icon);
                        Toast.makeText(getApplicationContext(),"Power Off", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}