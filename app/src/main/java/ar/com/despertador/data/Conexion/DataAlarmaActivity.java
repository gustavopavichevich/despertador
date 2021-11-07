package ar.com.despertador.data.Conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ar.com.despertador.data.model.Alarma;


public class DataAlarmaActivity extends AsyncTask<String, Void, String> {


    private ListView lvArticulo;
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
            ResultSet rs = st.executeQuery("SELECT * FROM articulos");
            result2 = " ";

//            Articulos articulo;
//            while (rs.next()) {
//                articulo = new Articulos();
//                articulo.setId(rs.getInt("id"));
//                articulo.setNombre(rs.getString("nombre"));
//                articulo.setStock(rs.getInt("stock"));
//                articulo.setCategoria(rs.getInt("categoria"));
//                listaArticulos.add(articulo);
//            }
//            response = "Conexion exitosa";
        } catch (Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
//        ArticuloAdapter adapter = new ArticuloAdapter(context, listaArticulos);
//        lvArticulo.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        ((ArticuloAdapter)lvArticulo.getAdapter()).notifyDataSetChanged();
    }
}

