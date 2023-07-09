package com.example.proyecto.actividades;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.proyecto.R;
import com.example.proyecto.adaptadores.AutoAdapter;
import com.example.proyecto.clases.Auto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MostrarActivity extends AppCompatActivity {
    RecyclerView jrv_autos;
    ArrayList<Auto> lista;
    AutoAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        jrv_autos = findViewById(R.id.mos_rv_mostrar_autos);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        jrv_autos.setLayoutManager(manager);
        adapter = new AutoAdapter(lista);
        jrv_autos.setAdapter(adapter);

        mostrar_autos();
    }

    private void mostrar_autos() {
        AsyncHttpClient ahc_mostrar_autos = new AsyncHttpClient();
        String s_url = "http://appmovilxddd.000webhostapp.com/webServices/MostrarAutos.php";
        RequestParams params = new RequestParams();
        params.add("ID", "");

        ahc_mostrar_autos.get(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for(int i = 0; i < jsonArray.length(); i++){
                            lista.add(new Auto(jsonArray.getJSONObject(i).getInt("ID"),
                                    jsonArray.getJSONObject(i).getString("Marca"),
                                    jsonArray.getJSONObject(i).getString("Modelo"),
                                    jsonArray.getJSONObject(i).getString("Placa"),
                                    jsonArray.getJSONObject(i).getDouble("Precio"),
                                    jsonArray.getJSONObject(i).getString("Imagen")));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "Error al cargar data: "+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }
}