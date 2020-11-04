package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class opciones extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    public static final String usuario = "usuario";
    public static final String ident = "ident";
    public static final String nombre = "nombre";
    Button cerrar_op, transferencias, reportes;
    TextView cliente;
    RequestQueue rq;//permite crear un objeto para realizar una petición
    JsonRequest jrq;//Permite recibir los datos en formato JSON
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        cliente = findViewById(R.id.tvcliente);
        cerrar_op = findViewById(R.id.btncerrarsesion_op);
        transferencias = findViewById(R.id.btntransferencias);
        reportes = findViewById(R.id.btnreportes);
        rq = Volley.newRequestQueue(getApplicationContext());
        user = getIntent().getStringExtra("usuario");

        cliente.setText(getIntent().getStringExtra("nombre"));

        reportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intreport = new Intent(getApplicationContext(), reporteTrf.class);
                intreport.putExtra("usuario", user);
                startActivity(intreport);
            }
        });

        cerrar_op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        transferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tranferir();
            }
        });
    }

    private void tranferir() {
        String url = "http://10.10.11.211/servicioswebbanco/buscarCuenta.php?usuario="+user;
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No se encontró cuenta para este usuario", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        cuenta cuentauser = new cuenta();
        //usuarios: arreglo que envía los datos en formato JSON, en el archivo php
        JSONArray jsonArray = response.optJSONArray("cuenta");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posición 0 del arreglo
            cuentauser.setUsuario(jsonObject.optString("usuario"));
            cuentauser.setNrocuenta(jsonObject.optString("Nrocuenta"));
            cuentauser.setSaldo(jsonObject.optString("saldo"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Intent inttrf = new Intent(getApplicationContext(), transaccion.class);
        inttrf.putExtra(transaccion.usuario_op, cuentauser.getUsuario());
        inttrf.putExtra(transaccion.cuentaOrigen, cuentauser.getNrocuenta());
        inttrf.putExtra(transaccion.saldo_op, cuentauser.getSaldo());
        startActivity(inttrf);
    }
}