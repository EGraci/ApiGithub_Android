package com.example.apigithub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdapterKonten extends RecyclerView.Adapter<AdapterKonten.TampilanSet>{
    List<GitHub> data = new ArrayList<>();
    Context ct;
    public AdapterKonten(List<GitHub> dt, Context tx){
        this.data = dt;
        this.ct = tx;
    }

    @NonNull
    @Override
    public TampilanSet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater isi =LayoutInflater.from(ct);
        View lihat= isi.inflate(R.layout.row_view, parent,  false);
        return new TampilanSet(lihat);
    }

    @Override
    public void onBindViewHolder(@NonNull TampilanSet holder, int position) {
        holder.user.setText(data.get(position).getUsername());
        Glide.with(ct).load(data.get(position).getGambar()).circleCrop().into(holder.foto);
        holder.rowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(ct,DetailProfile.class);
                pindah.putExtra("user", data.get(position).getUsername());
                pindah.putExtra("gambar", data.get(position).getGambar());
                pindah.putExtra("follo", data.get(position).getFollo());
                pindah.putExtra("folli", data.get(position).getFolli());
                ct.startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TampilanSet extends RecyclerView.ViewHolder{
        TextView user;
        ImageView foto;
        View rowItem;
        public TampilanSet(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.nama_item);
            foto = itemView.findViewById(R.id.foto_item);
            rowItem = itemView;
        }
    }
}
