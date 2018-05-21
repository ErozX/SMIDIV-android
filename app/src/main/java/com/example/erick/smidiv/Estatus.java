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
 * {@link Estatus.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Estatus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estatus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    public ArrayList<obdItem> obds = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private OnFragmentInteractionListener mListener;

    public Estatus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Estatus.
     */
    // TODO: Rename and change types and number of parameters
    public static Estatus newInstance(String param1, String param2, String param3) {
        Estatus fragment = new Estatus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.fragment_estatus, container, false);
        // Inflate the layout for this fragment

        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String vehiculo =  "ABC123";
        final String url ="http://192.168.1.69:10010/OBD" +
                "/"+vehiculo;
        final ListView lista  = (ListView) vista.findViewById(R.id.estus);
        ArrayList<ubicacionitem> ubic = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getJSONObject("response").getJSONArray("OBD").length()==0){
                                Toast.makeText(getContext(), "Todavia no tenemos información", Toast.LENGTH_SHORT).show();
                            }
                            for (int i = 0; i <response.getJSONObject("response").getJSONArray("OBD").length(); i++) {
                                JSONObject info =  response.getJSONObject("response").getJSONArray("OBD").getJSONObject(i);
                                Log.d("contador", "onResponse: "+i);
                                obds.add(new obdItem(info.get("tipo").toString(),info.get("valor").toString()));
                                //añadeubicacion(new ubicacionitem("Casa",info.getJSONObject("ubicacion").get("lat").toString(),info.getJSONObject("ubicacion").get("lon").toString()));
                            }

                            AdaptadorOBD ad = new AdaptadorOBD(getContext(),obds);

                            Log.d("tamaño", "tamaño de ubicacion "+obds.size());
                            lista.setAdapter(ad);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error", "Error: " + error.getMessage());
                Toast.makeText(getContext(), "Error obteniendo conexion", Toast.LENGTH_SHORT).show();
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
            /*
            throw new RuntimeException(context.toString()
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
