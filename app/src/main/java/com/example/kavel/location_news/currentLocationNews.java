package com.example.kavel.location_news;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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

    LocationManager locationManager;
    LocationListener locationListener;
    Location currentLocation = null;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);

               /*Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> address = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                    if(address != null && address.size() > 0){

                        Toast.makeText(currentLocationNews.this, "The area is: " + address.get(0).getSubAdminArea(), Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location_news);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                currentLocation = location;

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        }

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {

            while (currentLocation == null){

                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            List<Address> address = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            if (address != null && address.size() > 0) {

                Toast.makeText(currentLocationNews.this, "The area is: " + address.get(0).getSubAdminArea(), Toast.LENGTH_SHORT).show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
    }

