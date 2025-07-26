package com.example.animedxd;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set item aktif ke 'Book'
        bottomNavigationView.setSelectedItemId(R.id.bottom_book);

        // Listener ketika item diklik
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(BookActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;

            } else if (itemId == R.id.bottom_book) {
                // Sudah di halaman Book
                return true;

            } else if (itemId == R.id.bottom_about) {
                startActivity(new Intent(BookActivity.this, AboutActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }

            return false;
        });
    }
}
