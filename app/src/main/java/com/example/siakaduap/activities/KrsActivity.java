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
import com.example.siakaduap.adapters.KrsAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Krs;
import com.example.siakaduap.models.KrsResponse;
import com.example.siakaduap.models.LoginResponse;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KrsActivity extends AppCompatActivity {

    private RecyclerView rvKrs;
    private SwipeRefreshLayout swipeRefresh;
    private TextView tvEmpty;
    private KrsAdapter adapter;
    // Fix: ApiService diinisialisasi sekali saja
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs);

        // Fix: Pasang listener tombol Back pada TopAppBar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rvKrs      = findViewById(R.id.rvKrs);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tvEmpty    = findViewById(R.id.tvEmpty);

        rvKrs.setLayoutManager(new LinearLayoutManager(this));

        // Fix: Buat ApiService sekali di onCreate
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        swipeRefresh.setOnRefreshListener(this::loadKrs);
        swipeRefresh.setRefreshing(true);
        loadKrs();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void loadKrs() {
        String npm = SharedPrefManager.getInstance(this).getNpm();

        apiService.getKrs(npm).enqueue(new Callback<KrsResponse>() {
            @Override
            public void onResponse(Call<KrsResponse> call, Response<KrsResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        List<Krs> krsList = response.body().getData();
                        // Fix: Tampilkan empty state jika data kosong
                        if (krsList == null || krsList.isEmpty()) {
                            tvEmpty.setVisibility(View.VISIBLE);
                            rvKrs.setVisibility(View.GONE);
                        } else {
                            tvEmpty.setVisibility(View.GONE);
                            rvKrs.setVisibility(View.VISIBLE);
                            adapter = new KrsAdapter(KrsActivity.this, krsList, new KrsAdapter.OnKrsDeleteListener() {
                                @Override
                                public void onDeleteClick(Krs krs) {
                                    confirmDelete(krs);
                                }
                            });
                            rvKrs.setAdapter(adapter);
                        }
                    } else {
                        showEmpty(response.body().getMessage());
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Respons server tidak valid", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KrsResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Snackbar.make(findViewById(android.R.id.content), "Koneksi gagal. Periksa server Anda.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba Lagi", v -> loadKrs())
                        .show();
            }
        });
    }

    private void showEmpty(String message) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(message);
        rvKrs.setVisibility(View.GONE);
    }

    private void confirmDelete(Krs krs) {
        new AlertDialog.Builder(this)
                .setTitle("Batal Mata Kuliah")
                .setMessage("Apakah Anda yakin ingin membatalkan " + krs.getNamaMk() + "?")
                .setPositiveButton("Ya", (dialog, which) -> deleteKrs(krs))
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void deleteKrs(Krs krs) {
        swipeRefresh.setRefreshing(true);
        String npm = SharedPrefManager.getInstance(this).getNpm();
        apiService.actionKrs(npm, "batal", krs.getKodeMk()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(KrsActivity.this, "Mata kuliah dibatalkan", Toast.LENGTH_SHORT).show();
                    loadKrs();
                } else {
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(KrsActivity.this, "Gagal membatalkan", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(KrsActivity.this, "Koneksi gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openIsiKrs(View view) {
        android.content.Intent intent = new android.content.Intent(this, IsiKrsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
