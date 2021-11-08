package ar.com.despertador;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import ar.com.despertador.dialogos.Configurar_ContactoActivity;
import ar.com.despertador.dialogos.DialogoConfigurarContactoFragment;
import ar.com.despertador.dialogos.DialogoConfigurarRadioFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

//    static final int PICK_CONTACT_REQUEST=1;

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