package ar.com.despertador.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entidad.Articulos;

import java.util.List;

//public class ArticuloAdapter extends ArrayAdapter<Articulos> {
//
//    public ArticuloAdapter(Context context, List<Articulos> objetos) {
//        super(context, R.layout.list_template, objetos);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View item = inflater.inflate(R.layout.list_template, null);
//
//        TextView tvNombre = (TextView) item.findViewById(R.id.nombreLT);
//        TextView tvStock = (TextView) item.findViewById(R.id.stockLT);
//
//        tvNombre.setText(getItem(position).getNombre()+"");
//        tvStock.setText(getItem(position).getStock());
//
//        return item;
//    }
}