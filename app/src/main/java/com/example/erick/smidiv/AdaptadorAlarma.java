package com.example.erick.smidiv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by erick on 20/05/18.
 */

public class AdaptadorAlarma extends BaseAdapter {

    private Context context;
    private ArrayList<alarmaItem> listItems;
    public AdaptadorAlarma (Context context, ArrayList<alarmaItem> listItemst){
        this.context = context;
        this.listItems = listItemst;
    }

    @Override
    public int getCount(){
        return  listItems.size();
    }

    @Override
    public Object getItem(int position){
        return listItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        alarmaItem item = (alarmaItem) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.alarmaitem,null);

        TextView estado = view.findViewById(R.id.estado);
        TextView rango_dist =  view.findViewById(R.id.rango_dist);
        TextView rango_ini = view.findViewById(R.id.r_inicio);
        TextView rango_fin = view.findViewById(R.id.r_fin);
        estado.setText(item.getEstado());
        rango_dist.setText(item.getRango_dist());
        rango_ini.setText(item.getRango_fin());
        rango_fin.setText(item.getRango_fin());
        return view;
    }
}
