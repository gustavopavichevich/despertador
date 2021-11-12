package ar.com.despertador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;

import ar.com.despertador.data.Conexion.DataAlarmaActivity;
import ar.com.despertador.data.Conexion.DataUsuarioActivity;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Usuario;

public class ConfiguracionAlarmaActivity extends AppCompatActivity {
    MediaPlayer mp = new MediaPlayer();
    int position;
    int position2;
    int s1[] = {
            R.raw.classic_whistle,
            R.raw.digital_bell,
            R.raw.dubstep,
            R.raw.iphone_5_alarm,
            R.raw.iphone_sms,
            R.raw.samsung_galaxy_s3,
            R.raw.vampire_call
    };
    String[] title = new String[]{
            "classic_whistle",
            "digital_bell",
            "dubstep",
            "iphone_5_alarm",
            "iphone_sms",
            "samsung_galaxy_s3",
            "vampire_call"};

    EditText _txtbusqueda;
    ListView _lvalarma;
    SeekBar _volalarma;
    EditText _txtmensajealarma;

    Button boton_aceptar;
    private Persona persona;
    private Usuario usuario;
    private Context con;

    String _emailU,_radiow, _radiosms,_txtmensaje,_nombre,_numero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_alarma);
        boton_aceptar = (Button) findViewById(R.id.btnAceptar);

        _emailU=getIntent().getStringExtra("email");
        _radiow = getIntent().getStringExtra("radiow");
        _radiosms = getIntent().getStringExtra("_adiosms");
        _txtmensaje = getIntent().getStringExtra("txtmensaje");
        _nombre = getIntent().getStringExtra("nombre");
        _numero = getIntent().getStringExtra("numero");

        if (_txtmensaje!="") {
            _txtmensajealarma.setText(_txtmensaje);//pongo el mensaje que agrego el usuario
        }
        else{
            _txtmensajealarma.setText(_emailU +  " esta llegando al destino seleccionado, usted es su contacto de aviso.");//pongo el mensaje predeterminado
        }

        //Inicio Agrego
        final ListView list30 = (ListView) findViewById(R.id.lvAlarmas);

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, title);
        list30.setAdapter(adaptador);



        con = this;
        boton_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String acepta = texto_casilla.isChecked() ? "si" : "no";
                persona = new Persona();
                usuario = new Usuario();
                persona.setApellido(texto_apellido.getText().toString());
                persona.setNombre(texto_nombre.getText().toString());
                persona.setTelefono(texto_telefono.getText().toString());
                persona.setTipo("usuario");
                persona.setEmail(texto_email.getText().toString());
                usuario.setContrasenia(texto_contrasena.getText().toString());
                usuario.setEmail(texto_email.getText().toString());*/
                DataAlarmaActivity task = new DataAlarmaActivity("insert",persona, usuario, con);
                task.execute();
            }
        });


        /*list30.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                method(position);
            }
        });*/
        //list.setAdapter(adapter);
        //Log.i("ramiro", "llego al final");

        /*list30.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                //saveas(RingtoneManager.TYPE_RINGTONE, position);
                position2 = position; //utilizo position2 porque la this.position es para onItemClick

                final CharSequence[] items = {"Establecer como Ringtone", "Establecer como SMS/Notificación", "Establecer como Alarma"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0:
                                saveas(RingtoneManager.TYPE_RINGTONE, position2);
                                Toast.makeText(getApplicationContext(), "Se estableció como Ringtone", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                saveas(RingtoneManager.TYPE_NOTIFICATION, position2);
                                Toast.makeText(getApplicationContext(), "Se estableció como SMS/Notificación", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                saveas(RingtoneManager.TYPE_ALARM, position2);
                                Toast.makeText(getApplicationContext(), "Se estableció como Alarma", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });*/
        //Fin Agrego
    }
}