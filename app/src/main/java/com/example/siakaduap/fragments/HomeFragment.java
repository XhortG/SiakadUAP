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
import com.example.siakaduap.activities.MainActivity;
import com.example.siakaduap.adapters.PengumumanAdapter;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.Pengumuman;
import com.example.siakaduap.models.PengumumanResponse;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView rvPengumuman;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        TextView tvWelcome = view.findViewById(R.id.tvWelcome);
        TextView tvGreeting = view.findViewById(R.id.tvGreeting);
        
        if (tvWelcome != null) {
            String nama = SharedPrefManager.getInstance(requireContext()).getNama();
            tvWelcome.setText(nama.isEmpty() ? "Mahasiswa" : nama);
        }
        
        if (tvGreeting != null) {
            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
            if(timeOfDay >= 0 && timeOfDay < 12){
                tvGreeting.setText("Selamat Pagi,");
            } else if(timeOfDay >= 12 && timeOfDay < 15){
                tvGreeting.setText("Selamat Siang,");
            } else if(timeOfDay >= 15 && timeOfDay < 18){
                tvGreeting.setText("Selamat Sore,");
            } else {
                tvGreeting.setText("Selamat Malam,");
            }
        }
        
        // Setup click listeners for Quick Access
        View.OnClickListener listener = v -> {
            if (getActivity() instanceof MainActivity) {
                MainActivity main = (MainActivity) getActivity();
                int id = v.getId();
                if (id == R.id.btnKrs) main.openKrs(v);
                else if (id == R.id.btnKhs) main.openKhs(v);
                else if (id == R.id.btnJadwal) main.openJadwal(v);
                else if (id == R.id.btnAbsen) main.openAbsen(v);
                else if (id == R.id.btnPembayaran) main.openPembayaran(v);
            }
        };

        view.findViewById(R.id.btnKrs).setOnClickListener(listener);
        view.findViewById(R.id.btnKhs).setOnClickListener(listener);
        view.findViewById(R.id.btnJadwal).setOnClickListener(listener);
        view.findViewById(R.id.btnAbsen).setOnClickListener(listener);
        view.findViewById(R.id.btnPembayaran).setOnClickListener(listener);
        
        // Setup Pengumuman
        rvPengumuman = view.findViewById(R.id.rvPengumuman);
        progressBar = view.findViewById(R.id.progressBar);
        rvPengumuman.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        loadPengumuman();
        
        return view;
    }
    
    private void loadPengumuman() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<PengumumanResponse> call = apiService.getPengumuman();
        
        call.enqueue(new Callback<PengumumanResponse>() {
            @Override
            public void onResponse(Call<PengumumanResponse> call, Response<PengumumanResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        List<Pengumuman> list = response.body().getData();
                        if (list != null && !list.isEmpty()) {
                            PengumumanAdapter adapter = new PengumumanAdapter(requireContext(), list);
                            rvPengumuman.setAdapter(adapter);
                        }
                    }
                } else {
                    if (getView() != null) {
                        Snackbar.make(getView(), "Respons server tidak valid", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PengumumanResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (getView() != null) {
                    Snackbar.make(getView(), "Koneksi gagal. Periksa server Anda.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Coba Lagi", v -> loadPengumuman())
                            .show();
                }
            }
        });
    }
}
