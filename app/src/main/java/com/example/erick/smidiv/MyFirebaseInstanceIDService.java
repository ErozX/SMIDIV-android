package com.example.erick.smidiv;


import android.content.Intent;
import android.util.Log;
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
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erick on 10/05/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    private static final String TAG ="MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);

        Log.d(TAG, "onTokenRefresh: "+refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "sendRegistrationToServer: "+token);
        final String url ="http://192.168.1.69:10010/fire";
        JSONObject json = new JSONObject();
        try {
            json.put("id", token);

            Log.d("prro json", "onClick: "+json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //final String url ="http://192.168.1.199:10010/ubicacionFav";
        final RequestQueue cola = Volley.newRequestQueue(this);
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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        cola.add(request);
        // TODO: Implement this method to send token to your app server.
    }

}
