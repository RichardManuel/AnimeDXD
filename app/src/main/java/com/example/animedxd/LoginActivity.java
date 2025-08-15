package com.example.animedxd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView errorPopupTextView;
    private LinearLayout errorPopupLayout;

    // Nama file SharedPreferences untuk menyimpan data
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME_KEY = "usernameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        errorPopupTextView = findViewById(R.id.errorPopupTextView);
        errorPopupLayout = findViewById(R.id.errorPopupLayout);

        // Hapus SharedPreferences saat aplikasi dibuka
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USERNAME_KEY);
        editor.apply();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColorOnClick();
                validateInputs();
            }
        });
    }

    private void showErrorPopup(String message) {
        errorPopupTextView.setText(message);
        errorPopupLayout.setVisibility(View.VISIBLE);
        errorPopupLayout.setTranslationY(-errorPopupLayout.getHeight());
        errorPopupLayout.animate().translationY(0).setDuration(300).start();

        new Handler().postDelayed(() -> {
            errorPopupLayout.animate().translationY(-errorPopupLayout.getHeight()).setDuration(300).withEndAction(() -> {
                errorPopupLayout.setVisibility(View.GONE);
            }).start();
        }, 2000);
    }

    private void changeButtonColorOnClick() {
        loginButton.getBackground().setColorFilter(Color.parseColor("#E55B50"), PorterDuff.Mode.SRC_ATOP);
        new Handler().postDelayed(() -> loginButton.getBackground().clearColorFilter(), 200);
    }

    private void validateInputs() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!validateUsername(username) || !validatePassword(password)) {
            return;
        }

        loginButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6B7829")));

        new Handler().postDelayed(() -> {
            // Simpan username ke SharedPreferences setelah login berhasil
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USERNAME_KEY, username);
            editor.apply();

            redirectToHomePage(username);
        }, 500);
    }

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            showErrorPopup("Username must be filled in!");
            return false;
        }

        if (username.length() < 5 || username.length() > 10) {
            showErrorPopup("Username must be 5-10 characters!");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            showErrorPopup("Password must be filled in!");
            return false;
        }
        return true;
    }

    private void redirectToHomePage(String username) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }
}
