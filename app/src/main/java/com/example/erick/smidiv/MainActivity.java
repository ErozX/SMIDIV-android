package com.example.erick.smidiv;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue = Volley.newRequestQueue(this);
    String url ="http://www.google.com";

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    try{
                        JSONObject respuesta = new JSONObject(response);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });

// Add the request to the RequestQueue.
    queue.add(stringRequest);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button inicio = (Button) findViewById(R.id.button);
        TextView registrar = (TextView) findViewById(R.id.textView7);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Haz iniciado sesion", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Selector.class));
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Por Favor llenar todos los datos", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Registrar.class));
            }
        });
    }
}
