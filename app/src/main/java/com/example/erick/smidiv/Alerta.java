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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
    private static final String ARG_PARAM3 = "param3";

    public ArrayList<alarmaItem> alarma = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;


    private OnFragmentInteractionListener mListener;

    public Alerta() {
        // Required empty public constructor
    }
    public String alerta = new String();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alerta.
     */
    // TODO: Rename and change types and number of parameters
    public static Alerta newInstance(String param1, String param2, String param3) {
        Alerta fragment = new Alerta();
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
        final View vista = inflater.inflate(R.layout.fragment_alerta, container, false);
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url ="http://smidiv.javiersl.com:10010/alarma/"+getArguments().getString(ARG_PARAM1).toString();
        final ListView lista  = (ListView) vista.findViewById(R.id.alarmas);
        final TextView nohay = (TextView) vista.findViewById(R.id.no_alerta);
        final Button actualizar = (Button) vista.findViewById(R.id.actu_ala);
        final Button eliminar  = (Button) vista.findViewById(R.id.borr_ala);
        actualizar.setVisibility(View.INVISIBLE);
        eliminar.setVisibility(View.INVISIBLE);
        ArrayList<ubicacionitem> ubic = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if(response.getJSONObject("response").getJSONArray("alarmas").length()==0){
                                Log.d("alerta", "onResponse: "+response.getJSONObject("response").getJSONArray("alarmas").length());
                                Toast.makeText(getContext(), "Todavia no tenemos información", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                nohay.setVisibility(View.INVISIBLE);
                                for (int i = 0; i <response.getJSONObject("response").getJSONArray("alarmas").length(); i++) {
                                    JSONObject info =  response.getJSONObject("response").getJSONArray("alarmas").getJSONObject(i);
                                    if(info.has("rangoHorario")){
                                        if(info.get("estado").toString().equals("true")){
                                            alarma.add(new alarmaItem(String.valueOf(i),"Encencida",info.getJSONObject("rangoHorario").get("inicio").toString(),info.getJSONObject("rangoHorario").get("fin").toString(),info.get("nombre").toString()));
                                        }
                                        else{
                                            //alarma.add(new alarmaItem(String.valueOf(i),"Apagada",info.getJSONObject("rangoDistancia").get("rango").toString(),info.getString("_id").toString(),info.getJSONObject("ubicacionfav").get("nombre").toString()));
                                            alarma.add(new alarmaItem(String.valueOf(i),"Encencida",info.getJSONObject("rangoHorario").get("inicio").toString(),info.getJSONObject("rangoHorario").get("fin").toString(),info.get("nombre").toString()));
                                        }
                                    }
                                    else {
                                        if(info.get("estado").toString().equals("true")){
                                            alarma.add(new alarmaItem(String.valueOf(i),"Encendida",info.getJSONObject("rangoDistancia").get("rango").toString(),info.getString("_id").toString(),info.getJSONObject("ubicacionfav").get("nombre").toString()));
                                        }else {

                                            alarma.add(new alarmaItem(String.valueOf(i),"Apagada",info.getJSONObject("rangoDistancia").get("rango").toString(),info.getString("_id").toString(),info.getJSONObject("ubicacionfav").get("nombre").toString()));
                                        }

                                    }
                                    Log.d("contador", "onResponse: "+info.toString());

                                }

                                AdaptadorAlarma ad = new AdaptadorAlarma(getContext(),alarma);

                                Log.d("tamaño", "tamaño de ubicacion "+alarma.size());
                                lista.setAdapter(ad);

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
        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                alarmaItem sel = (alarmaItem) adapterView.getItemAtPosition(i);
                alerta = sel.getEstado();
                actualizar.setVisibility(View.VISIBLE);
                eliminar.setVisibility(View.VISIBLE);

            }
        });*/
        Button agregaralarma = (Button) vista.findViewById(R.id.button6);
        agregaralarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(getContext(),AgregarAlarma.class);
                nuevo.putExtra("usuario",getArguments().getString(ARG_PARAM1).toString());
                nuevo.putExtra("token",getArguments().getString(ARG_PARAM2).toString());
                nuevo.putExtra("vehiculo",getArguments().getString(ARG_PARAM3).toString());
                startActivity(nuevo);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), alerta, Toast.LENGTH_SHORT).show();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actualizar = new Intent(getContext(),ActualizarAlarma.class);
                actualizar.putExtra("usuario",getArguments().get(ARG_PARAM1).toString());
                actualizar.putExtra("token",getArguments().get(ARG_PARAM2).toString());
                actualizar.putExtra("vehiculo",getArguments().get(ARG_PARAM3).toString());
                startActivity(actualizar);
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
