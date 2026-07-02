package com.example.siakaduap.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.siakaduap.R;
import com.example.siakaduap.models.Riwayat;
import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private final Context context;
    private final List<Riwayat> riwayatList;

    public RiwayatAdapter(Context context, List<Riwayat> riwayatList) {
        this.context = context;
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_riwayat, parent, false);
        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        Riwayat riwayat = riwayatList.get(position);
        holder.tvAktivitas.setText(riwayat.getAktivitas());
        holder.tvDeskripsi.setText(riwayat.getDeskripsi());
        
        // Handle newline for waktu naturally
        holder.tvWaktu.setText(riwayat.getWaktu().replace(", ", "\n"));
        
        // Dynamic Icon & Color based on type
        switch (riwayat.getJenis().toLowerCase()) {
            case "login":
                holder.ivIcon.setImageResource(R.drawable.ic_profil);
                holder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.md_tertiary));
                break;
            case "pembayaran":
                holder.ivIcon.setImageResource(R.drawable.ic_pembayaran);
                holder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.success_green));
                break;
            case "khs":
            case "krs":
                holder.ivIcon.setImageResource(R.drawable.ic_krs);
                holder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.md_primary));
                break;
            default:
                holder.ivIcon.setImageResource(R.drawable.ic_berita);
                holder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.md_secondary));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return riwayatList != null ? riwayatList.size() : 0;
    }

    public static class RiwayatViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvAktivitas, tvDeskripsi, tvWaktu;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvAktivitas = itemView.findViewById(R.id.tvAktivitas);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
        }
    }
}
