package com.example.erick.smidiv;

/**
 * Created by erick on 9/05/18.
 */

public class ubicacionitem {
    private String nombre;
    private String lat;
    private String lon;


    public String getNombre(){
        return nombre;
    }
    public ubicacionitem(String nombre, String lat, String lon){
        this.nombre = nombre;
        this.lat = lat;
        this.lon =  lon;
    }
    public String getLat(){
        return lat;
    }

    public String getLon(){
        return lon;
    }
}
