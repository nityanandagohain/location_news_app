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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity_main extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locator;
    Location currentLocation;
    Intent receiver;
    LatLng clickedLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        receiver = getIntent();
        currentLocation = receiver.getParcelableExtra("location");
        Log.i("CurrentLocation", currentLocation.toString());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()));
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        marker.title("Current Position");
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),10));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                clickedLatLng = point;
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> address = geocoder.getFromLocation(clickedLatLng.latitude, clickedLatLng.longitude,1);
                    if(address != null && address.size() > 0){

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


                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point));
            }
        });
    }
}
