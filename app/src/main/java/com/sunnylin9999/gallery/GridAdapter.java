package com.sunnylin9999.gallery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private final String TAG = "GridAdapter";
    private Context context;
    private final int[] imageIdList;
    private final String[] imageTitleList;

    public GridAdapter(Context context, int[] imageId, String[] imageTitle) {
        this.context = context;
        this.imageIdList = imageId;
        this.imageTitleList = imageTitle;
    }

    @Override
    public int getCount() {
        return imageIdList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        ImageView imageView;
        TextView textview;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = layoutInflater.inflate(R.layout.gridview_item, null);
            imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textview = (TextView) grid.findViewById((R.id.grid_text));
            imageView.setImageResource(imageIdList[position]);
            textview.setText(imageTitleList[position]);
            Log.d(TAG, "GridAdapter: " + position + "," + imageTitleList[position]);
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}
