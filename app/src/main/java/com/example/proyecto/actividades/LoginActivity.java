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
import com.example.proyecto.clases.Hash;
import com.example.proyecto.sqlite.yugioh;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtCorreo, txtClave;
    Button btnIngresar, btnSalir;
    CheckBox chkRecordar;
    TextView lblRegistrar;

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
        if(correo.equals("luis@upn.edu.pe")&& clave.equals("e05adfb80b6cc009a848215b506ae63f6fd05b97")){
            Intent iBienvenida = new Intent(this, BienvenidaActivity.class);
            iBienvenida.putExtra("nombre", "Luis");
            if(chkRecordar.isChecked()){git
                yugioh yugioh = new yugioh(getApplicationContext());
                yugioh.agregarUsuario(1, correo, clave);
            }
            startActivity(iBienvenida);
        }
        else{
            Toast.makeText(this, "Usuario o clave incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrar() {
        Intent iRegistro = new Intent(this, RegistroActivity.class);
        startActivity(iRegistro);
    }

    private void salir() {git s
        //limpiar temporales o cerrar archivos abiertos
        //validar si hay algo pendiente
        System.exit(0);
    }


}