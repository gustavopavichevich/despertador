package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ar.com.despertador.data.adapter.AlarmaAdapter;
import ar.com.despertador.data.model.Alarma;
import ar.com.despertador.data.model.Persona;


public class DataAlarmaActivity extends AsyncTask<String, Void, String> {


    private final ListView lvAlarma;
    private final Context context;
    private final Alarma alarma;
    private final Persona persona;

    public DataAlarmaActivity(Persona pe, Alarma al, ListView lv, Context ct) {
        lvAlarma = lv;
        context = ct;
        alarma = al;
        persona = pe;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataBD.urlMySQL, DataBD.user, DataBD.pass);
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO personas VALUES ("
                    + persona.getApellido() + ","
                    + persona.getNombre() + ","
                    + persona.getTelefono() + ","
                    + persona.getTelefono() + ","
                    + persona.getTipo());
            st.executeUpdate("INSERT INTO alarmas VALUES ("
                    + alarma.getIdPersona() + ","
                    + alarma.getNombre() + ","
                    + alarma.getUrlTono() + ","
                    + alarma.getMensaje() + ","
                    + alarma.getDistanciaActivacion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @Override
    protected void onPostExecute(String response) {
        AlarmaAdapter adapter = new AlarmaAdapter(context, listaAlarmas);
        lvAlarma.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ((AlarmaAdapter) lvAlarma.getAdapter()).notifyDataSetChanged();
    }
}

