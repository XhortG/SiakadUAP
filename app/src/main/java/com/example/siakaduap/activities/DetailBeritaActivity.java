package com.example.siakaduap.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siakaduap.R;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;

public class DetailBeritaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);

        // Fix #1: Pasang listener tombol Back pada TopAppBar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        TextView tvJudul   = findViewById(R.id.tvJudulDetail);
        Chip     tvTanggal = findViewById(R.id.tvTanggalDetail);
        TextView tvIsi     = findViewById(R.id.tvIsiDetail);

        // Fix #2: Null-check pada semua Intent extra agar tidak crash
        String judul   = getIntent().getStringExtra("judul");
        String tanggal = getIntent().getStringExtra("tanggal");
        String isi     = getIntent().getStringExtra("isi");

        tvJudul.setText(judul     != null ? judul   : "");
        tvTanggal.setText(tanggal != null ? tanggal : "");
        tvIsi.setText(isi         != null ? isi     : "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
