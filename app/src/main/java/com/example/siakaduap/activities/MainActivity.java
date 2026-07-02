package com.example.siakaduap.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;
import com.example.siakaduap.fragments.HomeFragment;
import com.example.siakaduap.fragments.ProfilFragment;
import com.example.siakaduap.fragments.RiwayatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.nav_riwayat) {
                    selectedFragment = new RiwayatFragment();
                } else if (itemId == R.id.nav_profil) {
                    selectedFragment = new ProfilFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        });

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        // Fix onBackPressed using OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Keluar Aplikasi")
                        .setMessage("Apakah Anda yakin ingin menutup aplikasi?")
                        .setPositiveButton("Ya, Keluar", (dialog, which) -> {
                            finishAffinity();
                        })
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });
    }

    /** Navigasi ke setiap halaman dengan slide transition */
    private void navigateTo(Class<?> target) {
        startActivity(new Intent(this, target));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openKrs(View view)        { navigateTo(KrsActivity.class); }
    public void openKhs(View view)        { navigateTo(KhsActivity.class); }
    public void openTranskrip(View view)  { navigateTo(TranskripActivity.class); }
    public void openBerita(View view)     { navigateTo(BeritaActivity.class); }
    public void openAbsen(View view)      { navigateTo(AbsenActivity.class); }
    public void openJadwal(View view)     { navigateTo(JadwalActivity.class); }
    public void openPembayaran(View view) { navigateTo(PembayaranActivity.class); }
    
    // openRiwayat and openProfil switch tabs instead of starting activities
    public void openRiwayat(View view) {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_riwayat);
    }

    public void openProfil(View view) {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_profil);
    }

    public void logout(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Keluar Akun")
                .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
                .setPositiveButton("Ya, Keluar", (dialog, which) -> {
                    SharedPrefManager.getInstance(this).logout();
                    startActivity(new Intent(this, LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
