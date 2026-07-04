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
import com.example.siakaduap.adapters.AbsenAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Absensi;
import com.example.siakaduap.models.AbsensiResponse;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenActivity extends AppCompatActivity {
    private RecyclerView rvAbsen;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private AbsenAdapter adapter;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);
        
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        rvAbsen = findViewById(R.id.rvAbsen);
        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmpty);
        searchView = findViewById(R.id.searchView);
        
        rvAbsen.setLayoutManager(new LinearLayoutManager(this));
        
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
        
        loadAbsensi();
    }
    
    private void loadAbsensi() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        
        String npm = SharedPrefManager.getInstance(this).getNpm();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<AbsensiResponse> call = apiService.getAbsensi(npm);
        
        call.enqueue(new Callback<AbsensiResponse>() {
            @Override
            public void onResponse(Call<AbsensiResponse> call, Response<AbsensiResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    if(response.body().isStatus()) {
                        List<Absensi> list = response.body().getData();
                        if(list != null && !list.isEmpty()){
                            adapter = new AbsenAdapter(AbsenActivity.this, list);
                            rvAbsen.setAdapter(adapter);
                        } else {
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText(response.body().getMessage());
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang dalam pemeliharaan.", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AbsensiResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang dalam pemeliharaan.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba Lagi", v -> loadAbsensi())
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
