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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
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
import ar.com.despertador.data.services.SoundManager;
import ar.com.despertador.data.Conexion.DataAlarmaActivity;
import ar.com.despertador.data.model.Alarma;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.dialogos.Configurar_ContactoActivity;
import ar.com.despertador.dialogos.Configurar_RadioActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    private Marker marcador;
    private TextView txvcalculo;
    private SearchView svbuscar;
    private float dist;
    private String distFormateada;
    private MediaPlayer mp;
    LocationManager locationManager;
    String provider;
    int flagLlego = 0;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_SMS = 225;
    Button btnGPSShowLocation;
    private Button btn_iniciar;
    double lat = 0.0;
    double log = 0.0;
    private int radio = 0;
    SupportMapFragment mapFragment;
    FloatingActionButton AvisaraContacto;
    FloatingActionButton aplicarRadio;
    // SearchView searchView;
    Location posactual = new Location("localizacion Usuario");
    Location posdestino = new Location("localizacion Destino");
    private static String _emailU;
    private String regrafica;
    private String TextoDestino="";
    private Persona persona;
    private Alarma alarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        _emailU = getIntent().getStringExtra("email");
        regrafica = getIntent().getStringExtra("Regrafica");
        TextoDestino =  getIntent().getStringExtra("TextoDestino");
        radio = getIntent().getIntExtra("radio",radio);
        checkLocationPermission();
        posdestino.setLongitude(log);
        posdestino.setLatitude(lat);
        btn_iniciar = findViewById(R.id.btn_iniciar);
        txvcalculo = findViewById(R.id.txvcalculo);
        svbuscar = findViewById(R.id.sv_ubicacion);
        btn_iniciar.setText("Iniciar Alarma");
        txvcalculo.setVisibility(View.INVISIBLE);

        if (TextoDestino != null){
            svbuscar.setQuery(TextoDestino.toString(),true);
        }
        if (regrafica != null) {
            if (regrafica.equals("si")) {
                radio = getIntent().getIntExtra("radio", radio);
                TextoDestino = getIntent().getStringExtra("TextoDestino");
                svbuscar.setQuery(TextoDestino, true);
    //            svbuscar.onWindowFocusChanged(true);
            }
        }
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dist <= 0 && (posdestino.getLatitude() != 0.0 && posdestino.getLongitude() != 0.0)) {
                    Toast.makeText(MapsActivity.this, "Ya estas dentro del radio seleccionado", Toast.LENGTH_LONG).show();
                } else {
                    if (btn_iniciar.getText() == "Iniciar Alarma") {
                        if (posdestino.getLatitude() == 0.0 && posdestino.getLongitude() == 0.0) {
                            Toast.makeText(MapsActivity.this, "Inicialmente por favor defina un destino", Toast.LENGTH_SHORT).show();
                        } else {
                            txvcalculo.setText("La distancia actual es: " + distFormateada + " Metros.");
                            flagLlego = 0;
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
                        if (mp.isPlaying()){mp.stop();}
                        btn_iniciar.setText("Iniciar Alarma");
                    }
                }
            }
        });
        LocationServices.getFusedLocationProviderClient(MapsActivity.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            }
        });

        //  searchView = findViewById(R.id.sv_ubicacion);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        svbuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = svbuscar.getQuery().toString();
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
                    dist = posactual.distanceTo(posdestino) - radio;
                    TextoDestino = svbuscar.getQuery().toString();
                    DecimalFormat df = new DecimalFormat("#.00");
                    svbuscar.clearFocus();
                    distFormateada = df.format(dist);
                    mMap.clear();
                    marcador = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo2)).anchor(0.0f, 1.04f));
                    radio = getIntent().getIntExtra("radio", radio);
                    int zoom = 16;
                    if (radio >= 500)
                        zoom = 15;
                    if (radio >= 1000)
                        zoom = 14;
                    if (radio >= 1500)
                        zoom = 13;
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                    mMap.addCircle(new CircleOptions()
                            .center(latLng)
                            .radius(radio)
                            .strokeColor(Color.GRAY)
                            .fillColor(Color.CYAN));
                    Toast.makeText(MapsActivity.this, "LA DISTANCIA AL DESTINO ES: " + distFormateada + " Metros.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
        String emailU = getIntent().getStringExtra("email");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AvisaraContacto = findViewById(R.id.AvisaraContacto);
        AvisaraContacto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AbrirDialogoCofContacto();
            }
        });
        aplicarRadio = findViewById(R.id.DefinirDistancia);
        aplicarRadio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AbrirDialogoConfigRadio();
            }
        });
        btnGPSShowLocation = findViewById(R.id.btnGPSShowLocation);
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

            if (flagLlego == 0) {
                if (btn_iniciar.getText() != "Iniciar Alarma") {
                    //           Toast.makeText(MapsActivity.this, "Entra a onlocationchanged", Toast.LENGTH_LONG).show();
                    lat = location.getLatitude();

                    log = location.getLongitude();
                    posactual.setLongitude(log);
                    posactual.setLatitude(lat);
                    dist = posactual.distanceTo(posdestino) - radio;
                    if (dist <= 0) {
                        txvcalculo.setText("Llegaste a tu destino!");
                        btn_iniciar.setText("Finalizar Alarma");
                        reproducirTono();
                        mandarSMS();
                        flagLlego = 1;
                    } else {
                        DecimalFormat df = new DecimalFormat("#.00");
                        distFormateada = df.format(dist);
                        txvcalculo.setText("La distancia actual es: " + distFormateada + " Metros.");
                    }
                } else {
                    //  actualizarUbicacion(location);
                }
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
    private void reproducirTono() {
        String _tono = getIntent().getStringExtra("tono");
        Integer _volumen = 20;
        _volumen = getIntent().getIntExtra("volumen", _volumen);
        //  MediaPlayer mp;

        //Configuracion del tono seleecionado
        // Lee los sonidos que figuran en res/raw
        int sonido_de_Reproduccion;
        if (_tono == null)
            _tono = "iphone_sms";
        switch (_tono) {
            case "classic_whistle":
                mp = MediaPlayer.create(this, R.raw.classic_whistle);
                break;
            case "digital_bell":
                mp = MediaPlayer.create(this, R.raw.digital_bell);
                break;
            case "dubstep":
                mp = MediaPlayer.create(this, R.raw.dubstep);
                break;
            case "iphone_5_alarm":
                mp = MediaPlayer.create(this, R.raw.iphone_5_alarm);
                break;
            case "iphone_sms":
                mp = MediaPlayer.create(this, R.raw.iphone_sms);
                break;
            case "vampire_call":
                mp = MediaPlayer.create(this, R.raw.vampire_call);
                break;
            default:
                mp = MediaPlayer.create(this, R.raw.samsung_galaxy_s3);
                break;
        }
        //Configuro el volumen al valor elegido por el usuario
        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int streamType = AudioManager.STREAM_MUSIC;
        //Configuracion de volumen
        for (int j = 0; j < 20; j++) {//lo dejo en 0
            audioManager.adjustStreamVolume(streamType,
                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }
        int vo;
        for (vo = 0; vo < _volumen; vo++) {//lo incremento por lo seleccionado
            audioManager.adjustStreamVolume(streamType,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        }
        mp.start();

    }

    private void miUbucacion() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (!checkLocationPermission())
            return;

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Aprobacion de permisos de ubicacion")
                        .setMessage("Por favor habilite el permiso de ubicacion para la aplicacion")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
        if (posdestino.getLatitude() != 0.0 && posdestino.getLongitude() != 0.0) {
            Intent intent = new Intent(this, Configurar_ContactoActivity.class);
            intent.putExtra("email", _emailU);
            intent.putExtra("poidestino", posdestino.getLatitude() + "-" + posdestino.getLongitude());
            intent.putExtra("TextoDestino", TextoDestino);
            intent.putExtra("radio", radio);
            startActivity(intent);
        } else {
            Toast.makeText(MapsActivity.this, "Debe Buscar su Dirección de Destino", Toast.LENGTH_LONG).show();
        }
    }
    public void AbrirDialogoConfigRadio() {
            if (posdestino.getLatitude() != 0.0 && posdestino.getLongitude() != 0.0) {
        Intent intent = new Intent(this, Configurar_RadioActivity.class);
        TextoDestino = svbuscar.getQuery().toString();
        intent.putExtra("TextoDestino", TextoDestino);
                intent.putExtra("email", _emailU);
        intent.putExtra("radio", radio);
        //      intent.putExtra("desti", posdestino);
        startActivity(intent);
           } else {
                Toast.makeText(MapsActivity.this, "Debe Buscar su Dirección de Destino", Toast.LENGTH_LONG).show();
           }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //   Toast.makeText(MapsActivity.this, "Entra a onlocationchanged", Toast.LENGTH_LONG).show();
        dist = posactual.distanceTo(posdestino) - radio;
        DecimalFormat df = new DecimalFormat("#.00");
        distFormateada = df.format(dist);
        txvcalculo.setText("La distancia actual es: " + distFormateada + " Metros.");
    }
    public void mandarSMS() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para enviar SMS.");
            new AlertDialog.Builder(this)
                    .setTitle("Aprobacion de permisos de Envio de SMS")
                    .setMessage("Por favor habilite el permiso de Envio de SMS para la aplicacion")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SMS);
                        }
                    })
                    .create()
                    .show();
            return;
        } else {
            int control = 1;
            persona = new Persona();
            persona.setEmail(_emailU);
            alarma = new Alarma();
            DataAlarmaActivity task = new DataAlarmaActivity("selectSMS", persona, alarma, this);
            task.execute();

            return;
        }
    }
}