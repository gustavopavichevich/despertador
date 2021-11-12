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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import ar.com.despertador.dialogos.Configurar_ContactoActivity;
import ar.com.despertador.dialogos.Configurar_RadioActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Marker marcador;
    private TextView txvcalculo;
    private SearchView svbuscar;
    private float dist;
    private String distFormateada;
    LocationManager locationManager;
    String provider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Button btnGPSShowLocation;
    private Button btn_iniciar;
    double lat = 0.0;
    double log = 0.0;
    SupportMapFragment mapFragment;
    SearchView searchView;
    Location posactual = new Location("localizacion Usuario");
    Location posdestino = new Location("localizacion Destino");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        checkLocationPermission();
        posdestino.setLongitude(log);
        posdestino.setLatitude(lat);
        btn_iniciar = (Button) findViewById(R.id.btn_iniciar);
        txvcalculo = (TextView) findViewById(R.id.txvcalculo);
        svbuscar = (SearchView) findViewById(R.id.sv_ubicacion);
        btn_iniciar.setText("Iniciar Alarma");
        txvcalculo.setVisibility(View.INVISIBLE);
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dist <= 300) {
                    Toast.makeText(MapsActivity.this, "Ya estas dentro del radio seleccionado", Toast.LENGTH_LONG).show();

                } else {
                    if (btn_iniciar.getText() == "Iniciar Alarma") {
                        if (posdestino.getLatitude() == 0.0 && posdestino.getLongitude() == 0.0) {
                            Toast.makeText(MapsActivity.this, "Inicialmente por favor defina un destino", Toast.LENGTH_SHORT).show();
                        } else {
                            txvcalculo.setText("La distancia actual es: " + distFormateada + " Metros.");
                            txvcalculo.setVisibility(View.VISIBLE);
                            btn_iniciar.setText("CANCELAR Alarma");
                        }
                    } else {
                        miUbucacion();
                        txvcalculo.setVisibility(View.INVISIBLE);
                        txvcalculo.setText("");
                        svbuscar.setQuery("", false);
                        svbuscar.clearFocus();
                        posdestino.setLatitude(0.0);
                        posdestino.setLongitude(0.0);
                        btn_iniciar.setText("Iniciar Alarma");
                    }
                }
            }
        });
        LocationServices.getFusedLocationProviderClient(MapsActivity.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
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
        });

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
                    dist = posactual.distanceTo(posdestino);
                    DecimalFormat df = new DecimalFormat("#.00");
                    distFormateada = df.format(dist);

                    mMap.clear();

                    marcador = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo2)).anchor(0.0f, 1.04f));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    mMap.addCircle(new CircleOptions()
                            .center(latLng)
                            .radius(300)
                            .strokeColor(Color.GRAY)
                            .fillColor(Color.CYAN));
                    Toast.makeText(MapsActivity.this, "LA DISTANCIA AL DESTINO ES: " + distFormateada + " Metros.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);

        String emailU;
        emailU = getIntent().getStringExtra("email");


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
/*        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi Posicion Actual")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo2)).anchor(0.0f, 1.04f));*/
        mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi Posicion Actual"));
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
            //actualizarUbicacion(location);
            if (btn_iniciar.getText() != "Iniciar Alarma") {

                //           Toast.makeText(MapsActivity.this, "Entra a onlocationchanged", Toast.LENGTH_LONG).show();
                lat = location.getLatitude();
                log = location.getLongitude();
                posactual.setLongitude(log);
                posactual.setLatitude(lat);
                dist = posactual.distanceTo(posdestino);
                if (dist <= 300) {
                    txvcalculo.setText("LLEGASTEEEEEEE!!!!!!! (se supone que aca deberia sonar algo jaja)");
                } else {
                    DecimalFormat df = new DecimalFormat("#.00");
                    distFormateada = df.format(dist);
                    txvcalculo.setText("La distancia actual es: " + distFormateada + " Metros.");
                }

            } else {
                actualizarUbicacion(location);
            }
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbucacion();
    }

    public void AbrirDialogoCofContacto() {
        Intent intent = new Intent(this, Configurar_ContactoActivity.class);
        startActivity(intent);
    }

    public void AbrirDialogoConfigRadio() {
        Intent intent = new Intent(this, Configurar_RadioActivity.class);
        startActivity(intent);
    }

    public void IniciarRecorrido() {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(MapsActivity.this, "Entra a onlocationchanged", Toast.LENGTH_LONG).show();

        dist = posactual.distanceTo(posdestino);
        DecimalFormat df = new DecimalFormat("#.00");
        distFormateada = df.format(dist);
        txvcalculo.setText("La distancia actual es: " + distFormateada + " Metros.");
    }
}