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
import com.example.siakaduap.adapters.JadwalAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Jadwal;
import com.example.siakaduap.models.JadwalResponse;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalActivity extends AppCompatActivity {
    private RecyclerView rvJadwal;
    private ProgressBar progressBar;
    private View layoutEmpty;
    private TextView tvEmptyText;
    private JadwalAdapter adapter;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rvJadwal = findViewById(R.id.rvJadwal);
        progressBar = findViewById(R.id.progressBar);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        tvEmptyText = findViewById(R.id.tvEmptyText);
        searchView = findViewById(R.id.searchView);
        
        rvJadwal.setLayoutManager(new LinearLayoutManager(this));
        
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        
        loadJadwal();
    }
    
    private void loadJadwal() {
        progressBar.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
        
        String npm = SharedPrefManager.getInstance(this).getNpm();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<JadwalResponse> call = apiService.getJadwal(npm);
        
        call.enqueue(new Callback<JadwalResponse>() {
            @Override
            public void onResponse(Call<JadwalResponse> call, Response<JadwalResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    if(response.body().isStatus()) {
                        List<Jadwal> list = response.body().getData();
                        if(list != null && !list.isEmpty()){
                            adapter = new JadwalAdapter(JadwalActivity.this, list);
                            rvJadwal.setAdapter(adapter);
                        } else {
                            layoutEmpty.setVisibility(View.VISIBLE);
                            tvEmptyText.setText("Yey! Tidak ada kelas hari ini");
                        }
                    } else {
                        layoutEmpty.setVisibility(View.VISIBLE);
                        tvEmptyText.setText(response.body().getMessage());
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang dalam pemeliharaan.", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JadwalResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang dalam pemeliharaan.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba Lagi", v -> loadJadwal())
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
