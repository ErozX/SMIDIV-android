package com.example.erick.smidiv;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;

public class AgregarAlarma extends AppCompatActivity {
    String tipoalarma = new String();
    public String ubicacion = new String();
    public Boolean est =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alarma);
        final String url = "http://smidiv.javiersl.com:10010/alarma";
        final RequestQueue cola = Volley.newRequestQueue(AgregarAlarma.this);
        EditText placas = (EditText) findViewById(R.id.editText4);
        final EditText rango = (EditText) findViewById(R.id.rangoKil);
        final EditText rango_inicio = (EditText) findViewById(R.id.Rango_inicio);
        final EditText rango_fin = (EditText) findViewById(R.id.Rango_fin);

        rango_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                boolean es24 = FALSE;
                TimePickerDialog hora = new TimePickerDialog(AgregarAlarma.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        rango_inicio.setText(timePicker.getHour()+":"+timePicker.getMinute());
                    }
                },hour, minute, es24);
                hora.setTitle("Selecciona una hora");
                hora.show();
            }
        });
        rango_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime1 = Calendar.getInstance();
                final int hour1 = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
                final int minute1 = mcurrentTime1.get(Calendar.MINUTE);
                boolean es24 = FALSE;
                TimePickerDialog hora1 = new TimePickerDialog(AgregarAlarma.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        rango_fin.setText(timePicker.getHour()+":"+timePicker.getHour());
                    }
                },hour1, minute1, es24);
                hora1.setTitle("Selecciona una hora");
                hora1.show();
            }
        });
        Button registrar = (Button) findViewById(R.id.button7);
        final ListView lista = (ListView) findViewById(R.id.listview1);

        final String seleccion = new String();
        final Switch estado = (Switch) findViewById(R.id.switch1);
        estado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                est = b;
            }
        });

        final String url1 = "http://smidiv.javiersl.com:10010/ubicacionFav/"+getIntent().getStringExtra("usuario");

        final ArrayList<String> ubic = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url1, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.names().get(0).equals("success")) {
                                ArrayList<ubicacionitem> ubicacion = new ArrayList<>();
                                Log.d("respuesta", "onResponse: "+response);
                                for (int i = 0; i < response.getJSONObject("response").getJSONArray("ubicaciones").length(); i++) {
                                    JSONObject info = response.getJSONObject("response").getJSONArray("ubicaciones").getJSONObject(i);
                                    Log.d("contador", "onResponse: " + i);
                                    ubicacion.add(new ubicacionitem(info.get("nombre").toString(), info.getJSONObject("ubicacion").get("lat").toString().substring(0,5), info.getJSONObject("ubicacion").get("lng").toString().substring(0,5)));
                                    }

                                Adaptador1 ad = new Adaptador1(AgregarAlarma.this, ubicacion);

                                Log.d("tamaño", "tamaño de ubicacion " + ubicacion.size());
                                lista.setAdapter(ad);


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
        //estado.setOnCheckedChangeListener(ne);
        cola.add(request);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ubicacionitem sel = (ubicacionitem) adapterView.getItemAtPosition(i);

                ubicacion = sel.getNombre();
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ubicacion.length()!=0&estado.getText().length()!=0&rango.getText().length()!=0&rango_inicio.getText().length()!=0&rango_fin.getText().length()!=0){
                    JSONObject json = new JSONObject();
                    try {
                        json.put("username", getIntent().getExtras().getString("usuario").toString());
                        json.put("vehiculo", getIntent().getExtras().getString("vehiculo").toString());
                        json.put("ubicacionFav", ubicacion);
                        json.put("estado",est);
                        /*JSONObject on = new JSONObject();

                        on.put("rango", Integer.valueOf(rango.getText().toString()));

                        json.put("rangoDistancia", on);*/
                        JSONObject un = new JSONObject();
                        un.put("inicio", rango_inicio.getText().toString());
                        un.put("fin", rango_fin.getText().toString());
                        json.put("rangoHorario",un);
                        Log.d("prro json", "onClick: " + json);
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
                                            Toast.makeText(getApplicationContext(), "Alarma guardada", Toast.LENGTH_SHORT).show();

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
                }
                else {
                    Toast.makeText(AgregarAlarma.this, "Los datos campos deben de estar completos", Toast.LENGTH_SHORT).show();
                }


                Toast.makeText(AgregarAlarma.this, "la ubicacion", Toast.LENGTH_SHORT).show();
            }
        });

    }



}