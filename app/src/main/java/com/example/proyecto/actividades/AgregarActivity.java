package com.example.proyecto.actividades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.example.proyecto.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class AgregarActivity extends AppCompatActivity implements View.OnClickListener {
    EditText jtxt_marca, jtxt_modelo, jtxt_placa, jtxt_precio;
    Button jbtn_elegir, jbtn_registrar, jbtn_mostrar;
    ImageView jiv_foto_auto;
    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        jtxt_marca = findViewById(R.id.agr_txt_marca);
        jtxt_modelo = findViewById(R.id.agr_txt_modelo);
        jtxt_placa = findViewById(R.id.agr_txt_placa);
        jtxt_precio = findViewById(R.id.agr_txt_precio);
        jbtn_elegir = findViewById(R.id.agr_btn_elegir);
        jbtn_registrar = findViewById(R.id.agr_btn_registrar);
        jbtn_mostrar = findViewById(R.id.agr_btn_mostrar);
        jiv_foto_auto = findViewById(R.id.agr_iv_foto_auto);

        jbtn_elegir.setOnClickListener(this);
        jbtn_registrar.setOnClickListener(this);
        jbtn_mostrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.agr_btn_elegir:
                elegir_foto();
                break;
            case R.id.agr_btn_registrar:
                registrar_auto();
                break;
            case R.id.agr_btn_mostrar:
                mostrar_autos();
                break;
        }
    }

    private void elegir_foto() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i_file_chooser = new Intent(Intent.ACTION_PICK);
                i_file_chooser.setType("image/*");
                startActivityForResult(i_file_chooser, REQUEST_CODE_GALLERY);
            }
            else
                Toast.makeText(this, "No se puede acceder al almacenamiento externo", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    private void registrar_auto() {
        AsyncHttpClient ahc_registrar_auto = new AsyncHttpClient();

        String s_url = "http://appmovilxddd.000webhostapp.com/webServices/RegistrarAuto.php";
        RequestParams params = new RequestParams();
        params.add("marca", jtxt_marca.getText().toString().trim());
        params.add("modelo", jtxt_modelo.getText().toString().trim());
        params.add("placa", jtxt_placa.getText().toString().trim());
        params.add("precio", jtxt_precio.getText().toString().trim());
        params.add("imagen", image_view_to_base64(jiv_foto_auto));

        ahc_registrar_auto.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    int ret_val = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        Toast.makeText(getApplicationContext(), "Auto registrado!!!", Toast.LENGTH_SHORT).show();
                        jtxt_marca.setText("");
                        jtxt_modelo.setText("");
                        jtxt_placa.setText("");
                        jtxt_precio.setText("");
                        jiv_foto_auto.setImageResource(R.mipmap.ic_launcher);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "Error al registrar auto "+statusCode, Toast.LENGTH_SHORT).show();
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
        String imagen = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return  imagen;
    }

    private void mostrar_autos() {
        Intent i_mostrar = new Intent(this, MostrarActivity.class);
        startActivity(i_mostrar);
    }
}