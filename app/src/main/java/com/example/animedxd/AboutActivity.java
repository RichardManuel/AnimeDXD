package com.example.animedxd;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set item aktif ke 'About'
        bottomNavigationView.setSelectedItemId(R.id.bottom_about);

        // Listener ketika navigasi diklik
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(AboutActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;

            } else if (itemId == R.id.bottom_book) {
                startActivity(new Intent(AboutActivity.this, ListActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;

            } else if (itemId == R.id.bottom_about) {
                // Sudah di halaman About
                return true;
            }

            return false;
        });
    }
}
