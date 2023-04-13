package com.example.foodrecipe;

import android.content.Context;

import com.example.foodrecipe.Model.RecipeRoot;
import com.example.foodrecipe.RecipeListener.RandomRecipeListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    private final String KEY_ID = "18bfbe5eaf96452f9d083a6c6bb8c0c0";
    public final String number = "10";
    private Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipe(RandomRecipeListener listener, List<String> tags){
        CallRandomRecipe callRandomRecipe = retrofit.create(CallRandomRecipe.class);
        Call<RecipeRoot> call = callRandomRecipe.callRandomRecipe(KEY_ID, number, tags);
        call.enqueue(new Callback<RecipeRoot>() {
            @Override
            public void onResponse(Call<RecipeRoot> call, Response<RecipeRoot> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeRoot> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipe{
        @GET("recipes/random")
        Call<RecipeRoot> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }
}
