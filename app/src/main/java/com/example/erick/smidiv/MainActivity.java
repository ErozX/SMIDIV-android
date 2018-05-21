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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String SMTOKEN= new String();



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
        final String url ="http://smidiv.javiersl.com:10010/login";

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int con=0;
                //Log.d("Firebase prro", "onClick: "+ FirebaseInstanceId.getInstance().getToken().toString());
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
                                            FirebaseMessaging.getInstance().subscribeToTopic(usuario.getText().toString());
                                            Log.d("Firebase prro", "onResponse: "+FirebaseMessaging.getInstance().toString() );

                                            Log.d("login", "onResponse: "+response.getString("token").toString());
                                            /*Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                            //irainicio();*/

                                            final Intent nueva = new Intent(MainActivity.this, Selector.class);
                                            nueva.putExtra("token", response.get("token").toString());
                                            final String Tok = response.get("token").toString();
                                            nueva.putExtra("usuario",usuario.getText().toString());

                                            final RequestQueue cola = Volley.newRequestQueue(MainActivity.this);
                                            Toast.makeText(MainActivity.this, "login", Toast.LENGTH_SHORT).show();
                                            String direccion = "http://192.168.1.69:10010/vehicle/"+usuario.getText().toString();
                                            JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,
                                                    direccion, null,
                                                    new Response.Listener<JSONObject>() {

                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            Log.d("vehiculo", "RESPUESTA");
                                                            try {
                                                                if (response.names().get(0).equals("sucess")) {

                                                                    Intent main = new Intent (MainActivity.this,Selector.class);
                                                                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                                                    main.putExtra("token",Tok);
                                                                    main.putExtra("usuario",usuario.getText().toString());
                                                                    main.putExtra("vehiculo",response.getJSONObject("response").getJSONObject("vehiculo").get("placas").toString());
                                                                    startActivity(main);
                                                                } else {
                                                                    Log.d("error", "error vehiculo");
                                                                    Intent main = new Intent (MainActivity.this,Selector.class);
                                                                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                                                    main.putExtra("token",Tok);
                                                                    main.putExtra("usuario",usuario.getText().toString());
                                                                    main.putExtra("vehiculo","1");
                                                                    startActivity(main);
                                                                    Toast.makeText(getApplicationContext(), response.getString("error prro"), Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                                                    VolleyLog.d("error", "Error: " + error.getMessage());
                                                }}) {

                                                @Override
                                                public Map<String, String> getHeaders() throws AuthFailureError {
                                                    HashMap<String, String> headers = new HashMap<String, String>();
                                                    //headers.put("Content-Type", "application/json");
                                                    headers.put("X-API-KEY", Tok);
                                                    return headers;
                                                }
                                            };
                                            cola.add(request1);

                                            //verificar


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
                    Log.d("token antes de mandarlo", "onClick: "+SMTOKEN);

                }
            }
        });
        //Log.d("Token", SMTOKEN);
        //while(SMTOKEN.length()!=0){




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
