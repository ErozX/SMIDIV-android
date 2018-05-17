package com.example.erick.smidiv;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ubicacion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ubicacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ubicacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public int pruebas;
    public ArrayList<ubicacionitem> ubicacion = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Ubicacion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ubicacion.
     */
    // TODO: Rename and change types and number of parameters
    public static Ubicacion newInstance(String param1, String param2) {
        Ubicacion fragment = new Ubicacion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.d("attach", "onCreate: "+mParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View vista = inflater.inflate(R.layout.fragment_ubicacion, container, false);
            if (ContextCompat.checkSelfPermission(getContext(),
                    ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("maP","HOLA");
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{ACCESS_FINE_LOCATION},
                            pruebas);

                }
            }

        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String vehiculo =  "ABC123";
        final String url ="http://192.168.1.69:10010/ubicacion/"+vehiculo;
        final ListView lista  = (ListView) vista.findViewById(R.id.listaubicacion);
        ArrayList<ubicacionitem> ubic = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i <response.getJSONObject("response").getJSONArray("ubicaciones").length(); i++) {
                                JSONObject info =  response.getJSONObject("response").getJSONArray("ubicaciones").getJSONObject(i);
                                Log.d("contador", "onResponse: "+i);
                                ubicacion.add(new ubicacionitem(String.valueOf(i),info.getJSONObject("ubicacion").get("lat").toString(),info.getJSONObject("ubicacion").get("lon").toString()));
                                //añadeubicacion(new ubicacionitem("Casa",info.getJSONObject("ubicacion").get("lat").toString(),info.getJSONObject("ubicacion").get("lon").toString()));
                            }

                            Adaptador1 ad = new Adaptador1(getContext(),ubicacion);

                            Log.d("tamaño", "tamaño de ubicacion "+ubicacion.size());
                            lista.setAdapter(ad);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                headers.put("X-API-KEY",getArguments().getString(ARG_PARAM2).toString());
                return headers;
            }
        };
        queue.add(request);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ubicacionitem sel = (ubicacionitem) adapterView.getItemAtPosition(i);
                String lat = sel.getLat();
                String lon = sel.getLon();
                Intent nuevo = new Intent(getContext(),DibujaMapa.class);
                nuevo.putExtra("lat",lat);
                nuevo.putExtra("lon",lon);
                startActivity(nuevo);
            }
        });


            //lista.setVisibility(View.INVISIBLE);
            Button agregaUbic = (Button) vista.findViewById(R.id.Ubi_agregaUbi);
            agregaUbic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nuevo = new Intent(getContext(),AgregarUbicacion.class);
                    nuevo.putExtra("usuario",getArguments().getString(ARG_PARAM1).toString());
                    nuevo.putExtra("token",getArguments().getString(ARG_PARAM2).toString());
                    startActivity(nuevo);
                }
            });

            return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void getUbicacion(){


    }

    public void añadeubicacion(ubicacionitem cosa){
        Log.d("Añadeubicacion", "lo hice");
        ubicacion.add(cosa);
        Log.d("ubicacion", "tamaño"+ubicacion.size());
    }
    public ArrayList<ubicacionitem> obtenerUbi(){
        return ubicacion;
    }
    public void borraubicacion(ubicacionitem cosa){

    }
}
