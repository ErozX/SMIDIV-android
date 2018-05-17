package com.example.erick.smidiv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by erick on 9/05/18.
 */

public class Adaptador1 extends BaseAdapter {
    private Context context;
    private ArrayList<ubicacionitem> listItems;
    public Adaptador1(Context context, ArrayList<ubicacionitem> listItemst){
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
        ubicacionitem item = (ubicacionitem) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item,null);
        TextView nombre = view.findViewById(R.id.textView8);
        TextView lat = view.findViewById(R.id.textView11);
        TextView lon = view.findViewById(R.id.textView9);
        nombre.setText(item.getNombre());
        lat.setText(item.getLat());
        lon.setText(item.getLon());


        return view;
    }




}
