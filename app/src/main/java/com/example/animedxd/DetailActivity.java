package com.example.animedxd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.HashMap;
import java.util.Map;


public class DetailActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageView animeCoverImage;
    private TextView animeTitle, animeGenre, animeSynopsis;
    private Button postReviewButton;

    // Kelas untuk menampung data anime
    private static class AnimeData {
        String title;
        String genre;
        String synopsis;
        double rating;
        int imageResId;

        AnimeData(String title, String genre, String synopsis, double rating, int imageResId) {
            this.title = title;
            this.genre = genre;
            this.synopsis = synopsis;
            this.rating = rating;
            this.imageResId = imageResId;
        }
    }

    // "Database" data anime
    private final Map<String, AnimeData> animeDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inisialisasi "database" anime
        initializeAnimeDatabase();

        // Inisialisasi komponen UI
        backButton = findViewById(R.id.backButton);
        animeCoverImage = findViewById(R.id.animeCoverImage);
        animeTitle = findViewById(R.id.animeTitle);
        animeGenre = findViewById(R.id.animeGenre);
        animeSynopsis = findViewById(R.id.animeSynopsis);
        postReviewButton = findViewById(R.id.postReviewButton);

        // Menangani klik tombol kembali
        backButton.setOnClickListener(v -> finish());

        // Menerima ID item dari ListActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ITEM_ID")) {
            String itemId = intent.getStringExtra("ITEM_ID");
            loadAnimeData(itemId);
        }

        // Menangani klik tombol Post a Review
        postReviewButton.setOnClickListener(v -> showReviewDialog());
    }

    private void initializeAnimeDatabase() {
        // Data untuk Attack on Titan (aot)
        animeDatabase.put("aot", new AnimeData(
                "Attack on Titan",
                "Action, Dark Fantasy",
                "A post-apocalyptic anime series set in a world where humanity lives behind three massive walls to protect themselves from man-eating Titans.",
                4.9,
                R.drawable.aot // Ganti dengan nama drawable yang benar
        ));

        // Data untuk Hunter x Hunter (hxh)
        animeDatabase.put("hxh", new AnimeData(
                "Hunter x Hunter",
                "Adventure, Fantasy",
                "Gon Freecss aspires to become a Hunter, an exceptional being capable of greatness. With his friends and his potential, he seeks out his father, who left him when he was younger.",
                4.7,
                R.drawable.hxh // Ganti dengan nama drawable yang benar
        ));

        // Data untuk Gintama
        animeDatabase.put("gintama", new AnimeData(
                "Gintama",
                "Sci-fi, Comedy, Samurai",
                "Gintoki Sakata, an eccentric samurai, and his odd-jobs business Yorozuya (万事屋) in a world where aliens have conquered Edo-period Japan.",
                4.8,
                R.drawable.gintama // Ganti dengan nama drawable yang benar
        ));

        // Tambahkan data untuk anime lainnya sesuai dengan ListActivity Anda
        animeDatabase.put("love_is_war", new AnimeData(
                "Kaguya-sama: Love Is War",
                "Romance, Comedy",
                "Two brilliant and wealthy member of student council who both harbor feelings for each other but are too proud to confess.",
                4.6,
                R.drawable.love_is_war // Ganti dengan nama drawable yang benar
        ));

        animeDatabase.put("the_witch_from_mercury", new AnimeData(
                "Gundam: The Witch from Mercury",
                "Mecha, Sci-fi",
                "A compassionate girl enrolls herself in the Asticassia School of Technology in Planet Mercury that is run by the Beneritt Group that creates robotic mobile suits.",
                4.9,
                R.drawable.the_witch_from_mercury // Ganti dengan nama drawable yang benar
        ));

        animeDatabase.put("the_misfit_of_demon_king", new AnimeData(
                "The Misfit of Demon King Academy",
                "Action, Fantasy",
                "Anos Voldigoad, the Demon King of Tyranny, who, after defeating countless enemies and tiring of war, reincarnates to find peace two millennia later.",
                4.8,
                R.drawable.demon_king_academy // Ganti dengan nama drawable yang benar
        ));

        animeDatabase.put("overlord", new AnimeData(
                "Overlord",
                "Fantasy, Isekai",
                "Having full conscious in a shutdowned VR game, Ainz Ooal Gown explores the world to control it with his NPC companions.",
                4.7,
                R.drawable.overlord // Ganti dengan nama drawable yang benar
        ));

        // Pastikan Anda juga membuat drawable untuk semua gambar cover di res/drawable
    }

    private void loadAnimeData(String itemId) {
        AnimeData data = animeDatabase.get(itemId);
        if (data != null) {
            animeTitle.setText(data.title);
            animeGenre.setText(data.genre);
            animeSynopsis.setText(data.synopsis);
            animeCoverImage.setImageResource(data.imageResId);
            TextView animeRating = findViewById(R.id.animeRating);
            animeRating.setText(String.valueOf(data.rating));
        }
    }

    private void showReviewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_post_review, null);
        builder.setView(dialogView);

        final EditText reviewInput = dialogView.findViewById(R.id.reviewInput);
        final Button postButton = dialogView.findViewById(R.id.postButton);
        final ImageButton closeButton = dialogView.findViewById(R.id.closeButton);
        final View colorOlive = dialogView.findViewById(R.id.colorOlive);
        final View colorCoral = dialogView.findViewById(R.id.colorCoral);
        final View colorCream = dialogView.findViewById(R.id.colorCream);
        final LinearLayout dialogBackground = dialogView.findViewById(R.id.dialogBackground);

        final AlertDialog dialog = builder.create();

        // ✅ Bikin background dialog transparan & sudut ikut rounded
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        closeButton.setOnClickListener(v -> dialog.dismiss());

        colorOlive.setOnClickListener(v ->
                dialogBackground.setBackgroundResource(R.drawable.gradient_olive)
        );

        colorCoral.setOnClickListener(v ->
                dialogBackground.setBackgroundResource(R.drawable.gradient_coral)
        );

        colorCream.setOnClickListener(v ->
                dialogBackground.setBackgroundResource(R.drawable.gradient_cream)
        );

        postButton.setOnClickListener(v -> {
            String reviewText = reviewInput.getText().toString().trim();
            if (reviewText.isEmpty()) {
                reviewInput.setError("Review cannot be empty!");
                Toast.makeText(this, "Review must be filled in.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Review posted successfully!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}