package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class transaccion extends AppCompatActivity {
    public static final String usuario_op = "usuario";
    public static final String cuentaOrigen = "Nrocuenta";
    public static final String saldo_op = "saldo";
    EditText saldo, cuentadest, valor, hora, fecha;
    Button cerrar, transferir, regresar_tr;
    String NroCtaOrigen, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);
        saldo = findViewById(R.id.etsaldo);
        cuentadest = findViewById(R.id.etcuentadest);
        valor = findViewById(R.id.etvalor);
        hora = findViewById(R.id.ethora);
        fecha = findViewById(R.id.etfecha);
        cerrar = findViewById(R.id.btncerrarsesion_tr);
        transferir = findViewById(R.id.btntransferir);
        regresar_tr = findViewById(R.id.btnregresar_tr);

        TimeZone tz = TimeZone.getTimeZone("GMT-05:00");
        NroCtaOrigen = getIntent().getStringExtra("Nrocuenta");
        saldo.setEnabled(false);
        saldo.setText(getIntent().getStringExtra("saldo"));
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        fecha.setEnabled(false);
        fecha.setText(date);
        hora.setEnabled(false);

        regresar_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        transferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mcuentadest = cuentadest.getText().toString();
                String mvalor = valor.getText().toString();

                if (!mcuentadest.isEmpty() && !mvalor.isEmpty())
                {
                    Calendar c = Calendar.getInstance(tz);
                    time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    hora.setText(time);
                    transferir(mcuentadest, mvalor);
                }
                else
                {
                    Toast.makeText(transaccion.this, "Debe ingresar la cuenta de destino y el valor a transferir", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void transferir(String mcuentadest, String mvalor) {
        //Toast.makeText(this, "Hecho", Toast.LENGTH_SHORT).show();
        String url = "http://192.168.1.73/servicioswebbanco/agregarTrf.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    Toast.makeText(transaccion.this, "Transferencia realizada", Toast.LENGTH_SHORT).show();
                    cuentadest.setText("");
                    valor.setText("");
                    hora.setText("");
                    fecha.setText(date);
                    saldo.setText(getIntent().getStringExtra("saldo"));
                    cuentadest.requestFocus();
                }
                else{
                    Toast.makeText(transaccion.this, "La cuenta de destino no existe", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(transaccion.this, "La transferencia no se realizó. Intente de nuevo.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("NroCtaDest",cuentadest.getText().toString().trim());
                params.put("Valor", valor.getText().toString().trim());
                params.put("Hora",hora.getText().toString().trim());
                params.put("Fecha",fecha.getText().toString().trim());
                params.put("NroCtaOrigen",NroCtaOrigen);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}