package com.example.erick.smidiv;

/**
 * Created by erick on 15/05/18.
 */

public class obdItem {
    private String tipo;
    private String valor;



    public obdItem(String tipo, String valor){
        this.tipo = tipo;
        this.valor = valor;
    }
    public String getValor(){
        return valor;
    }
    public String getTipo(){
        return tipo;
    }


}
