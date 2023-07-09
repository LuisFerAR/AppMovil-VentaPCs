package com.example.proyecto.actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.proyecto.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {
    EditText jtxt_marca, jtxt_modelo, jtxt_placa, jtxt_precio;
    Button jbtn_actualizar, jbtn_eliminar, jbtn_regresar;
    ImageView jiv_foto_auto;
    int i_ID = -1;
    private static final int REQUEST_CODE_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        jtxt_marca = findViewById(R.id.edi_txt_marca);
        jtxt_modelo = findViewById(R.id.edi_txt_modelo);
        jtxt_placa = findViewById(R.id.edi_txt_placa);
        jtxt_precio = findViewById(R.id.edi_txt_precio);
        jbtn_actualizar = findViewById(R.id.edi_btn_actualizar);
        jbtn_eliminar = findViewById(R.id.edi_btn_eliminar);
        jbtn_regresar = findViewById(R.id.edi_btn_regresar);
        jiv_foto_auto = findViewById(R.id.edi_iv_foto_auto);

        jiv_foto_auto.setOnClickListener(this);
        jbtn_actualizar.setOnClickListener(this);
        jbtn_eliminar.setOnClickListener(this);
        jbtn_regresar.setOnClickListener(this);
        i_ID = getIntent().getIntExtra("id", -1);
        if(i_ID == -1){
            jbtn_actualizar.setEnabled(false);
            jbtn_eliminar.setEnabled(false);
            Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            return;
        }
        mostrar_auto(i_ID);
    }

    private void mostrar_auto(int i_id) {
        AsyncHttpClient ahc_mostrar_auto = new AsyncHttpClient();

        String s_url = "http://appmovilxddd.000webhostapp.com/webServices/MostrarAutos.php";
        RequestParams params = new RequestParams();

        params.add("ID", String.valueOf(i_id));

        ahc_mostrar_auto.get(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        if(jsonArray.length() == 1){
                            jtxt_marca.setText(jsonArray.getJSONObject(0).getString("Marca"));
                            jtxt_modelo.setText(jsonArray.getJSONObject(0).getString("Modelo"));
                            jtxt_placa.setText(jsonArray.getJSONObject(0).getString("Placa"));
                            jtxt_precio.setText(String.valueOf(jsonArray.getJSONObject(0).getDouble("Precio")));
                            String imagen = jsonArray.getJSONObject(0).getString("Imagen");
                            byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
                            jiv_foto_auto.setImageBitmap(bitmap);
                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edi_iv_foto_auto:
                elegir_foto();
                break;
            case R.id.edi_btn_actualizar:
                actualizar_auto();
                break;
            case R.id.edi_btn_eliminar:
                eliminar_auto();
                break;
            case R.id.edi_btn_regresar:
                regresar();
                break;
        }
    }

    private void elegir_foto() {
        Intent i_file_chooser = new Intent(Intent.ACTION_PICK);
        i_file_chooser.setType("image/*");
        startActivityForResult(i_file_chooser, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(resultCode == RESULT_OK && data != null){
                Uri uri = data.getData();
                jiv_foto_auto.setImageURI(uri);
            }
            else
                Toast.makeText(this, "Debe elegir una imagen", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void actualizar_auto() {
        AsyncHttpClient ahc_actualizar_auto = new AsyncHttpClient();
        String s_url = "http://appmovilxddd.000webhostapp.com/webServices/ActualizarAuto.php";
        RequestParams params = new RequestParams();
        params.add("marca", jtxt_marca.getText().toString().trim());
        params.add("modelo", jtxt_modelo.getText().toString().trim());
        params.add("placa", jtxt_placa.getText().toString().trim());
        params.add("precio", jtxt_precio.getText().toString().trim());
        params.add("imagen", image_view_to_base64(jiv_foto_auto));
        params.add("ID", String.valueOf(i_ID));
        ahc_actualizar_auto.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    int ret_val = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        Toast.makeText(getApplicationContext(), "Datos Actualizados!!!", Toast.LENGTH_SHORT).show();
                        regresar();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "Error al actualizar auto "+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private String image_view_to_base64(ImageView jiv_foto_auto) {
        Bitmap bitmap = ((BitmapDrawable)jiv_foto_auto.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        String imagen = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);

        return  imagen;
    }
    private void eliminar_auto() {
        AsyncHttpClient ahc_eliminar_auto = new AsyncHttpClient();
        String s_url = "http://appmovilxddd.000webhostapp.com/webServices/EliminarAuto.php";
        RequestParams params = new RequestParams();
        params.add("ID", String.valueOf(i_ID));
        ahc_eliminar_auto.delete(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    int ret_val = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        Toast.makeText(getApplicationContext(), "Auto Eliminado!!!", Toast.LENGTH_SHORT).show();
                        regresar();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "Error al eliminar auto "+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }



    private void regresar() {
        Intent i_mostrar = new Intent(getApplicationContext(), MostrarActivity.class);
        finish();
        startActivity(i_mostrar);
    }
}