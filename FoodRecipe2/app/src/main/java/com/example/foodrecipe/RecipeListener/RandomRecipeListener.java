package com.example.foodrecipe.RecipeListener;

import com.example.foodrecipe.Model.RecipeRoot;

public interface RandomRecipeListener {
    void didFetch(RecipeRoot response, String message);
    void didError(String message);
}
