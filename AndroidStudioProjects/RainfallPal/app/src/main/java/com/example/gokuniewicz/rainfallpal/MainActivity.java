package com.example.gokuniewicz.rainfallpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, WeatherService.class);
        intent.putExtra("lon", "longitude");
        intent.putExtra("lat", "latitude");
        Log.d(TAG, "Starting service...");
        startService(intent);
    }
}
