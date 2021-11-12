package ar.com.despertador.dialogos;

import android.content.Intent;
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
    private int radio = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_radio);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView) findViewById(R.id.progress);
        mTrackingText = (TextView) findViewById(R.id.tracking);
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
                volver();
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // Durante el arrastre, es decir, el valor está cambiando
        // el progreso es el tamaño del valor actual
        mProgressText.setText("Valor actual:" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Este método se llamará durante el arrastre
        mTrackingText.setText("xh se está ajustando");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Dejar de arrastrar
        mTrackingText.setText("ajuste de parada xh");
    }

    public void volver() {
        Intent intent = new Intent(this, MapsActivity.class);
        if (radio != 300)
            radio = Integer.parseUnsignedInt(mProgressText.getText().toString());
        intent.putExtra("radio", radio);
        startActivity(intent);
    }
}
