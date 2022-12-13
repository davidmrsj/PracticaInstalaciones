package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.db;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.practicainstalaciones.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.Console;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Marker ubicaciones[];
    LatLng puntoInicial;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

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

        añadirUbicaciones(googleMap);
        // Add a marker in Sydney and move the camera
        puntoInicial = new LatLng(currentLocation.getLatitude(), -currentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoInicial, 13));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnInfoWindowClickListener(this);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
                break;
        }
    }

    private void getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
            , REQUEST_CODE);
        }

        Task<Location> task=fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;
                    Toast.makeText(getApplicationContext(), (int)currentLocation.getLatitude(), Toast.LENGTH_LONG).show();
                    SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment !=null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }


    public void añadirUbicaciones(GoogleMap googleMap){
        mMap = googleMap;
        Cursor cursor = db.rawQuery("SELECT * FROM instalaciones;", null);
        int contador=0;
        try{
            while(cursor.moveToNext()){
                ubicaciones = new Marker[cursor.getCount()];
                Log.e("InstalacionesUbicacion", cursor.getString(0));
                double altitud = cursor.getFloat(1);
                double latitud = cursor.getFloat(2);
                LatLng ubicacion = new LatLng(altitud, latitud);
                ubicaciones[contador] = mMap.addMarker(new MarkerOptions().position(ubicacion).title(cursor.getString(0)).snippet(cursor.getString(3)).draggable(false));
                ++contador;
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al añadir ubicaciones", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getApplicationContext(), AnadirReserva.class);
        intent.putExtra("Nombre", marker.getTitle());
        intent.putExtra("Tipo pista", marker.getSnippet());
        startActivity(intent);
    }
}