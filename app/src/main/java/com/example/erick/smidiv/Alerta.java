package com.example.erick.smidiv;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Alerta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Alerta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alerta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ArrayList<ubicacionitem> ubicacion = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Alerta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alerta.
     */
    // TODO: Rename and change types and number of parameters
    public static Alerta newInstance(String param1, String param2) {
        Alerta fragment = new Alerta();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vista = inflater.inflate(R.layout.fragment_alerta, container, false);
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String vehiculo =  "ABC123";
        final String url ="http://192.168.1.69:10010/ubicacion/"+vehiculo;
        final ListView lista  = (ListView) vista.findViewById(R.id.alarmas);
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
                                ubicacion.add(new ubicacionitem("casa",info.getJSONObject("ubicacion").get("lat").toString(),info.getJSONObject("ubicacion").get("lon").toString()));
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
        Button agregaralarma = (Button) vista.findViewById(R.id.button6);
        agregaralarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(getContext(),AgregarAlarma.class);
                nuevo.putExtra("usuario",getArguments().getString(ARG_PARAM1).toString());
                nuevo.putExtra("token",getArguments().getString(ARG_PARAM2).toString());
                startActivity(nuevo);
            }
        });

        // Inflate the layout for this fragment
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

            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
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
