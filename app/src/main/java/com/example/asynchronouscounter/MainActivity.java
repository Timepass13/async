package com.example.asynchronouscounter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView bannerTextView;
    private Button startButton, stopButton;
    private boolean isScrolling = false;
    private ScrollTextTask scrollTextTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerTextView = findViewById(R.id.bannerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isScrolling) {
//                    bannerTextView.setVisibility(View.VISIBLE);
                    isScrolling = true;
                    scrollTextTask = new ScrollTextTask();
                    scrollTextTask.execute();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScrolling) {
                    isScrolling = false;
                    scrollTextTask.cancel(true);
//                    bannerTextView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private class ScrollTextTask extends AsyncTask<Void, Integer, Void> {
        private int position = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            while (isScrolling) {
                try {
                    Thread.sleep(5);
                    publishProgress(position+=1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int pos = values[0] % bannerTextView.getWidth();
            bannerTextView.setTranslationX(pos);
        }
    }
}
