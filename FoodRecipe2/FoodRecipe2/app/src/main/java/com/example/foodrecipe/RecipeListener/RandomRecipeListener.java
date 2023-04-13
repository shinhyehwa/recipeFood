package com.example.foodrecipe.RecipeListener;

import com.example.foodrecipe.Model.RecipeRoot;

public interface RandomRecipeListener {
    void didFetch(RecipeRoot listRecipe, String message);
    void didError(String message);
}
