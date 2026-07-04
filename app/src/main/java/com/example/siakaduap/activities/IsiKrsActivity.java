package com.example.siakaduap.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;
import com.example.siakaduap.adapters.MkTersediaAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.LoginResponse;
import com.example.siakaduap.models.MataKuliah;
import com.example.siakaduap.models.MkTersediaResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IsiKrsActivity extends AppCompatActivity {

    private RecyclerView rvMkTersedia;
    private SwipeRefreshLayout swipeRefresh;
    private TextView tvEmpty;
    private MkTersediaAdapter adapter;
    private List<MataKuliah> mkList = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_krs);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rvMkTersedia = findViewById(R.id.rvMkTersedia);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tvEmpty = findViewById(R.id.tvEmpty);

        rvMkTersedia.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new MkTersediaAdapter(this, mkList, this::confirmAmbil);
        rvMkTersedia.setAdapter(adapter);

        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        swipeRefresh.setOnRefreshListener(this::loadMkTersedia);
        loadMkTersedia();
    }

    private void loadMkTersedia() {
        swipeRefresh.setRefreshing(true);
        String npm = SharedPrefManager.getInstance(this).getNpm();

        apiService.getMkTersedia(npm).enqueue(new Callback<MkTersediaResponse>() {
            @Override
            public void onResponse(Call<MkTersediaResponse> call, Response<MkTersediaResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        mkList.clear();
                        if (response.body().getData() != null) {
                            mkList.addAll(response.body().getData());
                        }
                        adapter.notifyDataSetChanged();

                        if (mkList.isEmpty()) {
                            tvEmpty.setVisibility(View.VISIBLE);
                            rvMkTersedia.setVisibility(View.GONE);
                        } else {
                            tvEmpty.setVisibility(View.GONE);
                            rvMkTersedia.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MkTersediaResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Snackbar.make(findViewById(android.R.id.content), "Koneksi gagal, silakan coba lagi", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmAmbil(MataKuliah mk, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Ambil Mata Kuliah")
                .setMessage("Apakah Anda yakin ingin mengambil " + mk.getNamaMk() + "?")
                .setPositiveButton("Ya", (dialog, which) -> addKrs(mk, position))
                .setNegativeButton("Batal", null)
                .show();
    }

    private void addKrs(MataKuliah mk, int position) {
        swipeRefresh.setRefreshing(true);
        String npm = SharedPrefManager.getInstance(this).getNpm();
        apiService.actionKrs(npm, "tambah", mk.getKodeMk()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(IsiKrsActivity.this, mk.getNamaMk() + " berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                    // Remove item from list
                    if (position >= 0 && position < mkList.size()) {
                        mkList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, mkList.size());
                    }
                    if (mkList.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        rvMkTersedia.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(IsiKrsActivity.this, "Gagal: " + (response.body() != null ? response.body().getMessage() : "Error"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(IsiKrsActivity.this, "Koneksi gagal, silakan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
