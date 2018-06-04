package com.example.erick.smidiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);
        final EditText contra = (EditText) findViewById(R.id.editText11);
        final EditText recontra = (EditText) findViewById(R.id.editText10);
        final EditText email = (EditText) findViewById(R.id.editText9);
        final EditText fecha = (EditText) findViewById(R.id.editText13);
        Button actualizar = (Button) findViewById(R.id.button4);
        final String url ="http://192.168.1.64:10010/user/"+getIntent().getExtras().get("usuario").toString();
        final RequestQueue cola = Volley.newRequestQueue(ActualizarUsuario.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("usuario", response.toString());
                        try {
                            email.setText(response.getJSONObject("user").getString("email").toString());
                            //fecha.setText(response.getString(""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                                /*
                                try {
                                    if (response.names().get(0).equals("success")) {
                                        StringRequest request;
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                        //irainicio();
                                        Intent nuevo = new Intent(Registrar.this, MainActivity.class);
                                        nuevo.putExtra("token", response.names().get(0).equals("token"));
                                        startActivity(nuevo);

                                    } else {
                                        Log.d("error", "error en la respuesta");
                                        Toast.makeText(getApplicationContext(), response.getString("Ocurrio un error ingresando los datos"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error", "Error: " + error.getMessage());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("X-API-KEY",getIntent().getExtras().get("token").toString());
                return headers;
            }
        };
        cola.add(request);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contra.getText().toString().length()!=0&&recontra.getText().toString().length()!=0&&email.getText().toString().length()!=0&&fecha.getText().toString().length()!=0){
                    if(contra.getText().toString().length()>8){


                    }else {
                        Toast.makeText(ActualizarUsuario.this, "La contraseña debe ser de al menos 8 digitos", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ActualizarUsuario.this, "Los campos deben de tener información", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
