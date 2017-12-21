package com.example.kavel.location_news;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by kavel on 19/12/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("info", "splash starting");
        Intent intent = new Intent(this, MapsActivity_main.class);
        startActivity(intent);
        finish();
    }
}

