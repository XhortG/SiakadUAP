package com.example.siakaduap.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;

public class ProfilFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        
        TextView tvNama  = view.findViewById(R.id.tvNamaProfil);
        TextView tvNim   = view.findViewById(R.id.tvNimProfil);
        TextView tvProdi = view.findViewById(R.id.tvProdiProfil);
        TextView tvEmail = view.findViewById(R.id.tvEmailProfil);
        TextView tvFakultas = view.findViewById(R.id.tvFakultasProfil);
        TextView tvSemester = view.findViewById(R.id.tvSemesterProfil);
        TextView tvTahunMasuk = view.findViewById(R.id.tvTahunMasukProfil);
        TextView tvStatus = view.findViewById(R.id.tvStatusProfil);

        SharedPrefManager spm = SharedPrefManager.getInstance(requireContext());
        if (tvNama != null) tvNama.setText(spm.getNama());
        if (tvNim != null) tvNim.setText(spm.getNpm());
        if (tvProdi != null) tvProdi.setText(spm.getProdi());
        if (tvEmail != null) tvEmail.setText(spm.getEmail());
        if (tvFakultas != null) tvFakultas.setText(spm.getFakultas().isEmpty() ? "-" : spm.getFakultas());
        if (tvSemester != null) tvSemester.setText(spm.getSemester().isEmpty() ? "-" : spm.getSemester());
        if (tvTahunMasuk != null) tvTahunMasuk.setText(spm.getTahunMasuk().isEmpty() ? "-" : spm.getTahunMasuk());
        if (tvStatus != null) {
            String status = spm.getStatusMahasiswa();
            tvStatus.setText(status.isEmpty() ? "-" : status);
            if ("Aktif".equalsIgnoreCase(status)) {
                tvStatus.setTextColor(getResources().getColor(R.color.success_green));
            } else {
                tvStatus.setTextColor(getResources().getColor(R.color.md_error));
            }
        }
        
        return view;
    }
}
