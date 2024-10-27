package com.example.filedownloading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private Button btnDownload;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private Handler mainHandler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = findViewById(R.id.btn_download);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);
        mainHandler = new Handler(Looper.getMainLooper());
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
            }
        });
    }
    private void startDownload() {
        progressBar.setVisibility(View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        tvProgress.setText("0%");

        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadFile();
            }
        }).start();
    }
    private void downloadFile() {
        try {
            int fileLength = 100;
            for (int i = 1; i <= fileLength; i++) {
                Thread.sleep(50); // Simulate time taken to download part of the file
                int progress = i;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                        tvProgress.setText(progress + "%");
                        if (progress == 100) {
                            Toast.makeText(MainActivity.this, "Download Complete", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            tvProgress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}