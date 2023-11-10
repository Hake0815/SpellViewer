package com.example.spellviewer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ImageViewPagerAdapter extends FragmentStateAdapter {
    public ImageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return ImageFragment.newInstance("female");
            case 2: return ImageFragment.newInstance("other");
            default: return ImageFragment.newInstance("male");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
