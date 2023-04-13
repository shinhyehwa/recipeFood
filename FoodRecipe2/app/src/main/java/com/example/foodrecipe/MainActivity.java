package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.foodrecipe.Adapter.FragmentAdapterViews;
import com.example.foodrecipe.Adapter.RandomRecipeRycAdapter;
import com.example.foodrecipe.Model.RecipeRoot;
import com.example.foodrecipe.RecipeListener.RandomRecipeListener;
import com.example.foodrecipe.Views.HomeFragment;
import com.example.foodrecipe.Views.RecipeFragment;
import com.example.foodrecipe.Views.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ViewPager recyclerView_Random;
    FragmentAdapterViews fragmentAdapterViews;

    BottomNavigationView bottombar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();


    }

    private void Init(){
        bottombar = findViewById(R.id.bottombar);
        recyclerView_Random = findViewById(R.id.recyclerView_Random);
        fragmentAdapterViews = new FragmentAdapterViews(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        recyclerView_Random.setAdapter(fragmentAdapterViews);

        recyclerView_Random.setOffscreenPageLimit(2);

        setupViewPage();

    }

    private void setupViewPage(){
        recyclerView_Random.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottombar.getMenu().findItem(R.id.home_recipe).setChecked(true);
                        break;
                    case 1:
                        bottombar.getMenu().findItem(R.id.search_recipe).setChecked(true);
                        break;
                    case 2:
                        bottombar.getMenu().findItem(R.id.recipe).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottombar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_recipe:
                        recyclerView_Random.setCurrentItem(0);
                        return true;
                    case R.id.search_recipe:
                        recyclerView_Random.setCurrentItem(1);
                        return true;
                    case R.id.recipe:
                        recyclerView_Random.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
    }
}