package com.example.lukasztrojok.hour6app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    Button homebtn;
    TextView tlong;
    TextView tlat;
    TextView dataCity;

    Double latt;
    Double lonn;

    String latti;
    String lonng;

    LatLng car;
    Button btnNav;


    File sdcard = Environment.getExternalStorageDirectory();
    File file = new File(sdcard, "findCar/findMyCar.txt");


    String wholeText;
    String[] wholeTextArray;


    Double clong;
    Double clat;

    LatLng you;

    StringBuilder text = new StringBuilder();
    LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        tlong = (TextView) findViewById(R.id.getLon);
        tlat = (TextView) findViewById(R.id.getLat);
        homebtn = (Button) findViewById(R.id.btn_home);

        btnNav = (Button) findViewById(R.id.btn_nav);


        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');


            }
            br.close();


        } catch (IOException e) {

            //  Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();   //make toast
        }


        wholeText = text.toString();


        wholeTextArray = wholeText.split("/");


        lonn = Double.parseDouble(wholeTextArray[1]);
        latt = Double.parseDouble(wholeTextArray[2]);

        tlong.setText(Double.toString(lonn));
        tlat.setText(Double.toString(latt));


        dataCity = (TextView) findViewById(R.id.dataCity);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latt, lonn, 1);
            String cityName = addresses.get(0).getAddressLine(0);

            dataCity.setText(cityName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent startIntent = new Intent(getApplicationContext(), CoolMenu.class);
                startActivity(startIntent);


            }
        });

        car = new LatLng(latt, lonn);


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng car = new LatLng(latt, lonn);
        mMap.addMarker(new MarkerOptions().position(car).title("Your Car").icon(BitmapDescriptorFactory.fromResource(R.drawable.smallpin)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(car, 18));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);




    }


}
