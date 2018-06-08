package com.example.erick.smidiv;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Calendar;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Button b1 = (Button) findViewById(R.id.button7);
        final EditText fecha = (EditText) findViewById(R.id.editText7) ;
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(Registrar.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        fecha.setText(""+ selectedyear  + "/" +  selectedday + "/" + selectedmonth);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        final String url ="http://smidiv.javiersl.com:10010/user";
        final EditText usuario = (EditText) findViewById(R.id.editText4);
        final EditText contrasena = (EditText) findViewById(R.id.editText5);
        final EditText recontrasena = (EditText) findViewById(R.id.editText6);
        final EditText mail = (EditText) findViewById(R.id.editText3);
        final RequestQueue cola = Volley.newRequestQueue(Registrar.this);

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(usuario.getText().toString().length()!=0&contrasena.getText().toString().length()!=0&recontrasena.getText().toString().length()!=0&fecha.getText().toString().length()!=0&mail.getText().toString().length()!=0) {
                            if (contrasena.getText().toString().equals(recontrasena.getText().toString())) {
                                JSONObject json = new JSONObject();
                                try {
                                    json.put("username", usuario.getText().toString());
                                    json.put("email", mail.getText().toString());
                                    json.put("fecha", fecha.getText().toString());
                                    json.put("password", contrasena.getText().toString());
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
                                                        Intent nuevo = new Intent(Registrar.this, MainActivity.class);
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
                                    }
                                });
                                cola.add(request);
                            } else {
                                Toast.makeText(Registrar.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Registrar.this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }
}
