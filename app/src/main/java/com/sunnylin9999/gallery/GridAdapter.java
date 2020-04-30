package com.sunnylin9999.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunnylin9999.gallery.model.PhotoItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private final String TAG = "GridAdapter";
    private Context context;
    private List<PhotoItem> photoItems;

    public GridAdapter(Context context, List<PhotoItem> photoItems) {
        this.context = context;
        this.photoItems = photoItems;
    }

    @Override
    public int getCount() {
        return photoItems.size();
    }

    @Override
    public PhotoItem getItem(int position) {
        return photoItems.get(position);
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

            try {
                Uri path = Uri.fromFile(new File(photoItems.get(position).getPhotoPath()));
                Bitmap bmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(path));
                imageView.setImageBitmap(bmp);
                textview.setText(photoItems.get(position).getPhotoName());

//                Log.d(TAG, "GridAdapter: " + position + ", " + photoItems.get(position).getPhotoName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}
