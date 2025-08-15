package com.example.animedxd;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class ListActivity extends AppCompatActivity {

    private LinearLayout menuLayout;
    private Button logoutButton;
    private ConstraintLayout rootListLayout;
    private String username;

    // Custom Bottom Nav
    private LinearLayout navList, navHome, navAbout;
    private ImageView navListIcon, navHomeIcon, navAboutIcon;
    private TextView navListText, navHomeText, navAboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);
        rootListLayout = findViewById(R.id.root_layout);

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

        // Mengambil username dari Intent
        username = getIntent().getStringExtra("USERNAME");

        // Set state awal navigasi saat pertama kali masuk ke ListActivity
        updateBottomNavState("list");

        // Custom bottom navigation click listeners
        navHome.setOnClickListener(v -> {
            updateBottomNavState("home");
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            if (username != null) {
                intent.putExtra("USERNAME", username);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            // Hapus finish() di sini
        });

        navList.setOnClickListener(v -> {
            // Sudah di ListActivity, tidak perlu berpindah
            updateBottomNavState("list");
        });

        navAbout.setOnClickListener(v -> {
            updateBottomNavState("about");
            Intent intent = new Intent(ListActivity.this, AboutActivity.class);
            if (username != null) {
                intent.putExtra("USERNAME", username);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            // Hapus finish() di sini
        });

        if (menuLayout != null && logoutButton != null && rootListLayout != null) {
            setupLogoutMenu();
        } else {
            System.err.println("ERROR: Not calling setupLogoutMenu() due to missing UI components.");
        }
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
            Intent intent = new Intent(ListActivity.this, LoginActivity.class);
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

    private void navigateToDetail(String itemId) {
        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
        intent.putExtra("ITEM_ID", itemId);
        startActivity(intent);
    }

    public void openDetailPageAOT(View view) {
        navigateToDetail("aot");
    }

    public void openDetailPageHXH(View view) {
        navigateToDetail("hxh");
    }

    public void openDetailPageGintama(View view) {
        navigateToDetail("gintama");
    }

    public void openDetailPageLoveIsWar(View view) {
        navigateToDetail("love_is_war");
    }

    public void openDetailPageTheWitchFromMercury(View view) {
        navigateToDetail("the_witch_from_mercury");
    }

    public void openDetailPageTheMisfitOfDemonKing(View view) {
        navigateToDetail("the_misfit_of_demon_king");
    }

    public void openDetailPageOverlord(View view) {
        navigateToDetail("overlord");
    }

    // Metode untuk mengatur warna bottom navigation
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