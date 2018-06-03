package com.example.erick.smidiv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AgregarUbicacion extends AppCompatActivity implements OnMapReadyCallback {
    public int pruebas;
    public LatLng ubicacion;
    public GoogleMap mapa;

    PlaceAutocompleteFragment placeAutoComplete;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ubicacion);
        final RequestQueue queue = Volley.newRequestQueue(AgregarUbicacion.this);
        final String url ="http://192.168.1.64:10010/ubicacionFav";
        //final String url ="http://192.168.1.199:10010/ubicacionFav";
        final RequestQueue cola = Volley.newRequestQueue(AgregarUbicacion.this);
        final Button reg  = (Button) findViewById(R.id.reg_ubicacion);
        final EditText nombre = (EditText) findViewById(R.id.editText4);
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.auto);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Log.d("Maps", "Place selected: " + place.getName());
                ubicacion = place.getLatLng();
                addMarker(place);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });


            MapFragment mapa = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapa);
        mapa.getMapAsync(this);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (ubicacion!=null) {
                        JSONObject json = new JSONObject();
                        try {
                            json.put("idUsuario", getIntent().getExtras().getString("usuario").toString());
                            json.put("nombre",nombre.getText().toString());
                            JSONObject ubi = new JSONObject();

                            ubi.put("lat", ubicacion.latitude);
                            ubi.put("lng", ubicacion.longitude);
                            json.put("ubicacion", ubi);

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
                                                Toast.makeText(getApplicationContext(), "Ubicacion guardada", Toast.LENGTH_SHORT).show();
                                                //irainicio();
                                                Intent nuevo = new Intent(AgregarUbicacion.this, Selector.class);
                                                nuevo.putExtra("usuario",getIntent().getExtras().get("usuario").toString());
                                                nuevo.putExtra("vehiculo",getIntent().getExtras().get("vehiculo").toString());
                                                nuevo.putExtra("token",getIntent().getExtras().get("token").toString());
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
                                Toast.makeText(AgregarUbicacion.this, "Error", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AgregarUbicacion.this, "la ubicacion" + ubicacion, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap map) {
        //LatLng sydney = new LatLng( 19.432608, -99.133209);

        mapa = map;
        mapa.getUiSettings().setMyLocationButtonEnabled(true);
        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(latLng);
                markerOptions.title("Ubicacion");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                mapa.addMarker(markerOptions);
                ubicacion = latLng;
            }
        });
    }
    public void addMarker(Place p){

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(p.getLatLng());
        markerOptions.title(p.getName()+"");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        mapa.addMarker(markerOptions);
        mapa.moveCamera(CameraUpdateFactory.newLatLng(p.getLatLng()));
        mapa.animateCamera(CameraUpdateFactory.zoomTo(13));
        ubicacion=p.getLatLng();
    }
}
