package ar.com.despertador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarCuentaActivity extends AppCompatActivity {
    Button boton_continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cuenta);
        boton_continuar = (Button) findViewById(R.id.button);
        final EditText texto_nombre = findViewById(R.id.editTextTextPersonName);
        final EditText texto_apellido = findViewById(R.id.editTextTextPersonName2);
        final EditText texto_telefono = findViewById(R.id.editTextPhone);
        final EditText texto_email = findViewById(R.id.editTextTextPersonName3);
        final EditText texto_contrasena = findViewById(R.id.editTextTextPassword);
        final CheckBox texto_casilla = findViewById(R.id.checkBox);

        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = texto_nombre.getText().toString();
                String apellido = texto_apellido.getText().toString();
                String telefono = texto_telefono.getText().toString();
                String email = texto_email.getText().toString();
                String contrasena = texto_contrasena.getText().toString();
                String acepta = texto_casilla.isChecked() ? "si" : "no";
                Toast.makeText(AgregarCuentaActivity.this,"Nombre: "+nombre+" Apellido: "+apellido+" Telefono: "+telefono+" Email: "+email+" Pass "+contrasena+" Acepta?: "+acepta, Toast.LENGTH_SHORT).show();
            }
        });
    }
}