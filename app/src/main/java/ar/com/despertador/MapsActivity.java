package ar.com.despertador;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ar.com.despertador.databinding.ActivityMapsBinding;
import ar.com.despertador.dialogos.DialogoConfigurarContactoFragment;
import ar.com.despertador.dialogos.DialogoConfigurarRadioFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //  mMap.setPadding(28,80,4,67);
        // Add a marker in Buenos Aires and move the camera
//        LatLng bsas = new LatLng(-34.6075682, -58.4370894);
//        mMap.addMarker(new MarkerOptions().position(bsas).title("Buenos Aires, Argentina"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(bsas));
        //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bsas,12));
        LatLng unicenter = new LatLng(-34.5086111, -58.52388888888889);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.logo2)).anchor(0.0f, 1.04f).position(unicenter).title("Unicenter"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unicenter, 16));
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(unicenter)
                .radius(300)
                .strokeColor(Color.GRAY)
                .fillColor(Color.CYAN));

    }

    public void AbrirDialogoCofContacto() {

        DialogoConfigurarContactoFragment dialogoTipoJuego = new DialogoConfigurarContactoFragment();
        dialogoTipoJuego.show(getSupportFragmentManager(), "Dialogo Configurar Contacto");
    }

    public void AbrirDialogoConfigRadio() {

        DialogoConfigurarRadioFragment dialogoRadio = new DialogoConfigurarRadioFragment();
        dialogoRadio.show(getSupportFragmentManager(), "Dialogo Configurar Radio");
    }
}