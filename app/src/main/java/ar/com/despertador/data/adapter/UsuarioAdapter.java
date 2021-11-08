package ar.com.despertador.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.despertador.R;
import ar.com.despertador.data.model.Alarma;

public class UsuarioAdapter extends ArrayAdapter<Alarma> {

    public UsuarioAdapter(Context context, List<Alarma> objetos) {
        super(context, R.layout.list_template, objetos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_template, null);

        TextView tvNombre = (TextView) item.findViewById(R.id.nombreLT);
        TextView tvStock = (TextView) item.findViewById(R.id.stockLT);

        tvNombre.setText(getItem(position).getNombre()+"");
        tvStock.setText(getItem(position).getStock());

        return item;
    }
}