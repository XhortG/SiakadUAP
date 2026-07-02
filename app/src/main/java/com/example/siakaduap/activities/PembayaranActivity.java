package com.example.siakaduap.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;
import com.example.siakaduap.adapters.PembayaranAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Pembayaran;
import com.example.siakaduap.models.PembayaranResponse;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {
    private RecyclerView rvPembayaran;
    private ProgressBar progressBar;
    private View layoutEmpty;
    private TextView tvEmptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rvPembayaran = findViewById(R.id.rvPembayaran);
        progressBar = findViewById(R.id.progressBar);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        tvEmptyText = findViewById(R.id.tvEmptyText);
        
        rvPembayaran.setLayoutManager(new LinearLayoutManager(this));
        
        loadPembayaran();
    }
    
    private void loadPembayaran() {
        progressBar.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
        
        String npm = SharedPrefManager.getInstance(this).getNpm();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<PembayaranResponse> call = apiService.getPembayaran(npm);
        
        call.enqueue(new Callback<PembayaranResponse>() {
            @Override
            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    if(response.body().isStatus()) {
                        List<Pembayaran> list = response.body().getData();
                        if(list != null && !list.isEmpty()){
                            PembayaranAdapter adapter = new PembayaranAdapter(PembayaranActivity.this, list);
                            rvPembayaran.setAdapter(adapter);
                        } else {
                            layoutEmpty.setVisibility(View.VISIBLE);
                            tvEmptyText.setText("Belum ada tagihan pembayaran");
                        }
                    } else {
                        layoutEmpty.setVisibility(View.VISIBLE);
                        tvEmptyText.setText(response.body().getMessage());
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Respons server tidak valid", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PembayaranResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content), "Koneksi gagal. Periksa server Anda.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba Lagi", v -> loadPembayaran())
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
