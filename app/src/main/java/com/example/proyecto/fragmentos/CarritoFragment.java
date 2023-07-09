package com.example.proyecto.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cz.msebera.android.httpclient.Header;
import com.example.proyecto.R;
import com.example.proyecto.adaptadores.AutoAdapter;
import com.example.proyecto.clases.Auto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoFragment extends Fragment {

    RecyclerView jrv_autos;
    ArrayList<Auto> lista;
    AutoAdapter adapter = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarritoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment carritoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarritoFragment newInstance(String param1, String param2) {
        CarritoFragment fragment = new CarritoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        jrv_autos = view.findViewById(R.id.mos_rv_mostrar_autos);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        jrv_autos.setLayoutManager(manager);
        adapter = new AutoAdapter(lista);
        jrv_autos.setAdapter(adapter);

        mostrarAutos();

        return view;
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_carrito, container, false);
    }

    private void mostrarAutos() {
        AsyncHttpClient ahc_mostrar_autos = new AsyncHttpClient();
        String s_url = "http://appmovilxddd.000webhostapp.com/webServices/MostrarAutos.php";
        RequestParams params = new RequestParams();
        params.add("ID", "");

        ahc_mostrar_autos.get(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
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
                Toast.makeText(getContext(), "Error al cargar datos: " + statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

}