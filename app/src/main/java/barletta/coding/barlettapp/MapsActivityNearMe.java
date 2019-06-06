package barletta.coding.barlettapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Iterator;

import barletta.coding.barlettapp.Fragment.CategoryListActivity;
import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.util.MySingleton;
import barletta.coding.barlettapp.util.findLocal;

public class MapsActivityNearMe extends FragmentActivity implements OnMapReadyCallback{

    //41.320391, 16.270859 NANULA
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = false;
    private final static int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    private final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
    FusedLocationProviderClient mFusedLoc;
    LatLng myCurrentPosition;
    public ArrayList<LatLng> markerToShowLat = new ArrayList<>();
    private findLocal localHelper = new findLocal();
    private ImageLoader imgLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        imgLoader = MySingleton.getInstance(this).getImageLoader();
        checkMapServices();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLoc = LocationServices.getFusedLocationProviderClient(this);

    }

    public boolean isServicesOK() {


        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivityNearMe.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            return true;
        }

        return false;
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {

                } else {
                    getLocationPermission();
                }
            }
        }

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Task<Location> taskForLocal = mFusedLoc.getLastLocation();
        taskForLocal.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    myCurrentPosition = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myCurrentPosition));
                    Toast.makeText(getApplicationContext(),"!= NULL",Toast.LENGTH_LONG).show();
                    mMap.addMarker(new MarkerOptions().position(myCurrentPosition).title("CASA MIA"));


                }
            }
        });
        //SETTARE LA INFO WINDOW
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }


            //SERVE A MODIFICARE LE INFO WINDOWS, USO IL LAYOUT info_windows_adapter, QUINDI LE MODIFICHE VANNO FATTE ANCHE LA
            @Override
            public View getInfoContents(Marker marker) {
                //Prendiamo la posizione dal marker, che poi uso per il controllo degli oggetti
                LatLng latLng = marker.getPosition();
                Locale temp = localHelper.findLocalWithCoord(latLng.latitude,latLng.longitude);
                View v = getLayoutInflater().inflate(R.layout.info_windows_adapter,null);
                TextView localName = v.findViewById(R.id.textViewInfoWindowName);
                localName.setText(temp.getNome());
                TextView localDescrption = v.findViewById(R.id.textViewInfoWindowDescrizione);
                localDescrption.setText(temp.getDescrizione());
                ImageView immagineLocale = v.findViewById(R.id.imageViewInfoWindow);
                imgLoader.get(temp.getImmagine(), ImageLoader.getImageListener(immagineLocale, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
                return v;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                Locale temp = localHelper.findLocalWithCoord(latLng.latitude,latLng.longitude);
                //Chiamo la HomeActivty, con un extra, e da li apro il OpenLocalFragment con questo locale temp
                Intent intet = new Intent(MapsActivityNearMe.this,HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Locale", temp);
                bundle.putBoolean("Check",true);
                intet.putExtra("Bundle",bundle);
                startActivity(intet);

            }
        });


        Iterator<Locale> localeIterator = CategoryListActivity.listToShow.iterator();
        while (localeIterator.hasNext()){
            Locale currentLocal = localeIterator.next();
            mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocal.getLatitude(),currentLocal.getLongitude())).title(currentLocal.getNome()));
        }


        mMap.setMaxZoomPreference(18.0f);
        mMap.setMinZoomPreference(16.0f);

    }

    public void createMarkerForTheMap(){
        Iterator<Locale> localeIterator = CategoryListActivity.listToShow.iterator();
        while (localeIterator.hasNext()){
            Locale current = localeIterator.next();
            markerToShowLat.add(new LatLng(current.getLatitude(),current.getLongitude()));
        }
    }





}
