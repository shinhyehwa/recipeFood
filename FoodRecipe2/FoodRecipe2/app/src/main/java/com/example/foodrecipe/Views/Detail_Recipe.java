package com.example.foodrecipe.Views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrecipe.Model.Database_Helper;
import com.example.foodrecipe.Model.Recipe;
import com.example.foodrecipe.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Detail_Recipe extends Fragment {
    public static final String TAG = Detail_Recipe.class.getName();
    Activity mactivity;
    Recipe recipe;
    //Khai báo các view
    ImageButton button;

    ImageView button_Download;
    ImageView img_recipe;
    TextView title;
    TextView time_cooking;
    TextView like;
    TextView serving;
    TextView ingredient;
    TextView instructions;


    //Khai báo listString để lưu danh sách nguyên liệu
    ArrayList<String> list_ingredient = new ArrayList<>();
    String result = "";
    String result_2 = "";
    //Khai báo layout
    private ScrollView layout_contraint;

    //Khai báo Database để lưu trữ dữ liệu
    Database_Helper database_helper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_detail__recipe, container, false);
        //Database.
        database_helper = new Database_Helper(this.getContext());
        //Ánh xạ
        mactivity = getActivity();
        layout_contraint = views.findViewById(R.id.layout_constraint);
        img_recipe = views.findViewById(R.id.image_recipe);
        title = views.findViewById(R.id.title_name);
        time_cooking = views.findViewById(R.id.time_cooking);
        like = views.findViewById(R.id.textfavorite);
        serving = views.findViewById(R.id.serving);
        title = views.findViewById(R.id.title_name);
        ingredient = views.findViewById(R.id.recipe_ingredients);
        instructions = views.findViewById(R.id.recipe_instruction);
        button = (ImageButton) views.findViewById(R.id.recipe_back);
        button_Download = (ImageButton) views.findViewById(R.id.download_recipe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }

            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = (Recipe) bundle.get("recipe");
            if (recipe != null) {
                Picasso.get().load(recipe.image).into(img_recipe);;
                title.setText(recipe.title);
                time_cooking.setText(String.valueOf(recipe.readyInMinutes));
                like.setText(String.valueOf(recipe.aggregateLikes));
                serving.setText(String.valueOf(recipe.servings));
                for (int i = 0; i < recipe.extendedIngredients.size(); i++) {
                    list_ingredient.add(recipe.extendedIngredients.get(i).amount + " " + recipe.extendedIngredients.get(i).unit + " " + recipe.extendedIngredients.get(i).name + "\n");
                }
                for (int i = 0; i < list_ingredient.size(); i++) {
                    result += list_ingredient.get(i);
                }
                ingredient.setText(result);
                for(int i = 0; i < recipe.analyzedInstructions.size(); i ++){
                    for(int j = 0; j< recipe.analyzedInstructions.get(i).steps.size(); j++){
                        result_2 += recipe.analyzedInstructions.get(i).steps.get(j).step;
                    }
                }
                instructions.setText(result_2);
            }
        }
        layout_contraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(database_helper.checkName(recipe.title)){
                    Toast toast = new Toast(mactivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mactivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("This Recipe has already exits");
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0,25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    database_helper.insertData(recipe.title, recipe.image, recipe.readyInMinutes, recipe.aggregateLikes, recipe.servings,
                            result, result_2);
                    Toast toast = new Toast(mactivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mactivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("Add to Favorite Successfully");
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0,25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return views;
    }
}