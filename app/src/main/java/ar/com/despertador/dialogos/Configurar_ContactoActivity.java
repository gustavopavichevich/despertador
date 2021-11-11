package ar.com.despertador.dialogos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import ar.com.despertador.ConfiguracionAlarmaActivity;
import ar.com.despertador.R;

public class Configurar_ContactoActivity extends AppCompatActivity {
    static final int PICK_CONTACT_REQUEST=1;
    RadioButton _radiow, _radiosms;
    EditText _txtmensaje;
    String _emailU;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_contacto);
        _emailU=getIntent().getStringExtra("email");
        _radiow = (RadioButton)findViewById(R.id.radioWhatsApp);
        _radiosms = (RadioButton)findViewById(R.id.radioSMS);
        _txtmensaje = (EditText)findViewById(R.id.txtMensaje);
    }
    public void ElegirContacto(View v)
    {
        /*Intent intent=new Intent(this, ListadoContactos.class);
        startActivity(intent);*/
        Intent selectContactoIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contactas"));
        selectContactoIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(selectContactoIntent,PICK_CONTACT_REQUEST);
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
                    Toast.makeText(this, "Registro Seleccionado: " + nombre + " Celular: " + numero, Toast.LENGTH_SHORT).show();
                    //grabar los datos del contacto seleccionado en la base


                    //Voy a configurar Alarma
                    //paso los valores al siguiente activity

                    Intent intent = new Intent(this, ConfiguracionAlarmaActivity.class);
                    startActivity(intent);

                }
            }
        }
    }
}