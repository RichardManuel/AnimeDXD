package com.example.animedxd;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private ViewPager2 viewPager;
    private Handler carouselHandler;
    private Runnable carouselRunnable;

    private final List<CarouselItem> carouselItems = new ArrayList<>();

    public NewsFragment() {
        // Konstruktor kosong
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carouselItems.add(new CarouselItem(
                R.drawable.news_bleach,
                "Bleach, Star Wars Release Collab Visual for Andor Finale",
                "Both series have the theme of rising up against oppressors"
        ));
        carouselItems.add(new CarouselItem(
                R.drawable.news_blu,
                "BLUE LOCK Anime Takes Its Immersive Exhibition to Eight Cities",
                "Check out preview photo from Tokyo venue"
        ));
        carouselItems.add(new CarouselItem(
                R.drawable.news_bokuno,
                "My Hero Academia Anime Gets 'Final Season' in 2025",
                "Show's 7th season ended on Saturday"
        ));
        carouselItems.add(new CarouselItem(
                R.drawable.news_dandan,
                "DAN DA DAN Season 2 Anime Reveals New Trailer, Opening Song Artist",
                "AiNA THE END performs the new OP for the season starting this July"
        ));
        carouselItems.add(new CarouselItem(
                R.drawable.news_kny,
                "Demon Slayer: The Hinokami Chronicles 2 Trailer Introduces Yoriichi Type Zero",
                "Another playable addition is on the way to the sequel this August"
        ));
        carouselItems.add(new CarouselItem(
                R.drawable.news_sakomoto,
                "Sakamoto Days Manga Shares Gintama Mash-Up Art",
                "Volume 22 of the Shonen Jump series goes on sale in Japan next month"
        ));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        viewPager = view.findViewById(R.id.viewPager_carousel);

        setupViewPager();
        setupAutoScroll();

        return view;
    }

    private void setupViewPager() {
        CarouselAdapter adapter = new CarouselAdapter(carouselItems);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);

        // Menempatkan ViewPager di posisi "tengah" untuk memulai loop tak terbatas
        int startPosition = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % carouselItems.size());
        viewPager.setCurrentItem(startPosition, false);

        viewPager.setPageTransformer((page, position) -> {
            float scaleFactor = 0.85f;
            page.setScaleY(scaleFactor + (1 - scaleFactor) * (1 - Math.abs(position)));
            page.setAlpha(1 - Math.abs(position));
        });

        // Menggunakan callback untuk mengelola auto-scroll saat pengguna berinteraksi
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    carouselHandler.removeCallbacks(carouselRunnable);
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    carouselHandler.postDelayed(carouselRunnable, 5000);
                }
            }
        });
    }

    private void setupAutoScroll() {
        carouselHandler = new Handler(Looper.getMainLooper());
        carouselRunnable = () -> {
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem + 1, true);
        };
        // Mulai auto-scroll
        carouselHandler.postDelayed(carouselRunnable, 5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (carouselHandler != null) {
            carouselHandler.removeCallbacks(carouselRunnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (carouselHandler != null) {
            carouselHandler.postDelayed(carouselRunnable, 5000);
        }
    }

    // =================================================s================================================
    // Kelas data untuk item carousel
    // =================================================================================================
    public static class CarouselItem {
        public int imageResId;
        public String title;
        public String description;

        public CarouselItem(int imageResId, String title, String description) {
            this.imageResId = imageResId;
            this.title = title;
            this.description = description;
        }
    }

    // =================================================================================================
    // Adapter untuk ViewPager2
    // =================================================================================================
    public static class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

        private final List<CarouselItem> items;

        public CarouselAdapter(List<CarouselItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.carousel_item, parent, false);
            return new CarouselViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
            // Menggunakan operator modulo untuk membuat loop tak terbatas
            int actualPosition = position % items.size();
            CarouselItem item = items.get(actualPosition);
            holder.imageView.setImageResource(item.imageResId);
            holder.titleView.setText(item.title);
            holder.descriptionView.setText(item.description);
        }

        @Override
        public int getItemCount() {
            // Mengembalikan nilai yang sangat besar untuk efek loop tak terbatas
            return Integer.MAX_VALUE;
        }

        public static class CarouselViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleView;
            TextView descriptionView;

            public CarouselViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView_carousel);
                titleView = itemView.findViewById(R.id.textView_item_title);
                descriptionView = itemView.findViewById(R.id.textView_item_description);
            }
        }
    }
}
