package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ar.com.despertador.data.adapter.UsuarioAdapter;
import ar.com.despertador.data.model.Alarma;
import ar.com.despertador.data.model.Usuario;


public class DataUsuarioActivity extends AsyncTask<String, Void, String> {


    private ListView lvUsuarios;
    private Context context;
    private String sentenciaSQL= null;

    private static String result2;
    private static ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

    //Recibe por constructor el textview
    //Constructor
    public DataUsuarioActivity(ListView lvUsuarios, Context ct) {
        this.lvUsuarios = lvUsuarios;
        this.context = ct;
    }
    public DataUsuarioActivity(ListView lvUsuarios, Context ct, String sentenciaSQL) {
        this.lvUsuarios = lvUsuarios;
        this.context = ct;
        this.sentenciaSQL = sentenciaSQL;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataBD.urlMySQL, DataBD.user, DataBD.pass);
            Statement st = con.createStatement();
            if(sentenciaSQL.equals(null))
                sentenciaSQL = "SELECT * FROM usuarios";
            ResultSet rs = st.executeQuery(sentenciaSQL);
            result2 = " ";

            Usuario usuario;
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContrasena(rs.getString("contrasena"));
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
        UsuarioAdapter adapter = new UsuarioAdapter(context, listaUsuarios);
        lvUsuarios.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ((UsuarioAdapter)lvUsuarios.getAdapter()).notifyDataSetChanged();
    }
}

