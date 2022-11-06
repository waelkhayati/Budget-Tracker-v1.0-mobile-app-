package com.example.BudgetTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                Toast toast = Toast.makeText(SplashActivity.this, "Welcome to your wallet", Toast.LENGTH_SHORT);
                toast.show();
                mediaPlayer[0] = MediaPlayer.create(getApplicationContext(), R.raw.chaching);

                mediaPlayer[0].start();
            }

        }, 3000);


    }
}