package com.example.siakaduap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import com.example.siakaduap.R;
import com.example.siakaduap.activities.PengumumanDetailActivity;
import com.example.siakaduap.models.Pengumuman;
import java.util.List;

public class PengumumanAdapter extends RecyclerView.Adapter<PengumumanAdapter.ViewHolder> {

    private Context context;
    private List<Pengumuman> list;

    public PengumumanAdapter(Context context, List<Pengumuman> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pengumuman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pengumuman item = list.get(position);
        holder.tvJudul.setText(item.getJudul());
        holder.tvIsi.setText(item.getIsi());
        holder.tvTanggal.setText(item.getTanggal());
        
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PengumumanDetailActivity.class);
            intent.putExtra("judul", item.getJudul());
            intent.putExtra("tanggal", item.getTanggal());
            intent.putExtra("isi", item.getIsi());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvIsi, tvTanggal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvIsi = itemView.findViewById(R.id.tvIsi);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
