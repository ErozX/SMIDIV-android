package com.example.erick.smidiv;

/**
 * Created by erick on 21/05/18.
 */

public class alarmaItem {
        private String nombre;
        private String estado;
        private String lat;
        private String lon;


        public alarmaItem(String nombre, String estado, String lat, String lon){
            this.nombre = nombre;
            this.estado = estado;
            this.lat = lat;
            this.lon = lon;
        }
        public String getNombre(){return nombre;}
        public String getEstado(){return  estado;}
        public String getLat(){return lat;}
        public String getLon(){return lon;}

}
