package com.example.animedxd;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {

    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new NewsFragment();
        } else {
            return new MangaListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Karena kita punya 2 tab
    }
}