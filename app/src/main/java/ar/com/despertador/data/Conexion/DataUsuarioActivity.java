package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ar.com.despertador.MailJob;
import ar.com.despertador.MapsActivity;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Usuario;
import ar.com.despertador.ui.login.LoginActivity;


public class DataUsuarioActivity extends AsyncTask<String, Void, String> {


    private final Context context;
    private Persona persona;
    private Usuario usuario;
    private String accion;
    private static String result2;
    private int total;
    private int iduser;
    private String contrasena;


    //Recibe por constructor el textview
    //Constructor para el insert
    public DataUsuarioActivity(String accion, Persona persona, Usuario usuario, Context ct) {
        this.context = ct;
        this.persona = persona;
        this.usuario = usuario;
        this.accion = accion;

    }

    //constructor para el select
    public DataUsuarioActivity(String accion, Usuario usuario, Context ct) {
        this.usuario = usuario;
        this.context = ct;
        this.accion = accion;
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
                    ResultSet rs = st.executeQuery("SELECT idUsuario FROM usuarios where email = '" + usuario.getEmail() +
                            "' and contrasenia = '" + usuario.getContrasenia() + "' ");

                    result2 = " ";
                    total = 0;
                    while (rs.next()) {
                        iduser = rs.getInt("idUsuario");
                        total++;
                    }

                    // Terminamos de cargar el objet
                    //
                    if (total == 1) {
                        usuario.setIdUsuario(iduser);
                        usuario.setEmail(usuario.getEmail());
                    }
                    break;
                case "selectRecordar":
                    ResultSet rs2 = st.executeQuery("SELECT idUsuario, contrasenia FROM usuarios where email = '" + usuario.getEmail() + "' ");

                    result2 = " ";
                    total = 0;
                    while (rs2.next()) {
                        contrasena = rs2.getString("contrasenia");
                        iduser = rs2.getInt("idUsuario");
                        total++;
                    }

                    // Terminamos de cargar el objet
                    //
                    if (total == 1) {
                        usuario.setIdUsuario(iduser);
                        usuario.setContrasenia(contrasena);
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
                if (usuario.getIdUsuario() > 0) {
                    Toast.makeText(context, "Logueado con exito", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("email", usuario.getEmail());

                    context.startActivity(intent);

                    //context.startActivity(new Intent(context, MapsActivity.class));
                } else {
                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case "selectRecordar":

                if (usuario.getIdUsuario() > 0) {
                    new MailJob("appdespertador@gmail.com", "utn123456").execute(new MailJob.Mail("appdespertador@gmail.com", "leo.yermoli@gmail.com", "Contraseña de app Despertador UTN", "La contraseña del usuario " + usuario.getEmail() + " es: " + usuario.getContrasenia()));
                    Toast.makeText(context, "Contraseña enviada, revise su casilla de correo", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Usuario inexistente", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}

