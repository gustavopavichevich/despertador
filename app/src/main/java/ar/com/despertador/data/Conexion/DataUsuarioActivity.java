package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import ar.com.despertador.AgregarCuentaActivity;
import ar.com.despertador.data.adapter.UsuarioAdapter;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Usuario;
import ar.com.despertador.ui.login.LoginActivity;


public class DataUsuarioActivity extends AsyncTask<String, Void, String> {


    private Context context;
    private Persona persona;
    private Usuario usuario;

    private static String result2;

    //Recibe por constructor el textview
    //Constructor
    public DataUsuarioActivity(Persona persona, Usuario usuario, Context ct) {
        this.context = ct;
        this.persona = persona;
        this.usuario = usuario;

    }
    public DataUsuarioActivity(Context ct) {
        this.context = ct;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataBD.urlMySQL, DataBD.user, DataBD.pass);
            Statement st = con.createStatement();

            st.executeUpdate("INSERT INTO personas(apellido, nombre, telefono, tipo, email) VALUES ('"
                    + persona.getApellido() + "','"
                    + persona.getNombre() + "','"
                    + persona.getTelefono() + "','"
                    + persona.getTipo()+ "','"
                    + persona.getEmail()+"')");
            st.executeUpdate("INSERT INTO usuarios(email, contrasenia) VALUES('"
                    + persona.getEmail() +"','"
                    + usuario.getContrasenia()+"')");

            response = "Conexion exitosa";
        } catch (Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, "insertamos el usuario!!!", Toast.LENGTH_SHORT).show();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}

