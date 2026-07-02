package com.example.siakaduap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siakaduap.R;
import com.example.siakaduap.models.Krs;
import com.google.android.material.chip.Chip;
import android.widget.TextView;

import java.util.List;

public class KrsAdapter extends RecyclerView.Adapter<KrsAdapter.KrsViewHolder> {

    public interface OnKrsDeleteListener {
        void onDeleteClick(Krs krs);
    }

    private final Context context;
    private final List<Krs> krsList;
    private final OnKrsDeleteListener deleteListener;

    public KrsAdapter(Context context, List<Krs> krsList, OnKrsDeleteListener deleteListener) {
        this.context = context;
        this.krsList = krsList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public KrsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_krs, parent, false);
        return new KrsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KrsViewHolder holder, int position) {
        Krs krs = krsList.get(position);
        holder.tvKodeMk.setText(krs.getKodeMk());
        holder.tvNamaMk.setText(krs.getNamaMk());
        holder.tvSks.setText(krs.getSks() + " SKS");
        holder.tvDosen.setText(krs.getDosen());

        holder.btnBatalKrs.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(krs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return krsList != null ? krsList.size() : 0;
    }

    public static class KrsViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeMk, tvSks, tvNamaMk, tvDosen;
        android.widget.ImageButton btnBatalKrs;

        public KrsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            tvSks = itemView.findViewById(R.id.tvSks);
            tvDosen = itemView.findViewById(R.id.tvDosen);
            btnBatalKrs = itemView.findViewById(R.id.btnBatalKrs);
        }
    }
}
