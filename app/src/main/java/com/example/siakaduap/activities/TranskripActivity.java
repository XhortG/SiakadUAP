package com.example.siakaduap.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;
import com.example.siakaduap.adapters.TranskripAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Khs;
import com.example.siakaduap.models.TranskripResponse;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranskripActivity extends AppCompatActivity {

    private RecyclerView rvTranskrip;
    private TranskripAdapter adapter;
    private List<Khs> transkripList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private TextView tvEmpty, tvTotalSks, tvIpk;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transkrip);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> onBackPressed());

        rvTranskrip = findViewById(R.id.rvTranskrip);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvTotalSks = findViewById(R.id.tvTotalSks);
        tvIpk = findViewById(R.id.tvIpk);

        rvTranskrip.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TranskripAdapter(transkripList);
        rvTranskrip.setAdapter(adapter);

        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        loadTranskrip();

        swipeRefresh.setOnRefreshListener(this::loadTranskrip);
    }

    private void loadTranskrip() {
        swipeRefresh.setRefreshing(true);
        String npm = SharedPrefManager.getInstance(this).getNpm();

        apiService.getTranskrip(npm).enqueue(new Callback<TranskripResponse>() {
            @Override
            public void onResponse(Call<TranskripResponse> call, Response<TranskripResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        transkripList.clear();
                        transkripList.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                        
                        tvTotalSks.setText(String.valueOf((int)response.body().getTotalSks()));
                        tvIpk.setText(String.format("%.2f", response.body().getIpk()));

                        if (transkripList.isEmpty()) {
                            tvEmpty.setVisibility(View.VISIBLE);
                            rvTranskrip.setVisibility(View.GONE);
                        } else {
                            tvEmpty.setVisibility(View.GONE);
                            rvTranskrip.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(TranskripActivity.this, "Gagal memuat transkrip", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TranskripResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(TranskripActivity.this, "Koneksi error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
