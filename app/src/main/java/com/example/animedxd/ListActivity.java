package com.example.animedxd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.MotionEvent;
import android.graphics.Rect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;



import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListActivity extends AppCompatActivity {

    private LinearLayout menuLayout;
    private Button logoutButton;
    private ConstraintLayout rootListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);
        rootListLayout = findViewById(R.id.root_layout);

        if (menuLayout == null) {
            System.err.println("ERROR: menuLayout is null. Check R.id.menu_layout in activity_list.xml");
        }
        if (logoutButton == null) {
            System.err.println("ERROR: logoutButton is null. Check R.id.logout_button in activity_list.xml");
        }
        if (rootListLayout == null) {
            System.err.println("ERROR: rootListLayout is null. Check R.id.root_layout in activity_list.xml");
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (bottomNavigationView == null) {
            System.err.println("ERROR: bottomNavigationView is null. Check R.id.bottomNavigation in activity_list.xml");
        } else {
            bottomNavigationView.setSelectedItemId(R.id.bottom_book);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.bottom_home) {
                    startActivity(new Intent(ListActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                } else if (itemId == R.id.bottom_book) {
                    return true;

                } else if (itemId == R.id.bottom_about) {
                    startActivity(new Intent(ListActivity.this, AboutActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                }
                return false;
            });
        }

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

        rootListLayout.setOnClickListener(v -> {
            if (logoutButton.getVisibility() == View.VISIBLE) {
                logoutButton.setVisibility(View.GONE);
            }
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
}