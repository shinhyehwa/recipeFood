package com.example.foodrecipe.Views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodrecipe.Adapter.RandomRecipeRycAdapter;
import com.example.foodrecipe.MainActivity;
import com.example.foodrecipe.Model.Recipe;
import com.example.foodrecipe.Model.RecipeRoot;
import com.example.foodrecipe.R;
import com.example.foodrecipe.RecipeListener.RandomRecipeListener;
import com.example.foodrecipe.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recycler_search_layout;

    ProgressDialog dialog;
    ProgressDialog dialogLoad;
    RequestManager manager;
    RandomRecipeRycAdapter randomRecipeRycAdapter;
    private List<Recipe> recipeList;
    private Activity mActivity;

    private List<String> tags = new ArrayList<>();
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView search_recipe_layout = view.findViewById(R.id.search_recipe_layout);
        recycler_search_layout = view.findViewById(R.id.recycler_search_layout);
        mActivity = (MainActivity) getActivity();
        manager = new RequestManager(mActivity);
        dialog = new ProgressDialog(mActivity);
        dialogLoad = new ProgressDialog(mActivity);

        search_recipe_layout.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.show();
                tags.clear();
                tags.add(query);
                manager.getRandomRecipe(randomRecipeListener, tags);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recycler_search_layout.addOnScrollListener(addRecipeToRyc);
        return view;
    }

    private final RandomRecipeListener randomRecipeListener = new RandomRecipeListener() {
        @Override
        public void didFetch(RecipeRoot response, String message) {
            recipeList = response.recipes;
            recycler_search_layout.setHasFixedSize(true);
            recycler_search_layout.setLayoutManager(new GridLayoutManager(mActivity, 1));
            randomRecipeRycAdapter = new RandomRecipeRycAdapter(mActivity, recipeList);
            recycler_search_layout.setAdapter(randomRecipeRycAdapter);
            dialog.dismiss();
        }
        @Override
        public void didError(String message) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };

    private final RandomRecipeListener addRandomRecipe = new RandomRecipeListener() {
        @Override
        public void didFetch(RecipeRoot response, String message) {
            recipeList.addAll(response.recipes);
            randomRecipeRycAdapter.notifyDataSetChanged();
            dialogLoad.dismiss();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
            dialogLoad.dismiss();
        }
    };

    private final RecyclerView.OnScrollListener addRecipeToRyc = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (lastVisibleItemPosition == itemCount - 1) {
                    dialogLoad.show();
                    manager = new RequestManager(mActivity);
                    manager.getRandomRecipe(addRandomRecipe, tags);
                }
            }
        }
    };
}