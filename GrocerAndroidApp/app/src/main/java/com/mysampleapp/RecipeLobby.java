package com.mysampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mario_oliver93 on 12/4/16.
 */

public class RecipeLobby extends AppCompatActivity {

    private RecipesAdapter mRecipesAdapter;
    private static final String RECIPE = "recipe";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_lobby);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecipeLab recipeLab = RecipeLab.get(this);
        List<Recipe> recipes = recipeLab.getRecipes();
        mRecipesAdapter = new RecipesAdapter(recipes);
        recyclerView.setAdapter(mRecipesAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class RecipesHolder extends RecyclerView.ViewHolder{

        public TextView mTitleTextView;
        private Recipe currRecipe;

        public RecipesHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.recipeview_title_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(RecipeLobby.this, RecipeActivity.class);
                    /**
                     * RecipeHolder constructor gets called first to return a view - happens in OnCreateViewHolder;
                     * then Adapter calls onBindViewHolder that gives us the current recipe. However, we could never
                     * have a view onscreen until it is created and bound so this dependency is fine
                     *
                     */
                    i.putExtra(RECIPE, currRecipe);
                    startActivity(i);
                }
            });
        }

        public void bindView(Recipe currRecipe){
            this.currRecipe = currRecipe;
            mTitleTextView.setText(currRecipe.getTitle());
        }
    }

    private class RecipesAdapter extends RecyclerView.Adapter<RecipesHolder>{

        private List<Recipe> mRecipeList;

        public RecipesAdapter(List<Recipe> recipes){
            mRecipeList = recipes;
        }

        @Override
        public RecipesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(RecipeLobby.this);
            View view = layoutInflater.inflate(R.layout.activity_recipe_lobby_item, parent, false);
            return new RecipesHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipesHolder holder, int position) {
            Recipe recipe = mRecipeList.get(position);
            holder.bindView(recipe);
        }

        @Override
        public int getItemCount() {
            return mRecipeList.size();
        }
    }

}
