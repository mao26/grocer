package com.mysampleapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mario_oliver93 on 12/4/16.
 */

public class RecipeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(RecipeActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
        RecyclerView.ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_item, null);
            holder = new ViewHolder();
            holder.iv = (ImageButton) ctView.findViewById(R.id.menuItem_txt);
            convertView.setTag(holder);
        } else {
            holder = (RecyclerView.ViewHolder) convertView.getTag();
        }


        holder.b.setBackgroundColor(back_color[position]);
        holder.tv.setText(menuValues[position]);
        holder.tv.setTextColor(txt_color[position]);
        holder.tv.setTypeface(font);

        holder.iv.setScaleType(ImageButton.ScaleType.CENTER_CROP);
        holder.iv.setFocusable(false);
        holder.iv.setClickable(false);
        holder.iv.setAdjustViewBounds(true);
        holder.iv.setColorFilter(img_color[position]);
        holder.iv.setImageResource(img[position]);


        return convertView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.mipmap.egg, R.mipmap.butter,
            R.mipmap.milk, R.mipmap.sugar,
            R.mipmap.olive_oil, R.mipmap.flour,
            R.mipmap.salt, R.mipmap.water,
    };
}
