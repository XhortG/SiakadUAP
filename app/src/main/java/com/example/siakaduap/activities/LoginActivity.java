package com.example.siakaduap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siakaduap.R;
import com.example.siakaduap.SharedPrefManager;
import com.example.siakaduap.api.ApiClient;
import com.example.siakaduap.api.ApiService;
import com.example.siakaduap.models.LoginResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private TextInputLayout tilEmail, tilPassword;
    private MaterialButton btnLogin;
    private LinearProgressIndicator progressBar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String npm = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // MD3 field error states
        tilEmail.setError(null);
        tilPassword.setError(null);

        if (npm.isEmpty()) {
            tilEmail.setError("NPM diperlukan");
            return;
        }
        if (password.isEmpty()) {
            tilPassword.setError("Password diperlukan");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        apiService.login(npm, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        SharedPrefManager.getInstance(LoginActivity.this).userLogin(response.body().getData());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        // Slide in ke dashboard
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    } else {
                        tilPassword.setError(response.body().getMessage());
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang dalam pemeliharaan.", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                Snackbar.make(findViewById(android.R.id.content), "Maaf, server akademik sedang dalam pemeliharaan.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba Lagi", v -> loginUser())
                        .show();
            }
        });
    }
}
