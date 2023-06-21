package com.example.proyecto.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyecto.R;
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
        btnRegistrar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
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
            case R.id.regBtnRegistrar:
                registrarCliente();
                break;
            case R.id.regBtnCancelar:
                regresar();
                break;
        }
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
        
    }

    private void regresar() {
        
        
    }
}