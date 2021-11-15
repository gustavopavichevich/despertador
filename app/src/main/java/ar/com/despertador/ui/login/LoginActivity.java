package ar.com.despertador.ui.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.regex.Pattern;

import ar.com.despertador.AgregarCuentaActivity;
import ar.com.despertador.R;
import ar.com.despertador.Validaciones;
import ar.com.despertador.data.Conexion.DataUsuarioActivity;
import ar.com.despertador.data.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    private Context con;
    private Button boton_ingresar;
    private Button boton_recordar;
    private Usuario usuario;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_SMS = 225;
    Validaciones objValidar; //objeto de nuestro clase Validaciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        con = this;
        boton_ingresar = findViewById(R.id.btnAceptar);
        boton_recordar = findViewById(R.id.login3);

        objValidar = new Validaciones();

        final EditText texto_email = findViewById(R.id.username);
        final EditText texto_contrasenia = findViewById(R.id.password);

        boton_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = new Usuario();

                if (!objValidar.isEmail(texto_email.getText().toString())) {
                    Toast.makeText(con, "El Formato de mail es invalido", Toast.LENGTH_SHORT).show();
                } else {
                    usuario.setContrasenia(texto_contrasenia.getText().toString());
                    usuario.setEmail(texto_email.getText().toString());
                    if (checkLocationPermission()) {
                        if (checkPermissionSMS()) {
                            DataUsuarioActivity task = new DataUsuarioActivity("select", usuario, con);
                            boton_ingresar.setEnabled(false);
                            boton_ingresar.setBackgroundColor(getColor(R.color.colorGris));
                            Toast.makeText(con, "Por favor, aguarde hasta que validemos sus datos", Toast.LENGTH_LONG).show();
                            task.execute();
                        }
                    }
                }
            }
        });

        boton_recordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!objValidar.isEmail(texto_email.getText().toString())) {
                    Toast.makeText(con, "El Formato de mail es invalido", Toast.LENGTH_SHORT).show();
                } else {
                    usuario = new Usuario();
                    String textoUser = texto_email.getText().toString();
                    usuario.setEmail(textoUser);
                    DataUsuarioActivity task = new DataUsuarioActivity("selectRecordar", usuario, con);
                    task.execute();
                }
            }
        });
    }

    public void Agregar(View view) {
        Intent agregar = new Intent(this, AgregarCuentaActivity.class);
        startActivity(agregar);
    }

    public boolean checkPermissionSMS() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                new AlertDialog.Builder(this)
                        .setTitle("Aprobacion de permisos de Envio de SMS")
                        .setMessage("Por favor habilite el permiso de Envio de SMS para la aplicacion")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.SEND_SMS},
                                        MY_PERMISSIONS_REQUEST_SMS);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SMS);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Aprobacion de permisos de ubicacion")
                        .setMessage("Por favor habilite el permiso de ubicacion para la aplicacion")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

}