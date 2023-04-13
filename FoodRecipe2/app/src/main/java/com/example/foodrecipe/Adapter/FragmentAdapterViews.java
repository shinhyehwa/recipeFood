package com.example.foodrecipe.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.foodrecipe.Views.HomeFragment;
import com.example.foodrecipe.Views.RecipeFragment;
import com.example.foodrecipe.Views.SearchFragment;

public class FragmentAdapterViews extends FragmentStatePagerAdapter {


    public FragmentAdapterViews(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new RecipeFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
