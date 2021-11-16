package ar.com.despertador.dialogos;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.despertador.MapsActivity;
import ar.com.despertador.R;

public class Configurar_RadioActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar mSeekBar;
    private TextView mProgressText;
    private TextView mTrackingText;
    private Button btnCancelarConfRadio;
    private Button btn_AceptarConfRadio;
    private int radio;
    private String _emailU;
    private String TextoDestino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_radio);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView) findViewById(R.id.progress);
        mTrackingText = (TextView) findViewById(R.id.tracking);
        radio = getIntent().getIntExtra("radio",1);
        _emailU = getIntent().getStringExtra("email");

        TextoDestino = getIntent().getStringExtra("TextoDestino");
       // desti = getIntent().getLocationExtra("desti");
        mSeekBar.setProgress(radio);
        mTrackingText.setText(radio+" Mts.");
        btnCancelarConfRadio = (Button) findViewById(R.id.btnCancelarConfRadio);
        btnCancelarConfRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });
        btn_AceptarConfRadio = (Button) findViewById(R.id.btnAceptarConfRadio);
        btn_AceptarConfRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configurar_RadioActivity.this, MapsActivity.class);
                intent.putExtra("radio", radio);
         //       intent.putExtra("desti", desti);
                intent.putExtra("Regrafica", "si");

                intent.putExtra("email", _emailU);
                intent.putExtra("TextoDestino", TextoDestino);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // Durante el arrastre, es decir, el valor está cambiando
        // el progress es el tamaño del valor actual
        radio = progress;
   //     mProgressText.setText("Valor actual:" + radio);
        mTrackingText.setText(radio+" Mts.");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Este método se llamará durante el arrastre
    //    mTrackingText.setText("¿Cuál es la distancia de radio?");
        mProgressText.setText("Configurando un nuevo radio...");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Dejar de arrastrar
   //     mTrackingText.setText("¡Configuremos un nuevo radio!");


                mProgressText.setText("La alarma sonara cuando se encuentre a: " + radio + " mts. del destino");
    }

    public void volver() {
        this.finish();
    }
}
