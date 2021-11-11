package ar.com.despertador;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ar.com.despertador.databinding.ActivityMapsBinding;
import ar.com.despertador.dialogos.Configurar_ContactoActivity;
import ar.com.despertador.dialogos.DialogoConfigurarContactoFragment;
import ar.com.despertador.dialogos.DialogoConfigurarRadioFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

//    static final int PICK_CONTACT_REQUEST=1;

    private GoogleMap mMap;
    private Marker marcador;
    Button btnGPSShowLocation;
    double lat = 0.0;
    double log = 0.0;
    //private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //binding = ActivityMapsBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        String emailU = getIntent().getStringExtra("email");
        Toast.makeText(this, "Usuario logueado " + emailU, Toast.LENGTH_SHORT).show();

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);

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
        Intent intent=new Intent(this, Configurar_ContactoActivity.class);
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
}