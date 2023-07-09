package com.example.proyecto.adaptadores;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.actividades.EditarActivity;
import com.example.proyecto.clases.Auto;

import com.loopj.android.http.Base64;

import java.util.List;

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.ViewHolder> {
    private List<Auto> lista_autos;

    public AutoAdapter(List<Auto> lista_autos) {
        this.lista_autos = lista_autos;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewHolder holder, int position) {
        Auto auto = lista_autos.get(position);
        holder.lbl_marca.setText(auto.getMarca());
        holder.lbl_modelo.setText(auto.getModelo());
        holder.lbl_placa.setText(auto.getPlaca());
        holder.lbl_precio.setText("$ "+String.format("%.2f",auto.getPrecio()));
        String imagen = auto.getImagen();
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
        holder.iv_foto_auto.setImageBitmap(bitmap);
        holder.cv_item_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_editar = new Intent(v.getContext(), EditarActivity.class);
                i_editar.putExtra("id", lista_autos.get(position).getId());
                v.getContext().startActivity(i_editar);
                //Toast.makeText(v.getContext(), "ID: "+lista_autos.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_autos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv_item_auto;
        TextView lbl_marca, lbl_modelo, lbl_placa, lbl_precio;
        ImageView iv_foto_auto;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            cv_item_auto = itemView.findViewById(R.id.cv_item_auto);
            lbl_marca = itemView.findViewById(R.id.lbl_item_marca);
            lbl_modelo = itemView.findViewById(R.id.lbl_item_modelo);
            lbl_placa = itemView.findViewById(R.id.lbl_item_placa);
            lbl_precio = itemView.findViewById(R.id.lbl_item_precio);
            iv_foto_auto = itemView.findViewById(R.id.iv_item_auto);
        }
    }
}
