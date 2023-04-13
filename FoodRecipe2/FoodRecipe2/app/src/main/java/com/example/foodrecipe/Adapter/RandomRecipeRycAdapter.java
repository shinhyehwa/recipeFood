package com.example.foodrecipe.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.Model.ExtendedIngredient;
import com.example.foodrecipe.Model.Recipe;
import com.example.foodrecipe.Model.RecipeRoot;
import com.example.foodrecipe.Model.Step;
import com.example.foodrecipe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RandomRecipeRycAdapter extends RecyclerView.Adapter<ViewHolder>{
   private Activity mContext;
    private List<Recipe> recipeList;

    private Detail_ClickListener DetailClickListener;


    public interface  Detail_ClickListener{
        void OnClickRecipe(Recipe recipe);
    }
    public RandomRecipeRycAdapter(Activity activity,List<Recipe> recipeList, Detail_ClickListener detail_clickListener) {
        this.mContext = activity;
        this.recipeList = recipeList;
        this.DetailClickListener = detail_clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_custom_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Nap du lieu

        Recipe dataModel = recipeList.get(position);
        holder.Ryc_TextView_title.setText(dataModel.title);
        holder.Ryc_TextView_title.setSelected(true);
        holder.Ryc_textfavorite.setText(dataModel.aggregateLikes + " Likes");
        holder.time_cooking.setText(dataModel.readyInMinutes + " Min");
        holder.Ryc_serving.setText(dataModel.servings + " Servings");
        Picasso.get().load(dataModel.image).into(holder.Ryc_Image_food);
        holder.Ryc_Image_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailClickListener.OnClickRecipe(dataModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{
    CardView Ryc_CardView;
    TextView Ryc_TextView_title, Ryc_textfavorite, time_cooking, Ryc_serving;
    ImageView Ryc_Image_food;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        Ryc_CardView = itemView.findViewById(R.id.Ryc_CardView);
        Ryc_TextView_title = itemView.findViewById(R.id.Ryc_TextView_title);
        Ryc_textfavorite = itemView.findViewById(R.id.Ryc_textfavorite);
        Ryc_Image_food = itemView.findViewById(R.id.Ryc_Image_food);
        time_cooking = itemView.findViewById(R.id.time_cooking);
        Ryc_serving = itemView.findViewById(R.id.Ryc_serving);
    }
}
