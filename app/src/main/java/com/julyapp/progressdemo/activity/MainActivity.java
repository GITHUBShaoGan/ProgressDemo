package com.julyapp.progressdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.julyapp.progressdemo.R;
import com.julyapp.progressdemo.view.ArcProgressView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private float progress = 0.0f;
    private ArcProgressView arcProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        progressStart();
    }

    private void initView() {
        arcProgressView = (ArcProgressView) findViewById(R.id.progress_arc);
    }

    private void progressStart() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                progress += 0.1;
                arcProgressView.setProgress(progress);
                if (progress >= 100) {
                    progress = 0;
                }
            }
        };
        timer.schedule(task, 0, 10);
    }
}
