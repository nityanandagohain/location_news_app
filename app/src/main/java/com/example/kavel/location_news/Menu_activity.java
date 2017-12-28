package com.example.kavel.location_news;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Menu_activity extends AppCompatActivity {

    LocationManager locManager;
    boolean network_enabled;
    Location location;
    Intent intent;

    public void switchToMapsActivity(View view){

        intent = new Intent(getApplicationContext(), MapsActivity_main.class);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void switchToLocalNewsActivity(View view) {

        intent = new Intent(getApplicationContext(), News_Display.class);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (address != null && address.size() > 0) {

                String subAdminArea = address.get(0).getSubAdminArea();
                String AdminArea = address.get(0).getAdminArea();

                Intent intent = new Intent(getApplicationContext(), News_Display.class);
                Bundle bundle = new Bundle();
                bundle.putString("AdminArea", AdminArea);
                bundle.putString("subAdminArea", subAdminArea);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (network_enabled) {

                    location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {

                        Log.i("CurrentNetworkLocation.", location.toString());


                    }

                }

            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activity);

        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        else {

            if(network_enabled){

                location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if(location!=null){

                    Log.i("CurrentNetworkLocation.", location.toString());
                }
            }

        }


    }
}
