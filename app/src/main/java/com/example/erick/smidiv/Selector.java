package com.example.erick.smidiv;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Selector extends AppCompatActivity {

    FragmentManager fragmentManager =  getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private TextView mTextMessage;

    Intent uno = getIntent();

    public String usuario = new String();
    public String token = new String();
    public String vehiculo = new String();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment seleccion =  null;
            usuario = getIntent().getExtras().get("usuario").toString();
            token = getIntent().getExtras().get("token").toString();
            vehiculo=  getIntent().getExtras().get("vehiculo").toString();
            switch (item.getItemId()) {
                case R.id.inicio:
                    seleccion = Inicio.newInstance(usuario,token,vehiculo);
                    break;
                case R.id.estatus:
                    seleccion = Estatus.newInstance(usuario,token,vehiculo);
                    break;
                case R.id.alerta:
                    seleccion = Alerta.newInstance(usuario,token,vehiculo);
                    break;
                case R.id.ubicacion:
                    seleccion = Ubicacion.newInstance(usuario,token,vehiculo);
                    break;
                case R.id.configuracion:
                    seleccion = Configuracion.newInstance(usuario,token,vehiculo);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, seleccion);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        final RequestQueue queue = Volley.newRequestQueue(Selector.this);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager1 = getSupportFragmentManager();
        Bundle arguments = new Bundle();
        Log.d("usuario", getIntent().getExtras().get("usuario").toString());
        arguments.putString("usuario",getIntent().getExtras().get("usuario").toString());
        arguments.putString("token",getIntent().getExtras().get("token").toString());
        arguments.putString("vehiculo",getIntent().getExtras().get("vehiculo").toString());
        Inicio inicio = new Inicio();
        inicio.setArguments(arguments);
        FragmentTransaction trans =  fragmentManager1.beginTransaction();
        trans.replace(R.id.container,inicio).commit();
    }

}
