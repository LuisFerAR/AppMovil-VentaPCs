package com.example.proyecto.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.proyecto.R;
import com.example.proyecto.clases.Cliente;
import com.example.proyecto.clases.Menu;

public class BienvenidaActivity extends AppCompatActivity implements Menu {

    TextView lblNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        lblNombre = findViewById(R.id.bieLblNombre);
        Cliente cliente = (Cliente)getIntent().getSerializableExtra("cliente");
        lblNombre.setText("Bienvenido "+cliente.getNombre());
    }

    @Override
    public void menu(int iIdBoton) {
        Intent iMenu = new Intent(this, MenuActivity.class);
        iMenu.putExtra("BotonElegido", iIdBoton);
        startActivity(iMenu);

    }
}