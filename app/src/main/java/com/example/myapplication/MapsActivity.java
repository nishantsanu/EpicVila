package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    //on submitting location
    public void submitLocation(View view){
        Log.i("Info","Location Submitted");
        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
        startActivity(intent);
    }

    //update the location on google map
    public void updateLocation(@NonNull Location location){
        mMap.clear();
        LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(userLocation, 15)));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Log.i("Location","location permission granted");
                    Location lastKnownLocation=getLastKnownLocation();
                    mMap.clear();
                    if(lastKnownLocation!=null){
                        LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(userLocation, 15)));
                    }

                }
            }
        }
    }
//   funtion to get last known location
private Location getLastKnownLocation() {
    List<String> providers = locationManager.getProviders(true);
    Location bestLocation = null;
    for (String provider : providers) {
        Location l = locationManager.getLastKnownLocation(provider);
        if (l == null) {
            continue;
        }
        if (bestLocation == null
                || l.getAccuracy() < bestLocation.getAccuracy()) {
            bestLocation = l;
        }
    }
    if (bestLocation == null) {
        return null;
    }
    return bestLocation;
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);
        mapFragment.getMapAsync(this);
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
        //set satellite view
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //

        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("Location","location changed");
                updateLocation(location);
            }
            @Override
            public void onStatusChanged(String s,int i,Bundle bundle){

            }
            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(Build.VERSION.SDK_INT<23){
            Log.i("Build version","less than 23");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }else{
            Log.i("Build version","greater than 23");
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                //requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation=getLastKnownLocation();
                if(lastKnownLocation!=null){
                    LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                    mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(userLocation, 15)));
                }

            }

        }

    }
}