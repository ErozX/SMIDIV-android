package com.example.erick.smidiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AgregarAutomovil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_automovil);
        final EditText placas = (EditText) findViewById(R.id.editText6);
        final EditText marca = (EditText) findViewById(R.id.editText3);
        final EditText modelo = (EditText) findViewById(R.id.editText5);
        final EditText SMIDIVID = (EditText) findViewById(R.id.editText7);
        final EditText nombre = (EditText) findViewById(R.id.editText4);
        final Button registrar = (Button) findViewById(R.id.button7);
        final RequestQueue cola = Volley.newRequestQueue(AgregarAutomovil.this);
        final String url ="http://192.168.1.64:10010/vehicle";

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(placas.length()==0&marca.length()==0&modelo.length()==0&SMIDIVID.length()==0){
                    Toast.makeText(AgregarAutomovil.this, "Error ", Toast.LENGTH_SHORT).show();
                }
                else{
                    JSONObject json = new JSONObject();
                    try {
                        json.put("username", getIntent().getExtras().getString("usuario").toString());
                        json.put("marca",marca.getText().toString());
                        json.put("modelo",modelo.getText().toString());
                        json.put("smidivID",SMIDIVID.getText().toString());
                        json.put("placas",placas.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                            url, json,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {



                                            Toast.makeText(getApplicationContext(), "Vehiculo guardado", Toast.LENGTH_SHORT).show();
                                            //irainicio();

                                            Intent nuevo = new Intent(AgregarAutomovil.this, MainActivity.class);
                                            startActivity(nuevo);



                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("error", "Error: " + error.getMessage());
                            Toast.makeText(AgregarAutomovil.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            //headers.put("Content-Type", "application/json");
                            Log.d("headers",getIntent().getExtras().getString("token").toString() );
                            headers.put("X-API-KEY", getIntent().getExtras().getString("token").toString());
                            return headers;
                        }
                    };
                    cola.add(request);
                }
                }
        });
    }
}
