package com.mysampleapp;

import java.io.Serializable;

/**
 * Created by mario_oliver93 on 12/4/16.
 */

public class Recipe implements Serializable {

    private String mTitle;
    private String[] mIngredients;

    public Recipe(String title, String[] ingredients){
        mTitle = title;
        mIngredients = ingredients;
    }

    public String getTitle() {
        return mTitle;
    }

    public String[] getIngredients() {
        return mIngredients;
    }
}
