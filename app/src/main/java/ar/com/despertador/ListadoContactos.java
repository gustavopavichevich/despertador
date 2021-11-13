package ar.com.despertador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ar.com.despertador.dialogos.ConfiguracionAlarmaActivity;

public class ListadoContactos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_contactos);
    }
    public void EnviarContacto(View v)
    {
        Intent intent=new Intent(this, ConfiguracionAlarmaActivity.class);
        startActivity(intent);
    }
}