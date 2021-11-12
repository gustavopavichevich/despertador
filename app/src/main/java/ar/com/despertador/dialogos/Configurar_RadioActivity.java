package ar.com.despertador.dialogos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.despertador.R;

public class Configurar_RadioActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar mSeekBar;
    private TextView mProgressText;
    private TextView mTrackingText;
    private Button btnCancelarConfCont;
    private Button btn_AceptarConfCont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_radio);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView) findViewById(R.id.progress);
        mTrackingText = (TextView) findViewById(R.id.tracking);
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
}
