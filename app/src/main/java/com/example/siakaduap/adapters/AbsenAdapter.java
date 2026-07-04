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
import com.example.siakaduap.models.Absensi;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.LoginResponse;
import com.example.siakaduap.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenAdapter extends RecyclerView.Adapter<AbsenAdapter.AbsenViewHolder> implements Filterable {

    private final Context context;
    private List<Absensi> absenList;
    private List<Absensi> absenListFull;

    public AbsenAdapter(Context context, List<Absensi> absenList) {
        this.context = context;
        this.absenList = absenList;
        this.absenListFull = new ArrayList<>(absenList);
    }

    @NonNull
    @Override
    public AbsenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_absen, parent, false);
        return new AbsenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenViewHolder holder, int position) {
        Absensi absen = absenList.get(position);
        holder.tvKodeMk.setText(absen.getKodeMk());
        holder.tvNamaMk.setText(absen.getNamaMk());
        
        holder.tvHadir.setText(String.valueOf(absen.getHadir()));
        holder.tvIzin.setText(String.valueOf(absen.getIzin()));
        holder.tvSakit.setText(String.valueOf(absen.getSakit()));
        holder.tvAlfa.setText(String.valueOf(absen.getAlfa()));
        holder.progressKehadiran.setMax(absen.getTotalPertemuan());
        holder.progressKehadiran.setProgress(absen.getHadir());

        setupButton(holder.btnHadir, holder, absen, "hadir", position);
        setupButton(holder.btnIzin, holder, absen, "izin", position);
        setupButton(holder.btnSakit, holder, absen, "sakit", position);
        setupButton(holder.btnAlfa, holder, absen, "alfa", position);
    }

    private void setupButton(com.google.android.material.button.MaterialButton button, AbsenViewHolder holder, Absensi absen, String status, int position) {
        button.setOnClickListener(v -> {
            button.setEnabled(false);
            
            String npm = SharedPrefManager.getInstance(context).getNpm();
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<LoginResponse> call = apiService.absenAction(npm, absen.getKodeMk(), status);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    button.setEnabled(true);
                    if(response.isSuccessful() && response.body() != null) {
                        if(response.body().isStatus()) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            // Update UI optimistically
                            switch(status) {
                                case "hadir": absen.setHadir(absen.getHadir() + 1); break;
                                case "izin": absen.setIzin(absen.getIzin() + 1); break;
                                case "sakit": absen.setSakit(absen.getSakit() + 1); break;
                                case "alfa": absen.setAlfa(absen.getAlfa() + 1); break;
                            }
                            notifyItemChanged(position);
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Error dari server", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    button.setEnabled(true);
                    Toast.makeText(context, "Koneksi gagal, silakan coba lagi", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return absenList != null ? absenList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return absenFilter;
    }

    private Filter absenFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Absensi> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(absenListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Absensi item : absenListFull) {
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
            absenList.clear();
            if (results.values != null) {
                absenList.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };

    public static class AbsenViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeMk, tvNamaMk, tvHadir, tvIzin, tvSakit, tvAlfa;
        com.google.android.material.progressindicator.LinearProgressIndicator progressKehadiran;
        com.google.android.material.button.MaterialButton btnHadir, btnIzin, btnSakit, btnAlfa;

        public AbsenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            tvHadir = itemView.findViewById(R.id.tvHadir);
            tvIzin = itemView.findViewById(R.id.tvIzin);
            tvSakit = itemView.findViewById(R.id.tvSakit);
            tvAlfa = itemView.findViewById(R.id.tvAlfa);
            progressKehadiran = itemView.findViewById(R.id.progressKehadiran);
            btnHadir = itemView.findViewById(R.id.btnHadir);
            btnIzin = itemView.findViewById(R.id.btnIzin);
            btnSakit = itemView.findViewById(R.id.btnSakit);
            btnAlfa = itemView.findViewById(R.id.btnAlfa);
        }
    }
}
