package com.example.proyecto.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyecto.R;
import com.example.proyecto.clases.Hash;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtDni, txtNombre, txtApellido, txtFechaNac, txtCorreo, txtClave;
    CheckBox chkTerminos;
    Button btnRegistrar, btnCancelar;
    Spinner cboDistritos;
    RadioGroup ragSexo;

    private final String getController = "http://proyecto-yugioh.atwebpages.com/webServices/mostrarController.php";
    private final String setCliente = "http://proyecto-yugioh.atwebpages.com/webServices/agregarCliente.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtDni = findViewById(R.id.regTxtDNI);
        txtNombre = findViewById(R.id.regTxtNombre);
        txtApellido = findViewById(R.id.regTxtApellidos);
        txtFechaNac = findViewById(R.id.regTxtFechaNac);
        txtCorreo = findViewById(R.id.regTxtCorreo);
        chkTerminos = findViewById(R.id.regChkTerminos);
        btnRegistrar = findViewById(R.id.regBtnRegistrar);
        btnCancelar = findViewById(R.id.regBtnCancelar);
        cboDistritos  = findViewById(R.id.regCboDistritos);
        ragSexo  = findViewById(R.id.regragSexo);

        cboDistritos.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, new String[] {"Seleccione distrito"}));

        llenarDistritos();


        txtFechaNac.setOnClickListener(this);
        chkTerminos.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        btnRegistrar.setEnabled(false);
    }

    private void llenarDistritos() {
        com.loopj.android.http.AsyncHttpClient aDistritos = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("tipo","1");

        aDistritos.get(getController, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        String[] distritos = new String[jsonArray.length()+1];
                        distritos[0] = "Seleccione distrito";
                        for(int i = 1; i < jsonArray.length()+1; i++){
                            distritos[i] = jsonArray.getJSONObject(i-1).getString("nombre_distrito");
                        }

                        cboDistritos.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item, distritos));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regTxtFechaNac:
                cargarSelectorFechas();
                break;
            case R.id.regChkTerminos:
                setEstadoBotonRegistrar(chkTerminos.isChecked());
                break;
            case R.id.regBtnRegistrar:
                registrarCliente();
                break;
            case R.id.regBtnCancelar:
                regresar();
                break;
        }
    }

    private void setEstadoBotonRegistrar(boolean checked) {
        btnRegistrar.setEnabled(checked);
    }

    private void cargarSelectorFechas() {
        DatePickerDialog dateDialog;
        final Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);
        dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                txtFechaNac.setText(i+"-"+(((i1+1)<10?"0"+(i1+1):(i1+1)))+"-"+(i2<10?"0"+i2:i2));
            }
        }, anio, mes, dia);
        dateDialog.show();
    }

    private void registrarCliente() {
        if (validarFormulario()) {
            String dni = txtDni.getText().toString();
            String nombre = txtNombre.getText().toString();
            String apellidos = txtApellido.getText().toString();
            String fechaNac = txtFechaNac.getText().toString();
            char sexo = 'X';
            int radioButtonID = ragSexo.getCheckedRadioButtonId();
            RadioButton radioButton = ragSexo.findViewById(radioButtonID);
            String texto = radioButton.getText().toString();
            switch (texto){
                case "Masculino": sexo = 'M';break;
                case "Femenino": sexo = 'F';break;
                default:sexo = 'X';break;
            }
            String correo = txtCorreo.getText().toString();
            Hash hash = new Hash();
            String clave = hash.StringToHash(txtClave.getText().toString(), "SHA1");
            int idDistrito = cboDistritos.getSelectedItemPosition();
            AsyncHttpClient ahcRegistrarcliente = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("dni", dni);
            params.add("nombre", nombre);
            params.add("apellidos", apellidos);
            params.add("fecha_nac", fechaNac);
            params.add("sexo", String.valueOf(sexo));
            params.add("correo", correo);
            params.add("clave", clave);
            params.add("id_distrito", String.valueOf(idDistrito));

            ahcRegistrarcliente.post(setCliente, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if(statusCode == 200){
                        int resultado = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                        if(resultado == 1){
                            setEstadoBotonRegistrar(false);
                            btnCancelar.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e){
                                throw new RuntimeException(e);
                            }
                            startActivity(login);
                            finish();

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error al  Registrar", Toast.LENGTH_LONG).show();
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
    }

    private boolean validarFormulario() {
        //Se realiza la validacion del formulario(pendiente por tiempo para ustedes)
        return true;
    }

    private void regresar() {
        
        
    }
}