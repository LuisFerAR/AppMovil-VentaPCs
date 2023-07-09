package com.example.proyecto.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyecto.R;

public class CargaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);
        Thread tMain = new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    //validaciones necesarias
                    //validar si hay internet
                    //validar si la data esta integra
                    sleep(3000);
                }catch (InterruptedException e){
                    e.getMessage();
                }finally {
                    Intent iLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(iLogin);
                    //para no dejar historial
                    finish();
                }
            }
        };
        tMain.start();
    }
}