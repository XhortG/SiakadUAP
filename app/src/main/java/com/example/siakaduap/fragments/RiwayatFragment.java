package com.example.siakaduap.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;
import com.example.siakaduap.adapters.RiwayatAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Riwayat;
import com.example.siakaduap.models.RiwayatResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatFragment extends Fragment {

    private RecyclerView rvRiwayat;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        
        rvRiwayat = view.findViewById(R.id.rvRiwayat);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        
        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));
        
        loadRiwayat();
        
        return view;
    }

    private void loadRiwayat() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        
        String npm = SharedPrefManager.getInstance(getContext()).getNpm();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<RiwayatResponse> call = apiService.getRiwayat(npm);
        
        call.enqueue(new Callback<RiwayatResponse>() {
            @Override
            public void onResponse(Call<RiwayatResponse> call, Response<RiwayatResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    if(response.body().isStatus()) {
                        List<Riwayat> list = response.body().getData();
                        if(list != null && !list.isEmpty()){
                            RiwayatAdapter adapter = new RiwayatAdapter(getContext(), list);
                            rvRiwayat.setAdapter(adapter);
                        } else {
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText(response.body().getMessage());
                    }
                } else {
                    if (getView() != null) {
                        Snackbar.make(getView(), "Respons server tidak valid", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RiwayatResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (getView() != null) {
                    Snackbar.make(getView(), "Koneksi gagal. Periksa server Anda.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Coba Lagi", v -> loadRiwayat())
                            .show();
                }
            }
        });
    }
}
