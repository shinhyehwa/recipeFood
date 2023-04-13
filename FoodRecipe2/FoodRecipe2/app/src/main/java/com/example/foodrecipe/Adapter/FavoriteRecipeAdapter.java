package com.example.foodrecipe.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipe.Model.Recipe;
import com.example.foodrecipe.Model.Recipe_Favorite;
import com.example.foodrecipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteRecipeAdapter extends BaseAdapter {

    private Activity mContext;
    private List<Recipe_Favorite> recipeList;
    private Detail_ClickListener_Favorite detail_clickListener_favorite;

    public FavoriteRecipeAdapter(Activity mContext, List<Recipe_Favorite> recipeList, Detail_ClickListener_Favorite detail_clickListener_favorite){
        this.mContext = mContext;
        this.recipeList = recipeList;
        this.detail_clickListener_favorite = detail_clickListener_favorite;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return recipeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view_assemble = view;
        Viewholder viewholder;
        if(view_assemble == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = mContext.getLayoutInflater();
            view_assemble = inflater.inflate(R.layout.custom_favorite, null, true);
            viewholder.cardView = view_assemble.findViewById(R.id.Favorite_CardView);
            viewholder.TextView_title= view_assemble.findViewById(R.id.Title_Favorite);
            viewholder.textfavorite = view_assemble.findViewById(R.id.text_favorite);
            viewholder.serving = view_assemble.findViewById(R.id.serving_favorite);
            viewholder.time_cooking = view_assemble.findViewById(R.id.time_cooking_favorite);
            viewholder.Image_food = view_assemble.findViewById(R.id.Image_favorite);
            view_assemble.setTag(viewholder);
        }else{
            viewholder = (Viewholder) view_assemble.getTag();
        }
        Recipe_Favorite dataModel = recipeList.get(i);
        viewholder.TextView_title.setText(dataModel.getName());
        viewholder.TextView_title.setSelected(true);
        viewholder.textfavorite.setText(dataModel.getLikes() + " Likes");
        viewholder.time_cooking.setText(dataModel.getTime() + " Min");
        viewholder.serving.setText(dataModel.getServing() + " Servings");
        Picasso.get().load(dataModel.getImage()).into(viewholder.Image_food);
        viewholder.Image_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_clickListener_favorite.OnClickRecipe(dataModel);
            }
        });

        return view_assemble;
    }
    public static class Viewholder{
        CardView cardView;
        TextView TextView_title, textfavorite, time_cooking, serving;
        ImageView Image_food;
    }

    public interface  Detail_ClickListener_Favorite{
        void OnClickRecipe(Recipe_Favorite recipeFavorite);
    }


}