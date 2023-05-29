package com.example.proyecto.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.proyecto.fragmentos.CarritoFragment;
import com.example.proyecto.fragmentos.CartasFragment;
import com.example.proyecto.fragmentos.ConfiguracionFragment;
import com.example.proyecto.fragmentos.UbicanosFragment;
import com.example.proyecto.R;
import com.example.proyecto.clases.Menu;

public class MenuActivity extends AppCompatActivity implements Menu {
    Fragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        fragments = new Fragment[4];
        fragments[0] = new CartasFragment();
        fragments[1] = new CarritoFragment();
        fragments[2] = new UbicanosFragment();
        fragments[3] = new ConfiguracionFragment();
        int iIdBoton = getIntent().getIntExtra("BotonElegido", -1);
        menu(iIdBoton);
    }

    @Override
    public void menu(int iIdBoton) {
        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction frgTransaction = frgManager.beginTransaction();
        frgTransaction.replace(R.id.menRelMenu, fragments[iIdBoton]);
        frgTransaction.commit();
    }
}