package com.example.siakaduap.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.siakaduap.R;
import com.google.android.material.appbar.MaterialToolbar;

public class PengumumanDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengumuman_detail);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        TextView tvJudul = findViewById(R.id.tvJudul);
        TextView tvTanggal = findViewById(R.id.tvTanggal);
        TextView tvIsi = findViewById(R.id.tvIsi);

        String judul = getIntent().getStringExtra("judul");
        String tanggal = getIntent().getStringExtra("tanggal");
        String isi = getIntent().getStringExtra("isi");

        if (judul != null) tvJudul.setText(judul);
        if (tanggal != null) tvTanggal.setText(tanggal);
        if (isi != null) tvIsi.setText(isi);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
