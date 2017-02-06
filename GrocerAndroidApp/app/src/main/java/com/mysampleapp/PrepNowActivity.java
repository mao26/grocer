package com.mysampleapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mario_oliver93 on 12/21/16.
 */

public class PrepNowActivity extends AppCompatActivity {

    private static final String RECIPE = "recipe";
    private Recipe currRecipe;
    private String[] ingTitles;
    private Integer[] ingThumbnails;
    AnimationDrawable rocketAnimation;

    @Override
    protected void onPause() {
        super.onPause();
        rocketAnimation.stop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_now);
        ImageView image = (ImageView) findViewById(R.id.image_view_chef_cooking);
        image.setBackgroundResource(R.drawable.cook_now_chef);
        rocketAnimation = (AnimationDrawable) image.getBackground();
        rocketAnimation.start();

        currRecipe = (Recipe) getIntent().getSerializableExtra(RECIPE);
        ingTitles = currRecipe.getIngredients();
        ingThumbnails = currRecipe.getThumbnails();

        RecyclerView listview = (RecyclerView) findViewById(R.id.recipeview_listview);
        listview.setLayoutManager(new LinearLayoutManager(PrepNowActivity.this));
        listview.setAdapter(new RecipeLinearViewAdapter());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout cookNowButton = (LinearLayout) findViewById(R.id.cook_now_button_container);
        cookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrepNowActivity.this, CookNowActivity.class));
            }
        });
    }

    private class RecipeLinearViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title;

        //changed the View itemview out of the constructor because I'm inflating a view
        public RecipeLinearViewHolder(ViewGroup container) {
            super(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, container, false));

            tv_title = (TextView) itemView;
        }

        public void bindView(String title) {

            tv_title.setText(title);
        }
    }

    private class RecipeLinearViewAdapter extends RecyclerView.Adapter<RecipeLinearViewHolder>{

        @Override
        public RecipeLinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecipeLinearViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecipeLinearViewHolder holder, int position) {
            String title = ingTitles[position];
            holder.bindView(title);
        }

        @Override
        public int getItemCount() {
            return ingTitles.length;
        }
    }

}
