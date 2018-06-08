package com.example.erick.smidiv;

/**
 * Created by erick on 21/05/18.
 */

public class alarmaItem {
        private String estado;
        private String rango_inicio;
        private String rango_dist;
        private String rango_fin;
        private String id;


        public alarmaItem(String Id, String estado, String rango_dist, String rango_inicio,String rango_fin){
            this.estado = estado;
            this.rango_dist = rango_dist;
            this.rango_inicio = rango_inicio;
            this.rango_fin = rango_fin;
            this.id = Id;
        }
        public String getId(){return  id;}
        public String getEstado(){return  estado;}
        public String getRango_inicio(){return rango_inicio;}
        public String getRango_dist(){return rango_dist;}
        public String getRango_fin(){return rango_fin;}

}
