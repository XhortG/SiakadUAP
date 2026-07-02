package com.example.siakaduap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siakaduap.R;
import com.example.siakaduap.models.Khs;
import com.google.android.material.chip.Chip;

import java.util.List;

public class KhsAdapter extends RecyclerView.Adapter<KhsAdapter.KhsViewHolder> {

    private final Context context;
    private final List<Khs> khsList;

    public KhsAdapter(Context context, List<Khs> khsList) {
        this.context = context;
        this.khsList = khsList;
    }

    @NonNull
    @Override
    public KhsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khs, parent, false);
        return new KhsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhsViewHolder holder, int position) {
        Khs khs = khsList.get(position);
        holder.tvKodeMk.setText(khs.getKodeMk());
        holder.tvNamaMk.setText(khs.getNamaMk());
        holder.tvSks.setText(khs.getSks() + " SKS");
        holder.tvNilai.setText(khs.getNilai());
    }

    @Override
    public int getItemCount() {
        return khsList != null ? khsList.size() : 0;
    }

    public static class KhsViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeMk, tvSks, tvNamaMk, tvNilai;

        public KhsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            tvSks = itemView.findViewById(R.id.tvSks);
            tvNilai = itemView.findViewById(R.id.tvNilai);
        }
    }
}
