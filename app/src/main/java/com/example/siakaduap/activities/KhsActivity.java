package com.example.siakaduap.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;
import com.example.siakaduap.adapters.KhsAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Khs;
import com.example.siakaduap.models.KhsResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhsActivity extends AppCompatActivity {

    private RecyclerView rvKhs;
    private SwipeRefreshLayout swipeRefresh;
    private TextView tvEmpty;
    private KhsAdapter adapter;
    // Fix: ApiService diinisialisasi sekali saja
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khs);

        // Fix: Pasang listener tombol Back pada TopAppBar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rvKhs      = findViewById(R.id.rvKhs);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tvEmpty    = findViewById(R.id.tvEmpty);

        rvKhs.setLayoutManager(new LinearLayoutManager(this));

        // Fix: Buat ApiService sekali di onCreate
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        swipeRefresh.setOnRefreshListener(this::loadKhs);
        swipeRefresh.setRefreshing(true);
        loadKhs();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void loadKhs() {
        String npm = SharedPrefManager.getInstance(this).getNpm();

        apiService.getKhs(npm).enqueue(new Callback<KhsResponse>() {
            @Override
            public void onResponse(Call<KhsResponse> call, Response<KhsResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        List<Khs> khsList = response.body().getData();
                        // Fix: Tampilkan empty state jika data kosong
                        if (khsList == null || khsList.isEmpty()) {
                            tvEmpty.setVisibility(View.VISIBLE);
                            rvKhs.setVisibility(View.GONE);
                        } else {
                            tvEmpty.setVisibility(View.GONE);
                            rvKhs.setVisibility(View.VISIBLE);
                            adapter = new KhsAdapter(KhsActivity.this, khsList);
                            rvKhs.setAdapter(adapter);
                        }
                    } else {
                        showEmpty(response.body().getMessage());
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang sibuk.", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KhsResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Snackbar.make(findViewById(android.R.id.content), "Tidak ada koneksi internet. Silakan coba lagi.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba Lagi", v -> loadKhs())
                        .show();
            }
        });
    }

    private void showEmpty(String message) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(message);
        rvKhs.setVisibility(View.GONE);
    }
}
