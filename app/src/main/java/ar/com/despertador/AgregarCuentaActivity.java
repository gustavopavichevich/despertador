package ar.com.despertador;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import ar.com.despertador.data.Conexion.DataUsuarioActivity;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Usuario;
import ar.com.despertador.ui.login.LoginActivity;

public class AgregarCuentaActivity extends AppCompatActivity {
    Button boton_continuar;
    private Persona persona;
    private Usuario usuario;
    private Context con;

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
        con = this;
        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarEmail(texto_email.getText().toString())){
                    Toast.makeText(con, "El Formato de mail es invalido", Toast.LENGTH_SHORT).show();
                }
                else{
                    String acepta = texto_casilla.isChecked() ? "si" : "no";
                    persona = new Persona();
                    usuario = new Usuario();
                    persona.setApellido(texto_apellido.getText().toString());
                    persona.setNombre(texto_nombre.getText().toString());
                    persona.setTelefono(texto_telefono.getText().toString());
                    persona.setTipo("usuario");
                    persona.setEmail(texto_email.getText().toString());
                    usuario.setContrasenia(texto_contrasena.getText().toString());
                    usuario.setEmail(texto_email.getText().toString());
                    DataUsuarioActivity task = new DataUsuarioActivity("insert",persona, usuario, con);
                    task.execute();
                }
            }
        });
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void volver() {
        this.finish();
    }

    public void IraLogin(View view) {
        Intent agregar = new Intent(this, LoginActivity.class);
        startActivity(agregar);
    }

}