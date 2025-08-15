package com.example.animedxd;

import android.content.Intent;
import android.graphics.Color;
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
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView buttonNews;
    private TextView buttonMangaList;
    private TextView welcomeTextView;

    private LinearLayout menuLayout;
    private Button logoutButton;

    // Custom Bottom Nav
    private LinearLayout navList, navHome, navAbout;
    private ImageView navListIcon, navHomeIcon, navAboutIcon;
    private TextView navListText, navHomeText, navAboutText;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tab button
        buttonNews = findViewById(R.id.buttonNews);
        buttonMangaList = findViewById(R.id.buttonMangaList);
        viewPager = findViewById(R.id.viewPager);
        welcomeTextView = findViewById(R.id.titlePage);

        // Logout menu
        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);

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

        // Ambil username
        username = getIntent().getStringExtra("USERNAME");
        if (username != null && !username.isEmpty()) {
            welcomeTextView.setText("Welcome, " + username + "!");
        } else {
            welcomeTextView.setText(R.string.welcome_home);
        }

        // ViewPager setup
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setUserInputEnabled(false);

        buttonNews.setOnClickListener(v -> {
            viewPager.setCurrentItem(0);
            updateButtonState(0);
        });

        buttonMangaList.setOnClickListener(v -> {
            viewPager.setCurrentItem(1);
            updateButtonState(1);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateButtonState(position);
            }
        });

        updateButtonState(0);

        // Custom bottom navigation click
        navHome.setOnClickListener(v -> {
            // Sudah di Home, set warna menjadi aktif
            updateBottomNavState("home");
        });

        navList.setOnClickListener(v -> {
            updateBottomNavState("list");
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            if (username != null) {
                intent.putExtra("USERNAME", username);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            // Hapus finish() di sini
        });

        navAbout.setOnClickListener(v -> {
            updateBottomNavState("about");
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            if (username != null) {
                intent.putExtra("USERNAME", username);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            // Hapus finish() di sini
        });


        // Set state awal navigasi saat pertama kali masuk
        updateBottomNavState("home");

        // Logout menu setup
        setupLogoutMenu();
    }

    private void setupLogoutMenu() {
        menuLayout.setOnClickListener(v -> {
            logoutButton.setVisibility(
                    logoutButton.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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

    private void updateButtonState(int position) {
        if (position == 0) {
            buttonNews.setBackgroundResource(R.drawable.tab_background_selected);
            buttonNews.setTextColor(getResources().getColor(R.color.white, null));
            buttonMangaList.setBackgroundResource(R.drawable.tab_background_unselected);
            buttonMangaList.setTextColor(getResources().getColor(R.color.black, null));
        } else {
            buttonNews.setBackgroundResource(R.drawable.tab_background_unselected);
            buttonNews.setTextColor(getResources().getColor(R.color.black, null));
            buttonMangaList.setBackgroundResource(R.drawable.tab_background_selected);
            buttonMangaList.setTextColor(getResources().getColor(R.color.white, null));
        }
    }

    private void updateBottomNavState(String activeNav) {
        int activeColor = ContextCompat.getColor(this, R.color.black);
        int inactiveColor = ContextCompat.getColor(this, R.color.dark_grey); // Pastikan warna gray sudah didefinisikan

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