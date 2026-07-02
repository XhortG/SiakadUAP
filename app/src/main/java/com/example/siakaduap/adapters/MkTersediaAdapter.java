package com.example.siakaduap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siakaduap.R;
import com.example.siakaduap.models.MataKuliah;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MkTersediaAdapter extends RecyclerView.Adapter<MkTersediaAdapter.ViewHolder> {

    public interface OnAmbilClickListener {
        void onAmbilClick(MataKuliah mk, int position);
    }

    private final Context context;
    private final List<MataKuliah> mkList;
    private final OnAmbilClickListener listener;

    public MkTersediaAdapter(Context context, List<MataKuliah> mkList, OnAmbilClickListener listener) {
        this.context = context;
        this.mkList = mkList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mk_tersedia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MataKuliah mk = mkList.get(position);
        holder.tvKodeMk.setText(mk.getKodeMk());
        holder.tvNamaMk.setText(mk.getNamaMk());
        holder.tvSks.setText(mk.getSks() + " SKS");
        holder.tvDosen.setText(mk.getDosen());

        holder.btnAmbil.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAmbilClick(mk, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mkList != null ? mkList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeMk, tvSks, tvNamaMk, tvDosen;
        MaterialButton btnAmbil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            tvSks = itemView.findViewById(R.id.tvSks);
            tvDosen = itemView.findViewById(R.id.tvDosen);
            btnAmbil = itemView.findViewById(R.id.btnAmbil);
        }
    }
}
