package com.example.stunnningbake.stunningbake.rest;

import com.example.stunnningbake.stunningbake.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by PC-Lenovo on 10/09/2017.
 */

public interface ApiInterface {
    @GET("baking.json")
    Call<List<Recipe>> getRecipe();
}
