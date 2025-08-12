package com.example.animedxd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

// It's good practice to ensure all explicit imports are present,
// though Android Studio often handles this.
// import com.example.animedxd.R; // R is usually implicitly available
// import com.example.animedxd.DetailActivity; // Already imported
// import com.example.animedxd.AboutActivity; // Already imported
// import com.example.animedxd.MainActivity; // Already imported
// import com.example.animedxd.LoginActivity; // Make sure this class exists

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListActivity extends AppCompatActivity {

    private LinearLayout menuLayout;
    private Button logoutButton;
    private ConstraintLayout rootListLayout; // Changed from root_list_layout to root_layout based on your XML

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list); // <<<< SET CONTENT VIEW FIRST

        // --- Now initialize views ---
        menuLayout = findViewById(R.id.menu_layout);
        logoutButton = findViewById(R.id.logout_button);
        // In your XML, the root ConstraintLayout has android:id="@+id/root_layout"
        rootListLayout = findViewById(R.id.root_layout);

        // --- Defensive null checks (good practice, especially during debugging) ---
        if (menuLayout == null) {
            // Log an error or throw an exception, e.g.:
            // throw new RuntimeException("menuLayout not found. Check activity_list.xml");
            System.err.println("ERROR: menuLayout is null. Check R.id.menu_layout in activity_list.xml");
            // You might want to return or handle this gracefully to prevent further crashes
            // if a critical UI component is missing, though the app might still crash later.
        }
        if (logoutButton == null) {
            System.err.println("ERROR: logoutButton is null. Check R.id.logout_button in activity_list.xml");
        }
        if (rootListLayout == null) {
            System.err.println("ERROR: rootListLayout is null. Check R.id.root_layout in activity_list.xml");
        }


        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (bottomNavigationView == null) {
            System.err.println("ERROR: bottomNavigationView is null. Check R.id.bottomNavigation in activity_list.xml");
        } else {
            // Set item aktif ke 'Book'
            bottomNavigationView.setSelectedItemId(R.id.bottom_book);

            // Listener ketika item diklik
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.bottom_home) {
                    startActivity(new Intent(ListActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                } else if (itemId == R.id.bottom_book) {
                    // Sudah di halaman Book
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

        // Only call setupLogoutMenu if the necessary views were found
        if (menuLayout != null && logoutButton != null && rootListLayout != null) {
            setupLogoutMenu();
        } else {
            System.err.println("ERROR: Not calling setupLogoutMenu() due to missing UI components.");
        }
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
            Intent intent = new Intent(ListActivity.this, LoginActivity.class);
            // Membersihkan semua activity sebelumnya dari stack
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            // You are calling finish() on ListActivity. The comment said "Tutup AboutActivity"
            // but this is ListActivity, which is correct here.
            finish();
        });

        // Syarat: Hide the menu dropdown if the user clicks on another area
        rootListLayout.setOnClickListener(v -> {
            if (logoutButton.getVisibility() == View.VISIBLE) {
                logoutButton.setVisibility(View.GONE);
            }
        });
    }

    // ... (rest of your ListActivity.java code: navigateToDetail and openDetailPage methods) ...
    private void navigateToDetail(String itemId) {
        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
        intent.putExtra("ITEM_ID", itemId); // Pass the specific item's ID
        startActivity(intent);
        // Optional: overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // Method called by the first card's android:onClick="openAotDetail"
    public void openDetailPageAOT(View view) {
        navigateToDetail("aot");
    }

    // Method called by the second card's android:onClick="openHxhDetail"
    public void openDetailPageHXH(View view) {
        navigateToDetail("hxh");
    }

    // Method called by the third card's android:onClick="openGintamaDetail"
    public void openDetailPageGintama(View view) {
        navigateToDetail("gintama");
    }

    // Add methods for your other cards:
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