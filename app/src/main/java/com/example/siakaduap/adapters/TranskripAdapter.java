package com.example.siakaduap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siakaduap.R;
import com.example.siakaduap.models.Khs;

import java.util.List;

public class TranskripAdapter extends RecyclerView.Adapter<TranskripAdapter.ViewHolder> {

    private List<Khs> transkripList;

    public TranskripAdapter(List<Khs> transkripList) {
        this.transkripList = transkripList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transkrip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Khs khs = transkripList.get(position);
        
        // Show semester header if it's the first item or semester changed
        boolean showHeader = false;
        if (position == 0) {
            showHeader = true;
        } else {
            Khs previousKhs = transkripList.get(position - 1);
            if (khs.getSemester() != null && !khs.getSemester().equals(previousKhs.getSemester())) {
                showHeader = true;
            }
        }

        if (showHeader) {
            holder.tvSemesterHeader.setVisibility(View.VISIBLE);
            holder.tvSemesterHeader.setText("Semester " + (khs.getSemester() != null ? khs.getSemester() : "-"));
        } else {
            holder.tvSemesterHeader.setVisibility(View.GONE);
        }

        holder.tvKodeMk.setText(khs.getKodeMk());
        holder.tvNamaMk.setText(khs.getNamaMk());
        holder.tvSks.setText(khs.getSks() + " SKS");
        holder.tvNilai.setText(khs.getNilai());
    }

    @Override
    public int getItemCount() {
        return transkripList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSemesterHeader, tvKodeMk, tvNamaMk, tvSks, tvNilai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSemesterHeader = itemView.findViewById(R.id.tvSemesterHeader);
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            tvSks = itemView.findViewById(R.id.tvSks);
            tvNilai = itemView.findViewById(R.id.tvNilai);
        }
    }
}
