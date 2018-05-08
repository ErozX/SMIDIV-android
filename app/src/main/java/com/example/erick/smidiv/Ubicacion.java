package com.example.erick.smidiv;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{ACCESS_FINE_LOCATION},
                        pruebas);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String vehiculo =  "ABC123";
        final String url ="http://192.168.1.69:10010/ubicacion/"+vehiculo;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    url,null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //validar la salida de el

                                TableLayout t1 = (TableLayout) vista.findViewById(R.id.tableLayout3);
                                TextView id ;
                                TextView fecha;
                                TextView accion;
                                TextView ubicacion;
                                for (int i = 0; i <response.getJSONObject("response").getJSONArray("ubicaciones").length(); i++) {
                                    JSONObject ubic =  response.getJSONObject("response").getJSONArray("ubicaciones").getJSONObject(i);
                                    Log.d("json"+ String.valueOf(i), ubic.getJSONObject("ubicacion").get("lat").toString());
                                    TableRow row= new TableRow(getContext());
                                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                    row.setLayoutParams(lp);
                                    id = new TextView(getContext());
                                    fecha = new TextView(getContext());
                                    accion = new TextView(getContext());
                                    ubicacion = new TextView(getContext());
                                    id.setText(String.valueOf(i));
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                    Date date = sdf.parse(ubic.get("fechaCreacion").toString());
                                    SimpleDateFormat año = new SimpleDateFormat("yyyyy-mm-dd");
                                    SimpleDateFormat hor = new SimpleDateFormat("hh:mm:ss");
                                    //SimpleDateFormat dt = new SimpleDateFormat(ubic.get("fechaCreacion").toString());
                                    fecha.setText(año.format(date));
                                    accion.setText(ubic.getJSONObject("ubicacion").get("lat").toString()+","+ubic.getJSONObject("ubicacion").get("lon").toString());
                                    ubicacion.setText("los cabos");
                                    row.addView(id);
                                    row.addView(fecha);
                                    row.addView(accion);
                                    t1.addView(row,i);
                                }
                                for (int i = 0 ;i <response.getJSONArray("response").length();i++){
                                    Log.d("json", response.getJSONArray("response").getString(1));
                                }

                                if (response.names().get(0).equals("success")) {
                                    StringRequest request;
                                    /*
                                    Intent nueva = new Intent(getContext(), Selector.class);

                                    startActivity(nueva);*/
                                } else {
                                    Log.d("error", "error en la respuesta");
                                    Toast.makeText(getContext(), response.getString("Usuario o contraseña incorrectos vuelve a intentarlo"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("error", "Error: " + error.getMessage());
                }
            }){

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Content-Type", "application/json");
                    headers.put("X-API-KEY",getArguments().getString(ARG_PARAM2).toString());
                    return headers;
                }
            };
            queue.add(request);


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
}
