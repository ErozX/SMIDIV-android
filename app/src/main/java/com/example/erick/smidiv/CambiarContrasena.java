package com.example.erick.smidiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CambiarContrasena extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);
        final TextView email = (TextView) findViewById(R.id.textView5);
        Button cambiar = (Button) findViewById(R.id.button9);
        final RequestQueue queue = Volley.newRequestQueue(CambiarContrasena.this);
        final String url ="http://smidiv.javiersl.com:10010/mail";

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CambiarContrasena.this, "Prueba", Toast.LENGTH_SHORT).show();
                if(email.getText().toString().length()==0){
                    Toast.makeText(CambiarContrasena.this, "El campo debe de contener información", Toast.LENGTH_SHORT).show();
                }
                else{
                    JSONObject json = new JSONObject();
                    try {
                        json.put("username", email.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                            url, json,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.names().get(0).equals("success")) {
                                            StringRequest request;
                                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                            //irainicio();
                                            Intent nueva = new Intent(CambiarContrasena.this, MainActivity.class);
                                            startActivity(nueva);
                                        } else {
                                            Log.d("error", "error en la respuesta");
                                            Toast.makeText(getApplicationContext(), response.getString("Usuario o contraseña incorrectos vuelve a intentarlo"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                            VolleyLog.d("error", "Error: " + error.getMessage());
                        }
                    });
                    queue.add(request);
                }
            }
        });
    }
}
