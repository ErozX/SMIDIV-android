package com.example.erick.smidiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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

public class AgregarAlarma extends AppCompatActivity {
    String tipoalarma = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alarma);
        final String url ="http://192.168.1.69:10010/alarma";
        //final String url ="http://192.168.1.199:10010/ubicacionFav";
        final RequestQueue cola = Volley.newRequestQueue(AgregarAlarma.this);
        //RadioGroup grupo = (RadioGroup) findViewById(R.id.)
        EditText placas = (EditText) findViewById(R.id.editText4);
        Button registrar = (Button) findViewById(R.id.button7);
        RadioGroup rd = (RadioGroup) findViewById(R.id.radiogroup1);
        final Switch estado = (Switch) findViewById(R.id.switch1);

        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton opcion = (RadioButton) findViewById(i);
                tipoalarma = opcion.getText().toString();
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    JSONObject json = new JSONObject();
                    try {
                        json.put("idUsuario", getIntent().getExtras().getString("usuario").toString());
                        json.put("nombre",tipoalarma.toString());
                        json.put("tipo",estado.getText().toString());

                        Log.d("prro json", "onClick: "+json);
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
                                            Intent nuevo = new Intent(AgregarAlarma.this, Selector.class);
                                            nuevo.putExtra("token", response.names().get(0).equals("token"));
                                            startActivity(nuevo);

                                        } else {
                                            Log.d("error", "error en la respuesta");
                                            Toast.makeText(getApplicationContext(), response.getString("Ocurrio un error ingresando los datos"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("error", "Error: " + error.getMessage());
                            Toast.makeText(AgregarAlarma.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            //headers.put("Content-Type", "application/json");
                            headers.put("X-API-KEY", getIntent().getStringExtra("token").toString());
                            return headers;
                        }
                    };
                    cola.add(request);

                Toast.makeText(AgregarAlarma.this, "la ubicacion" , Toast.LENGTH_SHORT).show();
            }
        });

    }
}
