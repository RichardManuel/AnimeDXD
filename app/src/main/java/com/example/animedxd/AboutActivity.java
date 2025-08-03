package com.example.animedxd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private LinearLayout menuLayout;
    private Button logoutButton;
    private View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Inisialisasi UI
        welcomeTextView = findViewById(R.id.welcomeTextView);
        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);
        rootLayout = findViewById(R.id.root_layout);

        // --- Mengambil dan Menampilkan Username ---
        // Syarat: Greeting message to display “Welcome, [Username]”
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null && !username.isEmpty()) {
            welcomeTextView.setText("Welcome, " + username + " !");
        } else {
            welcomeTextView.setText("Welcome, User !");
        }

        // --- Setup Menu Logout ---
        // Syarat: Menu is used to show a list of menus that users can access
        setupLogoutMenu();
    }

    private void setupLogoutMenu() {
        // Listener untuk menampilkan/menyembunyikan tombol logout
        menuLayout.setOnClickListener(v -> {
            if (logoutButton.getVisibility() == View.VISIBLE) {
                logoutButton.setVisibility(View.GONE);
            } else {
                logoutButton.setVisibility(View.VISIBLE);
            }
        });

        // Listener untuk tombol logout
        logoutButton.setOnClickListener(v -> {
            // Pindah kembali ke LoginActivity
            Intent intent = new Intent(AboutActivity.this, LoginActivity.class);
            // Membersihkan semua activity sebelumnya dari stack
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Tutup AboutActivity
        });

        // Syarat: Hide the menu dropdown if the user clicks on another area
        rootLayout.setOnClickListener(v -> {
            if (logoutButton.getVisibility() == View.VISIBLE) {
                logoutButton.setVisibility(View.GONE);
            }
        });
    }
}
