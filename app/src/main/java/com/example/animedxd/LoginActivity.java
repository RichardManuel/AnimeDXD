package com.example.animedxd;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    // Deklarasi komponen UI
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView usernameErrorTextView;
    private TextView passwordErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi komponen UI dari layout XML
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        usernameErrorTextView = findViewById(R.id.usernameErrorTextView);
        passwordErrorTextView = findViewById(R.id.passwordErrorTextView);

        // Menambahkan listener untuk tombol login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Memberikan efek visual saat tombol diklik (Syarat: Change background color)
                changeButtonColorOnClick();
                // Memvalidasi input pengguna (Syarat: Validate fields)
                validateInputs();
            }
        });
    }

    /**
     * Fungsi untuk mengubah warna tombol saat diklik dan mengembalikannya
     * ke warna semula setelah beberapa saat.
     */
    private void changeButtonColorOnClick() {
        // Mengubah warna tombol menjadi lebih gelap saat ditekan
        loginButton.getBackground().setColorFilter(Color.parseColor("#E55B50"), PorterDuff.Mode.SRC_ATOP);

        // Menggunakan Handler untuk mengembalikan warna tombol ke semula setelah 200ms
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginButton.getBackground().clearColorFilter();
            }
        }, 200);
    }

    /**
     * Fungsi utama untuk memvalidasi input username dan password.
     */
    private void validateInputs() {
        // Mengambil teks dari EditText
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Mengatur ulang pesan error setiap kali tombol diklik
        usernameErrorTextView.setVisibility(View.GONE);
        passwordErrorTextView.setVisibility(View.GONE);

        // Memvalidasi setiap field
        boolean isUsernameValid = validateUsername(username);
        boolean isPasswordValid = validatePassword(password);

        // Jika semua validasi berhasil (Syarat: If validation is success)
        if (isUsernameValid && isPasswordValid) {
            // Pindah ke halaman utama (MainActivity)
            redirectToHomePage(username);
        }
    }

    /**
     * Memvalidasi field username.
     * @param username Username yang diinput.
     * @return true jika valid, false jika tidak.
     */
    private boolean validateUsername(String username) {
        // Syarat: Username must be filled in
        if (username.isEmpty()) {
            usernameErrorTextView.setText("Username must be filled in.");
            usernameErrorTextView.setVisibility(View.VISIBLE);
            return false;
        }

        // Syarat: Length of username must be 5 - 10 characters
        if (username.length() < 5 || username.length() > 10) {
            usernameErrorTextView.setText("Length of username must be 5 - 10 characters.");
            usernameErrorTextView.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    /**
     * Memvalidasi field password.
     * @param password Password yang diinput.
     * @return true jika valid, false jika tidak.
     */
    private boolean validatePassword(String password) {
        // Syarat: Password must be filled in
        if (password.isEmpty()) {
            passwordErrorTextView.setText("Password must be filled in.");
            passwordErrorTextView.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    /**
     * Berpindah ke halaman beranda (MainActivity) dan mengirimkan data username.
     * @param username Username yang akan dikirim ke halaman selanjutnya.
     */
    private void redirectToHomePage(String username) {
        // Syarat: Redirect to the home page
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        // Syarat: Store the username to the global variable
        intent.putExtra("USERNAME", username);

        // Memulai activity baru
        startActivity(intent);

        // Menutup LoginActivity agar pengguna tidak bisa kembali dengan tombol back
        finish();
    }
}
