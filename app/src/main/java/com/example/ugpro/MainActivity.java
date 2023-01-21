package com.example.ugpro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.ugpro.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gmap;
    boolean isPermissionGranted;
    private int latLongSource = 0;//Donoa ramani
    private int GPS_REQUEST_CODE = 900;

    private ActivityMainBinding activityMainBinding;
    private SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


        activityMainBinding.btnChora.setOnClickListener(v -> {
            latLongSource = 1;
        });
        activityMainBinding.btnGps.setOnClickListener(v -> {
            latLongSource = 0;
        });


    }




    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        gmap.setOnMapClickListener(latLng -> {
            if (latLongSource==1){
                chora(latLng);
            }
        });

    }



    private List<LatLng> markerList = new ArrayList<>();
    private List<Polygon> polygonList = new ArrayList<>();

    protected void chora(LatLng latLng){
        //1. maka
        MarkerOptions mkOption = new MarkerOptions().position(latLng);
        gmap.addMarker(mkOption);

        markerList.add(latLng);

        //2. futa kama kulikuwa na polygon kabla ya kuongeza polygon
        for (Polygon p : polygonList) {
            p.remove();
        }

        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(markerList);

        Polygon polygon = gmap.addPolygon(polygonOptions);
        polygonList.add(polygon);





    }

}