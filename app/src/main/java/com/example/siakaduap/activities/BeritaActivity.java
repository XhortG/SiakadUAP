package com.example.siakaduap.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.siakaduap.R;
import com.example.siakaduap.adapters.BeritaAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Berita;
import com.example.siakaduap.models.BeritaResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaActivity extends AppCompatActivity {

    private RecyclerView rvBerita;
    private SwipeRefreshLayout swipeRefresh;
    private TextView tvEmpty;
    private EditText etSearch;
    private BeritaAdapter adapter;
    private List<Berita> allBeritaList = new ArrayList<>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        // Fix: Pasang listener tombol Back pada TopAppBar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rvBerita    = findViewById(R.id.rvBerita);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tvEmpty     = findViewById(R.id.tvEmpty);
        etSearch    = findViewById(R.id.etSearch);

        rvBerita.setLayoutManager(new LinearLayoutManager(this));
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Fix: Fitur pencarian / filter real-time
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBerita(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        swipeRefresh.setOnRefreshListener(this::loadBerita);
        swipeRefresh.setRefreshing(true);
        loadBerita();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /** Filter list berita berdasarkan judul atau isi yang diketik */
    private void filterBerita(String query) {
        List<Berita> filtered = new ArrayList<>();
        for (Berita b : allBeritaList) {
            if (b.getJudul().toLowerCase().contains(query.toLowerCase())
                    || b.getIsi().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(b);
            }
        }
        if (filtered.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("Tidak ada berita yang cocok dengan \"" + query + "\"");
            rvBerita.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvBerita.setVisibility(View.VISIBLE);
            adapter = new BeritaAdapter(BeritaActivity.this, filtered);
            rvBerita.setAdapter(adapter);
        }
    }

    private void loadBerita() {
        apiService.getBerita().enqueue(new Callback<BeritaResponse>() {
            @Override
            public void onResponse(Call<BeritaResponse> call, Response<BeritaResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        allBeritaList = response.body().getData();
                        if (allBeritaList == null || allBeritaList.isEmpty()) {
                            showEmpty("Belum ada berita tersedia.");
                        } else {
                            tvEmpty.setVisibility(View.GONE);
                            rvBerita.setVisibility(View.VISIBLE);
                            adapter = new BeritaAdapter(BeritaActivity.this, allBeritaList);
                            rvBerita.setAdapter(adapter);
                        }
                    } else {
                        showEmpty(response.body().getMessage());
                    }
                } else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Maaf, server akademik sedang dalam pemeliharaan.");
                }
            }

            @Override
            public void onFailure(Call<BeritaResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang dalam pemeliharaan.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba Lagi", v -> loadBerita())
                        .show();
            }
        });
    }

    private void showEmpty(String message) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(message);
        rvBerita.setVisibility(View.GONE);
    }
}
