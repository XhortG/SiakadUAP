package com.example.siakaduap.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siakaduap.R;
import com.example.siakaduap.activities.DetailBeritaActivity;
import com.example.siakaduap.models.Berita;
import com.google.android.material.chip.Chip;
import android.widget.TextView;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {

    private final Context context;
    private final List<Berita> beritaList;

    public BeritaAdapter(Context context, List<Berita> beritaList) {
        this.context = context;
        this.beritaList = beritaList;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita, parent, false);
        return new BeritaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {
        Berita berita = beritaList.get(position);
        holder.tvJudul.setText(berita.getJudul());
        holder.tvTanggal.setText(berita.getTanggal());
        holder.tvIsi.setText(berita.getIsi());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailBeritaActivity.class);
            intent.putExtra("judul", berita.getJudul());
            intent.putExtra("tanggal", berita.getTanggal());
            intent.putExtra("isi", berita.getIsi());
            context.startActivity(intent);
            // Slide transition from adapter context
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beritaList != null ? beritaList.size() : 0;
    }

    public static class BeritaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal, tvJudul, tvIsi;

        public BeritaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvIsi = itemView.findViewById(R.id.tvIsi);
        }
    }
}
