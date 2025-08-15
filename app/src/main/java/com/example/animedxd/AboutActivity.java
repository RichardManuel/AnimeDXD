package com.example.animedxd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Rect;
import android.graphics.PorterDuff;
import androidx.core.content.ContextCompat;



public class AboutActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private LinearLayout menuLayout;
    private Button logoutButton;
    private View rootLayout;
    private TextView usernameTextView;

    private String username;

    // Custom Bottom Nav
    private LinearLayout navList, navHome, navAbout;
    private ImageView navListIcon, navHomeIcon, navAboutIcon;
    private TextView navListText, navHomeText, navAboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);
        rootLayout = findViewById(R.id.root_layout);
        usernameTextView = findViewById(R.id.usernameTextView);

        // Custom bottom navigation
        navList = findViewById(R.id.nav_list);
        navHome = findViewById(R.id.nav_home);
        navAbout = findViewById(R.id.nav_about);

        // Ikon dan Teks Navigasi
        navListIcon = findViewById(R.id.nav_list_icon);
        navHomeIcon = findViewById(R.id.nav_home_icon);
        navAboutIcon = findViewById(R.id.nav_about_icon);
        navListText = findViewById(R.id.nav_list_text);
        navHomeText = findViewById(R.id.nav_home_text);
        navAboutText = findViewById(R.id.nav_about_text);

        // Ambil username dari Intent yang dikirim dari Activity sebelumnya
        username = getIntent().getStringExtra("USERNAME");
        if (username != null && !username.isEmpty()) {
            usernameTextView.setText("Welcome, " + username + "!");
        } else {
            usernameTextView.setText("Welcome, User!");
        }

        // Set state awal navigasi saat pertama kali masuk ke AboutActivity
        updateBottomNavState("about");

// Custom bottom navigation click listeners
        navHome.setOnClickListener(v -> {
            updateBottomNavState("home");
            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            if (username != null) {
                intent.putExtra("USERNAME", username);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            // Hapus finish() di sini
        });

        navList.setOnClickListener(v -> {
            updateBottomNavState("list");
            Intent intent = new Intent(AboutActivity.this, ListActivity.class);
            if (username != null) {
                intent.putExtra("USERNAME", username);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            // Hapus finish() di sini
        });

        navAbout.setOnClickListener(v -> {
            // Sudah di AboutActivity, tidak perlu berpindah atau menutup activity
            updateBottomNavState("about");
        });
        setupLogoutMenu();
    }

    private void setupLogoutMenu() {
        menuLayout.setOnClickListener(v -> {
            if (logoutButton.getVisibility() == View.VISIBLE) {
                logoutButton.setVisibility(View.GONE);
            } else {
                logoutButton.setVisibility(View.VISIBLE);
            }
        });

        logoutButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(LoginActivity.USERNAME_KEY);
            editor.apply();

            Intent intent = new Intent(AboutActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (logoutButton.getVisibility() == View.VISIBLE) {
                Rect outRect = new Rect();
                logoutButton.getGlobalVisibleRect(outRect);
                Rect menuRect = new Rect();
                menuLayout.getGlobalVisibleRect(menuRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()) &&
                        !menuRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    logoutButton.setVisibility(View.GONE);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    // Method untuk mengatur warna bottom navigation
    private void updateBottomNavState(String activeNav) {
        int activeColor = ContextCompat.getColor(this, R.color.black);
        int inactiveColor = ContextCompat.getColor(this, R.color.dark_grey);

        // Reset semua item menjadi tidak aktif
        navListIcon.setColorFilter(inactiveColor, PorterDuff.Mode.SRC_IN);
        navListText.setTextColor(inactiveColor);
        navHomeIcon.setColorFilter(inactiveColor, PorterDuff.Mode.SRC_IN);
        navHomeText.setTextColor(inactiveColor);
        navAboutIcon.setColorFilter(inactiveColor, PorterDuff.Mode.SRC_IN);
        navAboutText.setTextColor(inactiveColor);

        // Set item yang aktif
        switch (activeNav) {
            case "home":
                navHomeIcon.setColorFilter(activeColor, PorterDuff.Mode.SRC_IN);
                navHomeText.setTextColor(activeColor);
                break;
            case "list":
                navListIcon.setColorFilter(activeColor, PorterDuff.Mode.SRC_IN);
                navListText.setTextColor(activeColor);
                break;
            case "about":
                navAboutIcon.setColorFilter(activeColor, PorterDuff.Mode.SRC_IN);
                navAboutText.setTextColor(activeColor);
                break;
        }
    }
}