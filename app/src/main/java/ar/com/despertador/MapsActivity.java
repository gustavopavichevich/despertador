package ar.com.despertador;

import androidx.fragment.app.FragmentActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ar.com.despertador.databinding.ActivityMapsBinding;
import ar.com.despertador.dialogos.DialogoConfigurarContactoFragment;

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

        // Add a marker in Buenos Aires and move the camera
        LatLng bsas = new LatLng(-34.6075682, -58.4370894);
        mMap.addMarker(new MarkerOptions().position(bsas).title("Buenos Aires, Argentina"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(bsas));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bsas,8));
        LatLng unicenter = new LatLng(-34.5086111, -58.52388888888889);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_llegada_poi_foreground)).anchor(0.0f, 1.04f).position(unicenter).title("Unicenter"));
    }
    public void AbrirDialogoCofContacto()
    {

        DialogoConfigurarContactoFragment dialogoTipoJuego=new DialogoConfigurarContactoFragment();
        dialogoTipoJuego.show(getSupportFragmentManager(),"Dialogo Configurar Contacto");
    }
}