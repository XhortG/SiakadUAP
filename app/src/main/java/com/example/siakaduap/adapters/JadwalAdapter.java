package com.example.siakaduap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.siakaduap.R;
import com.example.siakaduap.models.Jadwal;
import java.util.ArrayList;
import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> implements Filterable {

    private final Context context;
    private List<Jadwal> jadwalList;
    private List<Jadwal> jadwalListFull;

    public JadwalAdapter(Context context, List<Jadwal> jadwalList) {
        this.context = context;
        this.jadwalList = jadwalList;
        this.jadwalListFull = new ArrayList<>(jadwalList);
    }

    @NonNull
    @Override
    public JadwalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jadwal, parent, false);
        return new JadwalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalViewHolder holder, int position) {
        Jadwal jadwal = jadwalList.get(position);
        holder.tvKodeMk.setText(jadwal.getKodeMk());
        holder.tvHariJam.setText(jadwal.getHari() + ", " + jadwal.getJam());
        holder.tvNamaMk.setText(jadwal.getNamaMk());
        holder.tvDosen.setText(jadwal.getDosen());
        holder.tvRuang.setText(jadwal.getRuang());
    }

    @Override
    public int getItemCount() {
        return jadwalList != null ? jadwalList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return jadwalFilter;
    }

    private Filter jadwalFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Jadwal> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(jadwalListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Jadwal item : jadwalListFull) {
                    if (item.getNamaMk().toLowerCase().contains(filterPattern) || 
                        item.getKodeMk().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            jadwalList.clear();
            if (results.values != null) {
                jadwalList.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };

    public static class JadwalViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeMk, tvHariJam, tvNamaMk, tvDosen, tvRuang;

        public JadwalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
            tvHariJam = itemView.findViewById(R.id.tvHariJam);
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            tvDosen = itemView.findViewById(R.id.tvDosen);
            tvRuang = itemView.findViewById(R.id.tvRuang);
        }
    }
}
