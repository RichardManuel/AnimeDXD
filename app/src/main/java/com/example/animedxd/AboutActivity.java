package com.example.animedxd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.graphics.Rect;

public class AboutActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private LinearLayout menuLayout;
    private Button logoutButton;
    private View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);
        rootLayout = findViewById(R.id.root_layout);

        String username = getIntent().getStringExtra("USERNAME");
        if (username != null && !username.isEmpty()) {
            welcomeTextView.setText("Welcome, " + username + "!");
        } else {
            welcomeTextView.setText(R.string.welcome_home);
        }

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (bottomNavigationView == null) {
            System.err.println("ERROR: bottomNavigationView is null. Check R.id.bottomNavigation in activity_list.xml");
        } else {
            // Set item aktif ke 'Book'
            bottomNavigationView.setSelectedItemId(R.id.bottom_about);

            // Listener ketika item diklik
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
                    return true;
                }
                return false;
            });
        }
        return super.dispatchTouchEvent(event);
    }
}