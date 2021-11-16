package ar.com.despertador.dialogos;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.despertador.MapsActivity;
import ar.com.despertador.R;
import ar.com.despertador.Validaciones;
import ar.com.despertador.data.Conexion.DataAlarmaActivity;
import ar.com.despertador.data.model.Alarma;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Ubicacion;

public class ConfiguracionAlarmaActivity extends AppCompatActivity {
    MediaPlayer mp = new MediaPlayer();
    int position;
    int position2;
    int[] s1 = {
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

    EditText _txtnombrealarma;
    ListView _lvalarmas;
    SeekBar _volalarma;
    EditText _txtmensajealarma;
    private String TextoDestino;
    private Integer radio = 0;

    Button boton_aceptar;
    Button boton_cancelar;
    private Alarma alarma;
    private Ubicacion ubicacion;
    private Persona persona;
    private Context con;

    String _emailU, _radiow, _radiosms, _txtmensaje, _nombre, _numero, _poiDestino, _tono;
    Integer _volumen;

    Validaciones objValidar; //objeto de nuestro clase Validaciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_alarma);
        //Variables del activity
        radio = getIntent().getIntExtra("radio", radio);
        TextoDestino = getIntent().getStringExtra("TextoDestino");
        boton_aceptar = (Button) findViewById(R.id.btnAceptar);
        boton_cancelar = (Button) findViewById(R.id.btnCancelarConfAlarm);
        _txtnombrealarma = (EditText) findViewById(R.id.txtNombreAlarma);
        _lvalarmas = (ListView) findViewById(R.id.lvAlarmas);
        _volalarma = (SeekBar) findViewById(R.id.seekBarVolumen);
        _txtmensajealarma = (EditText) findViewById(R.id.txtMensajeAlarma);

        objValidar = new Validaciones();

        _emailU = getIntent().getStringExtra("email");
        _poiDestino = getIntent().getStringExtra("poidestino");
        _txtmensaje = getIntent().getStringExtra("txtmensaje");
        _nombre = getIntent().getStringExtra("nombre");
        _numero = getIntent().getStringExtra("numero");

        _txtmensajealarma.setText(_txtmensaje);
        if (!objValidar.Vacio(_txtmensajealarma)) {
            _txtmensajealarma.setError("");
            _txtmensajealarma.setText(_txtmensaje);//pongo el mensaje que agrego el usuario
        } else {
            _txtmensajealarma.setError("");
            _txtmensajealarma.setText(_emailU + " Esta llegando al destino seleccionado, usted es su contacto de aviso.");//pongo el mensaje predeterminado
        }
        final ListView list30 = (ListView) findViewById(R.id.lvAlarmas);
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, title);
        list30.setAdapter(adaptador);


        _lvalarmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //TextView textView = (TextView) view.findViewById(R.id.list_content);
                _tono = _lvalarmas.getItemAtPosition(position).toString().trim();
                //System.out.println("Chosen Country = : " + text);

            }
        });

        con = this;
        boton_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String acepta = texto_casilla.isChecked() ? "si" : "no";
                //cargo las clases con los valores recuperado en tre los activitys
                if (!objValidar.Vacio( _txtnombrealarma) && !objValidar.Vacio(_txtmensajealarma) && _tono != "") {
                    boton_aceptar.setEnabled(false);
                    boton_aceptar.setBackgroundColor(getColor(R.color.colorGris));
                    Toast.makeText(getApplicationContext(), "Grabando información de alerta", Toast.LENGTH_SHORT).show();
                    alarma = new Alarma();
                    ubicacion = new Ubicacion();
                    persona = new Persona();
           //         int radio = 500;
                //    radio = getIntent().getIntExtra("radio", radio);

                    persona.setApellido(_nombre);
                    persona.setNombre(_nombre);
                    persona.setTelefono(_numero);
                    getIntent().putExtra("numeroTelefono", _numero);
                    persona.setTipo("contacto");
                    persona.setEmail(_emailU);

                    alarma.setNombre(_txtnombrealarma.getText().toString());
                    alarma.setUrlTono(_tono);
                    getIntent().putExtra("tono", _tono);
                    alarma.setMensaje(_txtmensajealarma.getText().toString());
                    getIntent().putExtra("txtMensaje", _txtmensajealarma.getText().toString());
                    alarma.setDistanciaActivacion(radio);//distancia predefinida
                    alarma.setVolumen(_volalarma.getProgress());//obtengo el valor seleccionado
                    getIntent().putExtra("volumen", _volalarma.getProgress());

                    ubicacion.setPoi(_poiDestino);//la posicion de la busqueda de destino

                    DataAlarmaActivity task = new DataAlarmaActivity("insert", persona, alarma, ubicacion, con,TextoDestino, radio);
                    task.execute();
         //           volver();
                } else {
                    Toast.makeText(getApplicationContext(), "Verifique que todos los campos esten completos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Fin Agrego
    }

    public void cancelar (View v){
        Intent intent = new Intent(ConfiguracionAlarmaActivity.this, MapsActivity.class);
        intent.putExtra("TextoDestino", TextoDestino);
        intent.putExtra("radio", radio);
        this.startActivity(intent);

    }
    public void volver ()
    {
        this.finish();
    }

}