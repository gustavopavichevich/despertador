package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ar.com.despertador.data.adapter.UsuarioAdapter;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Usuario;
import ar.com.despertador.ui.login.LoginActivity;


public class DataUsuarioActivity extends AsyncTask<String, Void, String> {


    private final Context context;
    private Persona persona;
    private Usuario usuario;
    private String accion;
    private ListView lvUsuarios;

    private static String result2;
    private static final ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

    //Recibe por constructor el textview
    //Constructor para el insert
    public DataUsuarioActivity(String accion, Persona persona, Usuario usuario, Context ct) {
        this.context = ct;
        this.persona = persona;
        this.usuario = usuario;
        this.accion = accion;

    }
//constructor para el select
    public DataUsuarioActivity(String accion, Usuario usuario, ListView lvUsuarios, Context ct) {
        this.usuario = usuario;
        this.lvUsuarios = lvUsuarios;
        this.context = ct;
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
            switch (accion) {
                case "insert":
                    st.executeUpdate("INSERT INTO personas(apellido, nombre, telefono, tipo, email) VALUES ('"
                            + persona.getApellido() + "','"
                            + persona.getNombre() + "','"
                            + persona.getTelefono() + "','"
                            + persona.getTipo() + "','"
                            + persona.getEmail() + "')");
                    st.executeUpdate("INSERT INTO usuarios(email, contrasenia) VALUES('"
                            + persona.getEmail() + "','"
                            + usuario.getContrasenia() + "')");
                    break;
                case "select":
                    ResultSet rs = st.executeQuery("SELECT * FROM usuarios where email = "); // falta terminar
                    result2 = " ";

                    Usuario usuario;
                    while (rs.next()) {
                        usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("email"), rs.getString("contrasena"));
                    }
                    break;
                default:
                    break;
            }
            response = "Conexion exitosa";
        } catch (Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        switch (accion) {
            case "insert":
                Toast.makeText(context, "insertamos el usuario!!!", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, LoginActivity.class));
                break;
            case "select":
                UsuarioAdapter adapter = new UsuarioAdapter(context, listaUsuarios);
                lvUsuarios.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                ((UsuarioAdapter)lvUsuarios.getAdapter()).notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}

