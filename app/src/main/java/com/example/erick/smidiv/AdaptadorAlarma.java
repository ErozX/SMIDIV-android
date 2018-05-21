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
        obdItem item = (obdItem) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.obd_item,null);
        TextView clave = view.findViewById(R.id.clave);
        TextView valor = view.findViewById(R.id.valor);
        clave.setText(item.getTipo());
        valor.setText(item.getValor());

        return view;
    }
}
