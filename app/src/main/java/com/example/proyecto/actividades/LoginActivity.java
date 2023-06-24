package com.example.proyecto.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto.R;
import com.example.proyecto.clases.Cliente;
import com.example.proyecto.clases.Hash;
import com.example.proyecto.sqlite.yugioh;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtCorreo, txtClave;
    Button btnIngresar, btnSalir;
    CheckBox chkRecordar;
    TextView lblRegistrar;


    //final private String urlLogin = "https://appmovilxdd.000webhostapp.com/webService/iniciarSesion.php";
    final private String urlLogin = "http://proyecto-yugioh.atwebpages.com/webServices/iniciarSesion.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo = findViewById(R.id.logTxtCorreo);
        txtClave = findViewById(R.id.logTxtClave);
        btnIngresar = findViewById(R.id.logBtnIngresar);
        btnSalir = findViewById(R.id.logBtnSalir);
        chkRecordar = findViewById(R.id.logChkRecordar);
        lblRegistrar = findViewById(R.id.logLblRegistro);
        //configura el evento on click
        btnIngresar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
        lblRegistrar.setOnClickListener(this);
        //VALIDAR SI SE RECORDO SESION
        yugioh yugioh = new yugioh(getApplicationContext());
        if(yugioh.recordoUsuario()){
            iniciarSesion(yugioh.buscarCampo("CORREO"), yugioh.buscarCampo("CLAVE"), true);
        }
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.logBtnIngresar:
                iniciarSesion(txtCorreo.getText().toString(), txtClave.getText().toString(), false);
                break;
            case R.id.logBtnSalir:
                salir();
                break;
            case R.id.logLblRegistro:
                registrar();
                break;

        }
    }

    private void iniciarSesion(String correo, String clave, boolean recordo) {
        Hash hash =  new Hash();
        clave = recordo == true ? clave : hash.StringToHash(clave, "SHA1");
        AsyncHttpClient ahcLogin = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("correo", correo);
        params.add("clave", clave);


        ahcLogin.post(urlLogin, params, new BaseJsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        if(jsonArray.length() > 0){
                            if(jsonArray.getJSONObject(0).getInt("id_cliente") != -1){
                                Cliente cliente = new Cliente();
                                cliente.setIdCliente(jsonArray.getJSONObject(0).getInt("id_cliente"));
                                cliente.setDni(jsonArray.getJSONObject(0).getString("dni"));
                                cliente.setNombre(jsonArray.getJSONObject(0).getString("nombre"));
                                cliente.setApellidos(jsonArray.getJSONObject(0).getString("apellidos"));
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                Date date = format.parse(jsonArray.getJSONObject(0).getString("fecha_nac"));
                                cliente.setFechaNac(date);
                                cliente.setSexo(jsonArray.getJSONObject(0).getString("sexo").charAt(0));
                                cliente.setCorreo(jsonArray.getJSONObject(0).getString("correo"));
                                cliente.setClave(jsonArray.getJSONObject(0).getString("clave"));
                                cliente.setIdDistrito(jsonArray.getJSONObject(0).getInt("id_distrito"));

                                Intent iBienvenida = new Intent(getApplicationContext(), BienvenidaActivity.class);
                                iBienvenida.putExtra("cliente", cliente);
                                if(chkRecordar.isChecked()){
                                    yugioh yugioh = new yugioh(getApplicationContext());
                                    yugioh.agregarUsuario(cliente.getIdCliente(), cliente.getCorreo(), cliente.getClave());
                                }
                                startActivity(iBienvenida);

                            }else{
                                Toast.makeText(getApplicationContext(), "ERROR: usuario o clave incorrecta", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "ERROR al iniciar sesion", Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException | ParseException e){
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });

    }
    private void registrar() {
        Intent iRegistro = new Intent(this, RegistroActivity.class);
        startActivity(iRegistro);
    }

    private void salir() {
        //limpiar temporales o cerrar archivos abiertos
        //validar si hay algo pendiente
        System.exit(0);
    }
}