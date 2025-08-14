package com.example.animedxd;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigation;

    // Tambahkan referensi untuk TextView
    private TextView buttonNews;
    private TextView buttonMangaList;
    private TextView welcomeTextView; // Deklarasi TextView baru untuk welcome message

    // Tambahkan referensi untuk menu logout
    private LinearLayout menuLayout;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi View
        buttonNews = findViewById(R.id.buttonNews);
        buttonMangaList = findViewById(R.id.buttonMangaList);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        welcomeTextView = findViewById(R.id.titlePage); // Inisialisasi TextView welcome

        // Inisialisasi komponen menu logout
        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);

        // Mengambil username dari Intent
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null && !username.isEmpty()) {
            welcomeTextView.setText("Welcome, " + username + "!");
        } else {
            welcomeTextView.setText(R.string.welcome_home);
        }

        // Membuat adapter untuk ViewPager
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Tambahkan listener untuk klik tombol
        buttonNews.setOnClickListener(v -> {
            viewPager.setCurrentItem(0);
            updateButtonState(0);
        });

        buttonMangaList.setOnClickListener(v -> {
            viewPager.setCurrentItem(1);
            updateButtonState(1);
        });

        // Listener untuk sinkronisasi ViewPager ke tombol
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateButtonState(position);
            }
        });

        // Set tombol awal aktif
        updateButtonState(0);

        // Inisialisasi BottomNavigationView
        if (bottomNavigation == null) {
            System.err.println("ERROR: bottomNavigationView is null. Check R.id.bottomNavigation in activity_main.xml");
        } else {
            bottomNavigation.setSelectedItemId(R.id.bottom_home);
            bottomNavigation.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    return true;
                } else if (itemId == R.id.bottom_book) {
                    startActivity(new Intent(MainActivity.this, ListActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                } else if (itemId == R.id.bottom_about) {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                }
                return false;
            });
        }

        // Setup menu logout
        if (menuLayout != null && logoutButton != null) {
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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (logoutButton != null && menuLayout != null && logoutButton.getVisibility() == View.VISIBLE) {
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
}
