package com.example.siakaduap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View cardLogo = findViewById(R.id.cardLogo);
        View tvTitle = findViewById(R.id.tvTitle);
        View tvSubtitle = findViewById(R.id.tvSubtitle);
        
        // Simple alpha & translation animation without needing xml files
        cardLogo.setAlpha(0f);
        cardLogo.setTranslationY(50f);
        cardLogo.animate().alpha(1f).translationY(0f).setDuration(800).start();

        tvTitle.setAlpha(0f);
        tvTitle.setTranslationY(30f);
        tvTitle.animate().alpha(1f).translationY(0f).setDuration(800).setStartDelay(200).start();

        tvSubtitle.setAlpha(0f);
        tvSubtitle.setTranslationY(30f);
        tvSubtitle.animate().alpha(1f).translationY(0f).setDuration(800).setStartDelay(400).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            // Transisi fade masuk ke halaman berikutnya
            overridePendingTransition(R.anim.fade_in, R.anim.slide_out_left);
            finish();
        }, 2200);
    }
}
