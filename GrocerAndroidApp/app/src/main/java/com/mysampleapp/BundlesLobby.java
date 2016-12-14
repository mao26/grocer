package com.mysampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mario_oliver93 on 12/9/16.
 */

public class BundlesLobby extends AppCompatActivity {

    private BundlesAdapter mBundlesAdapter;
    private static final String BUNDLE = "bundle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_lobby);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BundlesLab bundlesController = new BundlesLab(getApplicationContext());
        List<OneBundle> bundlesList = bundlesController.getBundles();
        mBundlesAdapter = new BundlesAdapter(bundlesList);
        recyclerView.setAdapter(mBundlesAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class BundlesHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;
        private List<Recipe> currRecipeList;
        private OneBundle currBundle;

        public BundlesHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.recipeview_title_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(BundlesLobby.this, RecipeLobby.class);
                    /**
                     * RecipeHolder constructor gets called first to return a view - happens in OnCreateViewHolder;
                     * then Adapter calls onBindViewHolder that gives us the current recipe. However, we could never
                     * have a view onscreen until it is created and bound so this dependency is fine
                     *
                     */
                    i.putExtra(BUNDLE, currBundle);
                    startActivity(i);
                }
            });
        }

        public void bindView(OneBundle bundle) {
            currBundle = bundle;
            this.currRecipeList = bundle.getRecipes();
            mTitleTextView.setText(bundle.getTitle());
        }
    }

    private class BundlesAdapter extends RecyclerView.Adapter<BundlesHolder> {

        private List<OneBundle> mBundlesList;

        public BundlesAdapter(List<OneBundle> bundles) {
            mBundlesList = bundles;
        }

        @Override
        public BundlesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(BundlesLobby.this);
            View view = layoutInflater.inflate(R.layout.activity_recipe_lobby_item, parent, false);
            return new BundlesHolder(view);
        }

        @Override
        public void onBindViewHolder(BundlesHolder holder, int position) {
            OneBundle bundle = mBundlesList.get(position);
            holder.bindView(bundle);
        }

        @Override
        public int getItemCount() {
            return mBundlesList.size();
        }
    }
}