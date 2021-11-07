package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ar.com.despertador.data.model.Alarma;


public class DataAlarmaActivity extends AsyncTask<String, Void, String> {


    private ListView lvAlarma;
    private Context context;

    private static String result2;
    private static ArrayList<Alarma> listaAlarma = new ArrayList<Alarma>();

    //Recibe por constructor el textview
    //Constructor
    public DataAlarmaActivity(ListView lv, Context ct) {
        lvAlarma = lv;
        context = ct;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataBD.urlMySQL, DataBD.user, DataBD.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM alarmas");
            result2 = " ";

            Alarma alarma;
            while (rs.next()) {
                alarma = new Alarma();
                alarma.setIdAlarma(rs.getInt("idAlarma"));
                alarma.setIdPersona(rs.getInt("idPersona"));
                alarma.setNombre(rs.getString("nombre"));
                alarma.setUrlTono(rs.getString("urlTono"));
                alarma.setMensaje(rs.getString("mensaje"));
                alarma.setDistanciaActivacion(rs.getInt(rs.getString("distanciaActivacion")));
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
        AlarmaAdapter adapter = new AlarmaAdapter(context, listaAlarma);
        lvAlarma.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ((AlarmaAdapter)lvAlarma.getAdapter()).notifyDataSetChanged();
    }
}

