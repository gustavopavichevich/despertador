package ar.com.despertador.data.Conexion;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ar.com.despertador.MapsActivity;
import ar.com.despertador.data.model.Alarma;
import ar.com.despertador.data.model.Persona;
import ar.com.despertador.data.model.Ubicacion;


public class DataAlarmaActivity extends AsyncTask<String, Void, String> {
    private final Context context;
    private Persona persona;
    private Alarma alarma;
    private Ubicacion ubicacion;
    private String accion;
    private static String result2;
    private int total;
    private int iduser;
    private String TextoDestino;
    private Integer radio;


    //Recibe por constructor el textview
    //Constructor para el insert
    public DataAlarmaActivity(String accion, Persona persona, Alarma alarma, Ubicacion ubicacion, Context ct) {
        this.context = ct;
        this.persona = persona;
        this.alarma = alarma;
        this.ubicacion = ubicacion;
        this.accion = accion;

    }
    public DataAlarmaActivity(String accion, Persona persona, Alarma alarma, Ubicacion ubicacion, Context ct, String txtDestino, Integer rad) {
        this.context = ct;
        this.persona = persona;
        this.alarma = alarma;
        this.ubicacion = ubicacion;
        this.accion = accion;
        TextoDestino = txtDestino;
        radio = rad;

    }


    //constructor para el select
    public DataAlarmaActivity(String accion, Alarma alarma, Ubicacion ubicacion, Context ct) {
        this.alarma = alarma;
        this.ubicacion = ubicacion;
        this.context = ct;
        this.accion = accion;
    }

    //constructor para el select
    public DataAlarmaActivity(String accion, Persona persona, Alarma alarma, Context ct) {
        this.persona = persona;
        this.alarma = alarma;
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
                    String QuerySelect="SELECT idPersona, apellido, nombre, telefono, tipo, email " +
                            "FROM sozsu9g190okokyf.personas where telefono='" + persona.getTelefono().toString() + "'";
                    ResultSet rs2 = st.executeQuery(QuerySelect);
                    result2 = " ";
                    try {
                        //boolean ultimo = rs2.last();
                        if (rs2.isBeforeFirst()) {
                            //rs2.first();
                            while (rs2.next()) {
                                persona.setIdPersona(rs2.getInt("idPersona"));
                                persona.setApellido(rs2.getString("apellido"));
                                persona.setNombre(rs2.getString("nombre"));
                                persona.setTelefono(rs2.getString("telefono"));
                                persona.setTipo(rs2.getString("tipo"));
                                persona.setEmail(rs2.getString("email"));
                            }

                            st.executeQuery("DELETE FROM sozsu9g190okokyf.ubicaciones where idPersona=" + persona.getIdPersona() + "");
                            st.executeQuery("DELETE FROM sozsu9g190okokyf.alarmas where idPersona=" + persona.getIdPersona() + "");
                        } else {
                            st.executeUpdate("INSERT INTO sozsu9g190okokyf.personas(apellido, nombre, telefono, tipo, email) VALUES ('"
                                    + persona.getApellido() + "','"
                                    + persona.getNombre() + "','"
                                    + persona.getTelefono() + "','"
                                    + persona.getTipo() + "','"
                                    + persona.getEmail() + "')");
                            ResultSet rs3 = st.executeQuery("SELECT idPersona " +
                                    "FROM sozsu9g190okokyf.personas where telefono='" + persona.getTelefono() + "'");
                            while (rs3.next()) {
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
                    QueryAlarma = "INSERT INTO sozsu9g190okokyf.alarmas(idPersona, nombre, urlTono, mensaje, distanciaActivacion, volumen)" +
                            "VALUES(" + persona.getIdPersona() + ",'" +
                            alarma.getNombre() + "', '" +
                            alarma.getUrlTono() + "', '" +
                            alarma.getMensaje() + "', " +
                            alarma.getDistanciaActivacion() + ", " +
                            alarma.getVolumen() + ");";
                    st.executeUpdate(QueryAlarma);
                    break;
                case "selectSMS":

                        ResultSet rsSMS = st.executeQuery("SELECT * FROM personas WHERE tipo = 'contacto' and email = '" + persona.getEmail() + "'");

                        result2 = " ";
                        while (rsSMS.next()) {
                            persona.setIdPersona(rsSMS.getInt("idPersona"));
                            persona.setApellido(rsSMS.getString("apellido"));
                            persona.setNombre(rsSMS.getString("nombre"));
                            persona.setTelefono(rsSMS.getString("telefono"));
                            persona.setTipo(rsSMS.getString("tipo"));
                            persona.setEmail(rsSMS.getString("email"));
                        }
                        if (persona.getIdPersona() >= 0) {
                            ResultSet rsSMS2 = st.executeQuery("SELECT idAlarma, nombre, urlTono, mensaje, distanciaActivacion, volumen FROM alarmas WHERE idPersona = " + persona.getIdPersona() + "");

                            while (rsSMS2.next()) {
                                alarma.setIdAlarma(rsSMS2.getInt("idAlarma"));
                                alarma.setNombre(rsSMS2.getString("nombre"));
                                alarma.setUrlTono(rsSMS2.getString("urlTono"));
                                alarma.setMensaje(rsSMS2.getString("mensaje"));
                                alarma.setDistanciaActivacion(rsSMS2.getInt("distanciaActivacion"));
                                alarma.setVolumen(rsSMS2.getInt("volumen"));
                            }
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
           case "selectSMS":
               if (!persona.getTelefono().trim().isEmpty()) {
                   String phone = persona.getTelefono().replaceAll("[-+/ ]", "").trim();
                   String text = alarma.getMensaje().trim();
                   try {
      //                 int control = Integer.parseUnsignedInt(phone);
                       if (phone.length() > 10) {
                           phone = phone.substring(phone.length() - 10);
                       } else {
                           SmsManager sms = SmsManager.getDefault();
                           sms.sendTextMessage(phone, null, text, null, null);
                           Toast.makeText(context, "Se enviará SMS al número " + phone.trim(), Toast.LENGTH_LONG).show();
                       }
                   } catch (NumberFormatException e) {
                       Toast.makeText(context, "El numero de teléfono contiene valores no numéricos", Toast.LENGTH_LONG).show();
                   } catch (Exception ex) {
                       Toast.makeText(context, "Corrija su contacto al formato 00000000", Toast.LENGTH_LONG).show();
                   }
//                Toast.makeText(context, "insertamos el usuario!!!", Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context, LoginActivity.class));
               }
           break;
           case "insert":

                   Intent intent = new Intent(context, MapsActivity.class);
                     intent.putExtra("TextoDestino", TextoDestino);
                     intent.putExtra("radio", radio);
                  context.startActivity(intent);
                break;
         default:
              break;
       }
    }
}