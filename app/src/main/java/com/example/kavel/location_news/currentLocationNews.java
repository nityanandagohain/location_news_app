package com.example.kavel.location_news;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class currentLocationNews extends AppCompatActivity {
    LocationManager locator;
    LocationListener locationListener;
    Location currentLocation;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                currentLocation = locator.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> address = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                    if(address != null && address.size() > 0){

                        Toast.makeText(currentLocationNews.this, "The area is: " + address.get(0).getSubAdminArea(), Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location_news);

        locator = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        else
        {

            currentLocation = locator.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> address = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                if(address != null && address.size() > 0){

                    Toast.makeText(currentLocationNews.this, "The area is: " + address.get(0).getSubAdminArea(), Toast.LENGTH_SHORT).show();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
