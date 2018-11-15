package com.example.lukasztrojok.hour6app;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;



     Button homebtn;
    TextView tlong;
    TextView tlat;

    Double latt;
    Double lonn;

    String latti;
    String lonng;



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


        Intent intent = getIntent();

       latt = intent.getDoubleExtra("Lati",0.0);
       lonn = intent.getDoubleExtra("Longi",0.0);


        latti = Double.toString(latt);
        tlong.setText(Double.toString(latt));
        tlat.setText(Double.toString(lonn));
        homebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        Intent startIntent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(startIntent);


    }
});
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

        // Add a marker in Sydney and move the camera
        LatLng car = new LatLng(latt, lonn);
        mMap.addMarker(new MarkerOptions().position(car).title("Your Car"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(car));
    }
}
