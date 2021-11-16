package ar.com.despertador.dialogos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import ar.com.despertador.MapsActivity;
import ar.com.despertador.R;
import ar.com.despertador.Validaciones;

public class Configurar_ContactoActivity extends AppCompatActivity {
    static final int PICK_CONTACT_REQUEST=1;
    RadioButton _radiow, _radiosms;
    EditText _txtmensaje;
    private static String _emailU,_poiDestino;
    Validaciones objValidar; //objeto de nuestro clase Validaciones
    private String TextoDestino;
    private Integer radio= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_contacto);
        radio = getIntent().getIntExtra("radio", radio);
       TextoDestino = getIntent().getStringExtra("TextoDestino");


        _emailU=getIntent().getStringExtra("email");
        _poiDestino = getIntent().getStringExtra("poidestino");
        _radiow = (RadioButton)findViewById(R.id.radioWhatsApp);
        _radiosms = (RadioButton)findViewById(R.id.radioSMS);
        _txtmensaje = (EditText)findViewById(R.id.txtMensaje);
        objValidar = new Validaciones();
    }
    public void ElegirContacto(View v)
    {
            Intent selectContactoIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contactas"));
            selectContactoIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(selectContactoIntent,PICK_CONTACT_REQUEST);
    }
    public void cancelar (View v){
        Intent intent = new Intent(Configurar_ContactoActivity.this, MapsActivity.class);
        intent.putExtra("TextoDestino", TextoDestino);
        intent.putExtra("radio", radio);
        this.startActivity(intent);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();

                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int columnaNombre = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int columnaNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String nombre = cursor.getString(columnaNombre);
                    String numero = cursor.getString(columnaNumero);
                    //grabar los datos del contacto seleccionado en la base

                    //Voy a configurar Alarma
                    Intent intent = new Intent(this, ConfiguracionAlarmaActivity.class);
                    //paso los valores al siguiente activity
                    intent.putExtra("email",_emailU);
                    intent.putExtra("poidestino",_poiDestino);
                    intent.putExtra("txtmensaje",_txtmensaje.getText().toString());
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("numero",numero);
                    intent.putExtra("TextoDestino", TextoDestino);
                    intent.putExtra("radio", radio);
                    startActivity(intent);
                    this.finish();
                }
            }
        }
    }
    public void volver(View v) {
        this.finish();
    }
}