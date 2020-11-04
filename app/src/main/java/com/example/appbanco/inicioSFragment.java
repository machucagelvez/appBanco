package com.example.appbanco;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class inicioSFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText usuario, clave;
    Button iniciar;
    RequestQueue rq;//permite crear un objeto para realizar una petición
    JsonRequest jrq;//Permite recibir los datos en formato JSON

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_inicio_s, container, false);
        View vista = inflater.inflate(R.layout.fragment_inicio_s,container,false);
        usuario = vista.findViewById(R.id.etusuario);
        clave = vista.findViewById(R.id.etpassword);
        iniciar = vista.findViewById(R.id.btniniciarsesion);
        rq = Volley.newRequestQueue(getContext());

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarsesion();
            }
        });
        return vista;
    }

    private void iniciarsesion() {
        String url = "http://10.10.11.211/servicioswebbanco/buscarCliente.php?usuario="+usuario.getText().toString()+"&contrasena="+clave.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Usuario o contraseña errados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Se utiliza la clase usuario para tomar los campos del arreglo datos del archivo php
        usuario cliente = new usuario();
        //usuarios: arreglo que envía los datos en formato JSON, en el archivo php
        JSONArray jsonArray = response.optJSONArray("cliente");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posición 0 del arreglo
            cliente.setUsuario(jsonObject.optString("usuario"));
            //usua.setClave(jsonObject.optString("clave"));
            cliente.setIdent(jsonObject.optString("ident"));
            cliente.setNombre(jsonObject.optString("nombre"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Intent intlogeado = new Intent(getContext(), opciones.class);
        intlogeado.putExtra(opciones.usuario, cliente.getUsuario());
        intlogeado.putExtra(opciones.nombre, cliente.getNombre());
        intlogeado.putExtra(opciones.ident, cliente.getIdent());
        startActivity(intlogeado);
    }
}