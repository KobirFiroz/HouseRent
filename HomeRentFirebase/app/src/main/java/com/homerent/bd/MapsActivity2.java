package com.homerent.bd;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    // new code
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 6.6f;

    @Override
    public void onMapReady(GoogleMap googleMap) {
                //  Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onMapReady: map is ready");
                mMap = googleMap;

                if (mLocationPermissionsGranted) {

                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);

                    geoLocate();
        }
    }


    private static final String TAG = "MapsActivity2";



    //vars
    private Boolean mLocationPermissionsGranted = true;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //new end


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        getDeviceLocation();

       // LatLng you = new LatLng(MapsActivity.lat1, MapsActivity.lng1);
       // Toast.makeText(this, you.toString(), Toast.LENGTH_SHORT).show();
       // mMap.addMarker(new MarkerOptions().position(you).title("Your Position"));
       //      mMap.addMarker(new MarkerOptions().position(you).title("Your Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
      //  mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(you , 6.6f) );


        for(int i = 0; i< MainActivity.donorInfo.size(); i++){


            Upload donor = MainActivity.donorInfo.get(i);
            String lat1= donor.lat;
            String lng1= donor.lng;
            Double lat = new Double(lat1);
            Double lng = new Double(lng1);




            LatLng donar = new LatLng(lat, lng);
          //  Toast.makeText(this, donar.toString(), Toast.LENGTH_SHORT).show();

            String donorName = donor.imgUpo + " , "+donor.imgDiv;
            String details = donor.phone+" , "+donor.imgName;
            mMap.addMarker(new MarkerOptions().position(donar).title(donorName).snippet(details).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).showInfoWindow();
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(donar , 6.6f) );

            Geocoder geocoder = new Geocoder(MapsActivity2.this);
            List<Address> list = new ArrayList<>();
            try{
                list = geocoder.getFromLocationName(donorName, 1);

            }catch (IOException e){
                Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
            }

            if(list.size() > 0){
                Address address = list.get(0);

                Log.d(TAG, "geoLocate: found a location: " + address.toString());
                //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();


            }
        }

    }

    public  void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            LatLng y =new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                            mMap.addMarker(new MarkerOptions().position(y).title("Your Position"));
                            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(y , 6.6f) );
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity2.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }




    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity2.this);
    }




}