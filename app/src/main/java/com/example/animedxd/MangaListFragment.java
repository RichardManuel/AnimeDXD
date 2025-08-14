package com.example.animedxd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MangaListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MangaAdapter adapter;

    private final List<MangaItem> mangaItems = new ArrayList<>();

    public MangaListFragment() {
        // Konstruktor kosong
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inisialisasi data manga
        mangaItems.add(new MangaItem(
                R.drawable.manga1kny, // Ganti dengan nama file gambar Anda
                "53.2m x • 4 years", // Tambahkan informasi ekstra di sini
                "Kimetsu no Yaiba",
                "Responsible brother Tanjiro had been living a peaceful life until he lost his family overnight."
        ));
        mangaItems.add(new MangaItem(
                R.drawable.manga2one, // Ganti dengan nama file gambar Anda
                "53.2m x • 2 weeks", // Tambahkan informasi ekstra di sini
                "One Piece",
                "The Pirate King confirmed the existence of the greatest treasure called One Piece."
        ));
        mangaItems.add(new MangaItem(
                R.drawable.manga3black, // Ganti dengan nama file gambar Anda
                "26.8m x • 2 weeks", // Tambahkan informasi ekstra di sini
                "Black Clover",
                "Two boys who were abandoned as babies have now become rival Magic Knights."
        ));
        mangaItems.add(new MangaItem(
                R.drawable.manga4opm, // Ganti dengan nama file gambar Anda
                "26.2m x • 1 month", // Tambahkan informasi ekstra di sini
                "One Punch Man",
                "Saitama can knock out anyone and anything with just one punch."
        ));
        mangaItems.add(new MangaItem(
                R.drawable.magna58, // Ganti dengan nama file gambar Anda
                "21.2m x • 2 months", // Tambahkan informasi ekstra di sini
                "8Kaijuu",
                "A man, who is unhappy with the work he has to do in life, gets involved in an unexpected event...!"
        ));
        mangaItems.add(new MangaItem(
                R.drawable.manga6dan, // Ganti dengan nama file gambar Anda
                "18m x • 1 day", // Tambahkan informasi ekstra di sini
                "DANDANDAN",
                "Ghosts, monsters, aliens, teenage romance, battles... and the kitchen sink!"
        ));
        mangaItems.add(new MangaItem(
                R.drawable.manga7hai, // Ganti dengan nama file gambar Anda
                "15.7m x • 5 years", // Tambahkan informasi ekstra di sini
                "Haikyuu",
                "Despite being short, Hinata never gave up playing volleyball."
        ));
        mangaItems.add(new MangaItem(
                R.drawable.manga8blu, // Ganti dengan nama file gambar Anda
                "14.3m x • 7 days", // Tambahkan informasi ekstra di sini
                "Blue Lock",
                "The story begins with the Japanese Football Association starting a program to start training for the 2022 World Cup."
        ));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_manga_list);
        adapter = new MangaAdapter(mangaItems);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public static class MangaItem {
        public int imageResId;
        public String extraInfo; // Variabel baru untuk teks tambahan
        public String title;
        public String description;

        public MangaItem(int imageResId, String extraInfo, String title, String description) {
            this.imageResId = imageResId;
            this.extraInfo = extraInfo;
            this.title = title;
            this.description = description;
        }
    }

    public static class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

        private final List<MangaItem> items;

        public MangaAdapter(List<MangaItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.manga_list_item, parent, false);
            return new MangaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
            MangaItem item = items.get(position);
            holder.imageView.setImageResource(item.imageResId);
            holder.extraInfoView.setText(item.extraInfo); // Set teks tambahan
            holder.titleView.setText(item.title);
            holder.descriptionView.setText(item.description);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public static class MangaViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView extraInfoView; // TextView baru
            TextView titleView;
            TextView descriptionView;

            public MangaViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView_manga_cover);
                extraInfoView = itemView.findViewById(R.id.textView_extra_info); // Inisialisasi TextView baru
                titleView = itemView.findViewById(R.id.textView_manga_title);
                descriptionView = itemView.findViewById(R.id.textView_manga_description);
            }
        }
    }
}
