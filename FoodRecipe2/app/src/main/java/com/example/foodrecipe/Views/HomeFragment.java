package com.example.foodrecipe.Views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ProgressDialog dialog;
    ProgressDialog dialogload;
    RequestManager manager;
    RandomRecipeRycAdapter randomRecipeRycAdapter;
    RecyclerView recyclerView;
    private List<Recipe> recipeList;
    private Activity mActivity;

    private Spinner spinner;
    private List<String> tags = new ArrayList<>();



    public HomeFragment() {
        // Required empty public constructor
    }
    private final RandomRecipeListener randomRecipeListener = new RandomRecipeListener() {
        @Override
        public void didFetch(RecipeRoot response, String message) {
            recipeList = response.recipes;
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 1));
            randomRecipeRycAdapter = new RandomRecipeRycAdapter(mActivity, recipeList);
            recyclerView.setAdapter(randomRecipeRycAdapter);

            dialog.dismiss();
        }
        @Override
        public void didError(String message) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = (MainActivity) getActivity();
        View views = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = views.findViewById(R.id.recyclerView);
        spinner = views.findViewById(R.id.spiner_tag);
        ArrayAdapter spinneradapter = ArrayAdapter.createFromResource(mActivity, R.array.tags, R.layout.spinner_text);
        spinneradapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(spinneradapter);


        dialog = new ProgressDialog(mActivity);
        dialog.setTitle("Loading.......");

        spinner.setOnItemSelectedListener(spinnerSelectedItem);
        recyclerView.addOnScrollListener(addRecipeToRyc);





        return views;
    }

    private final AdapterView.OnItemSelectedListener spinnerSelectedItem = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tags.clear();
            tags.add(parent.getSelectedItem().toString());
            manager = new RequestManager(mActivity);
            manager.getRandomRecipe(randomRecipeListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final RandomRecipeListener addRandomRecipe = new RandomRecipeListener() {
        @Override
        public void didFetch(RecipeRoot response, String message) {
            recipeList.addAll(response.recipes);
            randomRecipeRycAdapter.notifyDataSetChanged();
            dialogload.dismiss();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
            dialogload.dismiss();
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
                    dialogload = new ProgressDialog(mActivity);
                    dialogload.show();
                    manager = new RequestManager(mActivity);
                    manager.getRandomRecipe(addRandomRecipe, tags);
                }
            }
        }
    };


}