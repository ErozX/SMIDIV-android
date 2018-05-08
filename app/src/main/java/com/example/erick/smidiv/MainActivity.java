package com.example.erick.smidiv;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {




// Add the request to the RequestQueue.
    int contador = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button inicio = (Button) findViewById(R.id.button);
        TextView registrar = (TextView) findViewById(R.id.textView7);
        final TextView cambiarcon= (TextView) findViewById(R.id.textView4);
        cambiarcon.setVisibility(View.GONE);
        final EditText usuario = (EditText) findViewById(R.id.editText);
        final EditText contrasena = (EditText) findViewById(R.id.editText2);
        final RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final String url ="http://192.168.1.69:10010/login";
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int con=0;
                if (usuario.getText().toString().length() == 0 || contrasena.getText().toString().length() == 0) {
                    Toast.makeText(MainActivity.this, "Por favor llena los datos", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("username", usuario.getText().toString());
                        json.put("password", contrasena.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final int[] finalCon = {con};
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
                                            Intent nueva = new Intent(MainActivity.this, Selector.class);

                                            nueva.putExtra("token", response.get("token").toString());
                                            nueva.putExtra("usuario",usuario.getText().toString());
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
                            if (contador >=3){
                                cambiarcon.setVisibility(View.VISIBLE);
                            }
                            contador +=1;
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                            VolleyLog.d("error", "Error: " + error.getMessage());
                        }
                    });
                    queue.add(request);
                }
            }
        });
        cambiarcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CambiarContrasena.class));
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
