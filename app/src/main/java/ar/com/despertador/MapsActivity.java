package ar.com.despertador;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import ar.com.despertador.dialogos.Configurar_ContactoActivity;
import ar.com.despertador.dialogos.DialogoConfigurarRadioFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

//    static final int PICK_CONTACT_REQUEST=1;

    private GoogleMap mMap;
    private Marker marcador;
    LocationManager locationManager;
    String provider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Button btnGPSShowLocation;
    double lat = 0.0;
    double log = 0.0;
    //private ActivityMapsBinding binding;
    SupportMapFragment mapFragment;
    SearchView searchView;
  //  LatLng posactual = new LatLng(lat,log);
    Location posactual = new Location("localizacion Usuario");
    Location posdestino = new Location("localizacion Destino");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        checkLocationPermission();

        searchView = findViewById(R.id.sv_ubicacion);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    posdestino.setLatitude(address.getLatitude());
                    posdestino.setLongitude(address.getLongitude());
                    float dist = posactual.distanceTo(posdestino);
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    mMap.addCircle(new CircleOptions()
                            .center(latLng)
                            .radius(300)
                            .strokeColor(Color.GRAY)
                            .fillColor(Color.CYAN));
                    Toast.makeText(MapsActivity.this, "LA DISTANCIA AL DESTINO ES: " + dist + " Metros.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
        //binding = ActivityMapsBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        String emailU = getIntent().getStringExtra("email");
        //  Toast.makeText(this, "Usuario logueado " + emailU, Toast.LENGTH_SHORT).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton AvisaraContacto = (FloatingActionButton) findViewById(R.id.AvisaraContacto);
        AvisaraContacto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AbrirDialogoCofContacto();
            }
        });
        FloatingActionButton aplicarRadio = (FloatingActionButton) findViewById(R.id.DefinirDistancia);
        aplicarRadio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AbrirDialogoConfigRadio();
            }
        });
//        GoogleMap
//        mMap=GoogleMap;
        btnGPSShowLocation = (Button) findViewById(R.id.btnGPSShowLocation);
        btnGPSShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                miUbucacion();
            }
        });

    }

    private void agregarMarcador(double lat, double log) {
        LatLng coordenadas = new LatLng(lat, log);
        CameraUpdate miUbucacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        mMap.clear();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi Posicion Actual")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo2)).anchor(0.0f, 1.04f));
        mMap.animateCamera(miUbucacion);

    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            log = location.getLongitude();
posactual.setLongitude(log);
posactual.setLatitude(lat);
          //  posactual.se = new LatLng(lat,log);
            agregarMarcador(lat, log);
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    };

    private void miUbucacion() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            new AlertDialog.Builder(this)
                    .setTitle("Aprobacion de permisos de ubicacion")
                    .setMessage("Por favor habilite el permiso de ubicacion para la aplicacion")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .create()
                    .show();

           return;
        }


        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);

    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Aprobacion de permisos de ubicacion")
                        .setMessage("Por favor habilite el permiso de ubicacion para la aplicacion")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
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
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        CameraUpdate center=
//                CameraUpdateFactory.newLatLng(new LatLng(40.76793169992044,
//                        -73.98180484771729));
//        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
//        mMap.moveCamera(center);
//        mMap.animateCamera(zoom);
        miUbucacion();
    }

    public void AbrirDialogoCofContacto() {
        //comoento para probar activity seleecionar contactos
        /*DialogoConfigurarContactoFragment dialogo = new DialogoConfigurarContactoFragment();
        dialogo.show(getSupportFragmentManager(), "Dialogo Configurar Contacto");*/
        Intent intent = new Intent(this, Configurar_ContactoActivity.class);
        startActivity(intent);


        /*
        //prueba
        Intent selectContactoIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contactas"));
        selectContactoIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(selectContactoIntent,PICK_CONTACT_REQUEST);
         */
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK)
            {
                Uri uri = data.getData();

                Cursor cursor= getContentResolver().query(uri,null,null,null,null);

                if (cursor.moveToFirst()){
                    int columnaNombre=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int columnaNumero=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String nombre = cursor.getString(columnaNombre);
                    String numero = cursor.getString(columnaNumero);
                    Toast.makeText(this,"Registro Seleccionado: " + nombre + " Celular: " + numero, Toast.LENGTH_SHORT).show();
                    //grabar los datos del contacto seleccionado en la base


                    //Voy a configurar Alarma
                    Intent intent=new Intent(this, ConfiguracionAlarmaActivity.class);
                    startActivity(intent);

                }
            }
        }
    }
    */
    public void AbrirDialogoConfigRadio() {

        DialogoConfigurarRadioFragment dialogoRadio = new DialogoConfigurarRadioFragment();
        dialogoRadio.show(getSupportFragmentManager(), "Dialogo Configurar Radio");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}