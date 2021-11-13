package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ar.com.despertador.MailJob;
import ar.com.despertador.MapsActivity;
import ar.com.despertador.data.model.Alarma;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Ubicacion;
import ar.com.despertador.data.model.Usuario;
import ar.com.despertador.ui.login.LoginActivity;


public class DataAlarmaActivity extends AsyncTask<String, Void, String> {


    private final Context context;
    private Persona persona;
    private Alarma alarma;
    private Ubicacion ubicacion;
    private String accion;
    private static String result2;
    private int total;
    private int iduser;
    private String contrasena ;


    //Recibe por constructor el textview
    //Constructor para el insert
    public DataAlarmaActivity(String accion, Persona persona, Alarma alarma, Ubicacion ubicacion, Context ct) {
        this.context = ct;
        this.persona = persona;
        this.alarma = alarma;
        this.ubicacion = ubicacion;
        this.accion = accion;

    }
//constructor para el select
    public DataAlarmaActivity(String accion, Alarma alarma, Ubicacion ubicacion, Context ct) {
        this.alarma = alarma;
        this.ubicacion = ubicacion;
        this.context = ct;
        this.accion = accion;
    }

    public DataAlarmaActivity(Context ct) {
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
                    //inserto los datos de la persona7contacto para la alarma generada
                    ResultSet rs2 = st.executeQuery("SELECT idPersona, apellido, nombre, telefono, tipo, email " +
                            "FROM sozsu9g190okokyf.personas where telefono='" + persona.getTelefono().toString() + "'");
                    result2 = " ";
                    try {
                        boolean ultimo = rs2.last();
                        int total = 0;
                        if (ultimo) {
                            rs2.first();
                            while (rs2.next()){
                                persona.setIdPersona(rs2.getInt("idPersona"));
                                persona.setApellido(rs2.getString("apellido"));
                                persona.setNombre(rs2.getString("nombre"));
                                persona.setTelefono(rs2.getString("telefono"));
                                persona.setTipo(rs2.getString("tipo"));
                                persona.setEmail(rs2.getString("email"));
                            }

                            st.executeQuery("DELETE FROM sozsu9g190okokyf.ubicaciones where idPersona='" + persona.getIdPersona() + "'");
                            st.executeQuery("DELETE FROM sozsu9g190okokyf.alarmas where idPersona='" + persona.getIdPersona() + "'");
                        }
                        else{
                            st.executeUpdate("INSERT INTO sozsu9g190okokyf.personas(apellido, nombre, telefono, tipo, email) VALUES ('"
                                    + persona.getApellido() + "','"
                                    + persona.getNombre() + "','"
                                    + persona.getTelefono() + "','"
                                    + persona.getTipo() + "','"
                                    + persona.getEmail() + "')");
                            ResultSet rs3 = st.executeQuery("SELECT idPersona " +
                                    "FROM sozsu9g190okokyf.personas where telefono='" + persona.getTelefono().toString() + "'");
                            while (rs3.next()){
                                persona.setIdPersona(rs3.getInt("idPersona"));
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    
                    //inserto la ubicacion de destino de para la alarma del usuario

                    st.executeUpdate("INSERT INTO sozsu9g190okokyf.ubicaciones(idPersona, poi)" +
                            "VALUES(" + persona.getIdPersona() + ", '" + ubicacion.getPoi() + "');");

                    //inserto la alarma
                    String QueryAlarma;
                    QueryAlarma="INSERT INTO sozsu9g190okokyf.alarmas(idPersona, nombre, urlTono, mensaje, distanciaActivacion, volumen)" +
                            "VALUES(" + persona.getIdPersona() + ",'"+
                            alarma.getNombre() + "', '" +
                            alarma.getUrlTono() + "', '" +
                            alarma.getMensaje() + "', " +
                            alarma.getDistanciaActivacion() + ", " +
                            alarma.getVolumen() + ");";
                    st.executeUpdate(QueryAlarma);

                    break;
                case "select":
                    ResultSet rs = st.executeQuery("SELECT idUsuario FROM usuarios where email = '" +
                            "' and contrasenia = '" + "' ");

                   result2 = " ";
                  total = 0;
                    while (rs.next()){
                     iduser = rs.getInt("idUsuario");
                        total++;
                    }
         // Terminamos de cargar el objet
                if (total == 1){
                    //alarma.setIdUsuario(iduser);
                }
                    break;
 /*               case "selectRecordar":
                    ResultSet rs2 = st.executeQuery("SELECT idUsuario, contrasenia FROM usuarios where email = '" + usuario.getEmail() + "' ");

                    result2 = " ";
                    total = 0;
                    while (rs2.next()){
                        contrasena = rs2.getString("contrasenia");
                        iduser = rs2.getInt("idUsuario");
                        total++;
                    }
                    // Terminamos de cargar el objet
                    if (total == 1){
                        usuario.setIdUsuario(iduser);
                        usuario.setContrasenia(contrasena);
                    }
                    break;*/
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
//        switch (accion) {
//            case "insert":
//                Toast.makeText(context, "insertamos el usuario!!!", Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context, LoginActivity.class));
//                break;
//            case "select":
//                if (usuario.getIdUsuario() > 0) {
//                    Toast.makeText(context, "Logueado con exito", Toast.LENGTH_LONG).show();
//                    context.startActivity(new Intent(context, MapsActivity.class));
//                }else
//                {
//                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
//                    context.startActivity(new Intent(context, LoginActivity.class));
//                }
//                break;
//            case "selectRecordar":
//                if (usuario.getIdUsuario() > 0) {
//                    new MailJob("appdespertador@gmail.com", "utn123456").execute(new MailJob.Mail("appdespertador@gmail.com", "leo.yermoli@gmail.com", "Contraseña de app Despertador UTN", "La contraseña del usuario "+ usuario.getEmail().toString() + " es: "+ usuario.getContrasenia().toString()));
//                    Toast.makeText(context, "Contraseña enviada, revise su casilla de correo", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(context, "Usuario inexistente", Toast.LENGTH_LONG).show();
//                }
//                break;
//            default:
//                break;
//        }
    }
}

