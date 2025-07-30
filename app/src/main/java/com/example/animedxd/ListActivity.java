package com.example.animedxd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.animedxd.R;
import com.example.animedxd.DetailActivity;
import com.example.animedxd.AboutActivity;
import com.example.animedxd.MainActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListActivity extends AppCompatActivity {

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
