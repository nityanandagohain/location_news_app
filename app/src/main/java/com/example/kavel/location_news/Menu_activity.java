package com.example.kavel.location_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu_activity extends AppCompatActivity {

    public void switchToMapsActivity(View view){

        Intent intent = new Intent(getApplicationContext(), MapsActivity_main.class);
        startActivity(intent);
    }

    public void switchToLocalNewsActivity(View view){

        Intent intent = new Intent(getApplicationContext(), currentLocationNews.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activity);

    }
}
