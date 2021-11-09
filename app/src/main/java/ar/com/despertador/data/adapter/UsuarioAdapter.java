package ar.com.despertador.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.despertador.R;
import ar.com.despertador.data.model.Alarma;
import ar.com.despertador.data.model.Usuario;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    public UsuarioAdapter(Context context, ArrayList<Usuario> objetos) {
        super(context, R.layout.activity_listado_contactos, objetos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.activity_listado_contactos, null);

  /*      TextView tvNombre = (TextView) item.findViewById(R.id.nombreLT);
        TextView tvStock = (TextView) item.findViewById(R.id.stockLT);

        tvNombre.setText(getItem(position).getNombre()+"");
        tvStock.setText(getItem(position).getStock());*/

        return item;
    }
}