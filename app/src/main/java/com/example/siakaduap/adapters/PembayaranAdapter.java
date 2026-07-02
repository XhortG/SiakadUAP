package com.example.siakaduap.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.siakaduap.R;
import com.example.siakaduap.models.Pembayaran;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.LoginResponse;
import com.example.siakaduap.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

public class PembayaranAdapter extends RecyclerView.Adapter<PembayaranAdapter.PembayaranViewHolder> {

    private final Context context;
    private final List<Pembayaran> pembayaranList;

    public PembayaranAdapter(Context context, List<Pembayaran> pembayaranList) {
        this.context = context;
        this.pembayaranList = pembayaranList;
    }

    @NonNull
    @Override
    public PembayaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pembayaran, parent, false);
        return new PembayaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PembayaranViewHolder holder, int position) {
        Pembayaran pembayaran = pembayaranList.get(position);
        holder.tvSemester.setText("Semester " + pembayaran.getSemester());
        
        // Format rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        holder.tvTagihan.setText(formatRupiah.format(pembayaran.getTagihan()));
        
        holder.tvStatus.setText(pembayaran.getStatus());
        if (pembayaran.getStatus().equalsIgnoreCase("Lunas")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.success_green));
            holder.tvStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(context.getResources().getColor(R.color.success_container)));
            holder.tvTanggalBayar.setText("Dibayar pada: " + pembayaran.getTanggalBayar());
            holder.tvTanggalBayar.setVisibility(View.VISIBLE);
            holder.btnBayar.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.md_error));
            holder.tvStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(context.getResources().getColor(R.color.md_error_container)));
            holder.tvTanggalBayar.setVisibility(View.GONE);
            holder.btnBayar.setVisibility(View.VISIBLE);
        }

        holder.btnBayar.setOnClickListener(v -> {
            holder.btnBayar.setEnabled(false);
            holder.btnBayar.setText("Memproses...");
            
            String npm = SharedPrefManager.getInstance(context).getNpm();
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<LoginResponse> call = apiService.pembayaranAction(npm, pembayaran.getSemester());

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        pembayaran.setStatus("Lunas");
                        pembayaran.setTanggalBayar("Hari ini");
                        notifyItemChanged(position);
                    } else {
                        Toast.makeText(context, "Gagal memproses pembayaran", Toast.LENGTH_SHORT).show();
                        holder.btnBayar.setEnabled(true);
                        holder.btnBayar.setText("Bayar Sekarang");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(context, "Koneksi gagal", Toast.LENGTH_SHORT).show();
                    holder.btnBayar.setEnabled(true);
                    holder.btnBayar.setText("Bayar Sekarang");
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return pembayaranList != null ? pembayaranList.size() : 0;
    }

    public static class PembayaranViewHolder extends RecyclerView.ViewHolder {
        TextView tvSemester, tvTagihan, tvStatus, tvTanggalBayar;
        Button btnBayar;

        public PembayaranViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSemester = itemView.findViewById(R.id.tvSemester);
            tvTagihan = itemView.findViewById(R.id.tvTagihan);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTanggalBayar = itemView.findViewById(R.id.tvTanggalBayar);
            btnBayar = itemView.findViewById(R.id.btnBayar);
        }
    }
}
