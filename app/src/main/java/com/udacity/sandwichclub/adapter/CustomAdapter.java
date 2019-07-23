package com.udacity.sandwichclub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class CustomAdapter extends BaseAdapter {
    private String[] mSandwichList;
    private Context mContext;

    public CustomAdapter(Context context, String[] sandwichList) {
        mContext = context;
        mSandwichList = sandwichList;
    }

    @Override
    public int getCount() {
        return mSandwichList.length;
    }

    @Override
    public Object getItem(int position) {
        return mSandwichList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(mSandwichList[position]);
        } catch(JSONException e) {
            e.printStackTrace();
            return convertView;
        }
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.list_item, parent, false);

        ImageView sandwichImage = convertView.findViewById(R.id.item_iv);
        TextView sandwichName = convertView.findViewById(R.id.item_tv);

        sandwichName.setText(sandwich.getMainName());
        Picasso.with(mContext)
                .load(sandwich.getImage())
                .into(sandwichImage);

        return convertView;
    }
}
