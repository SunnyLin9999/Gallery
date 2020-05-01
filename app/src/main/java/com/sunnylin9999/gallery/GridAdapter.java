package com.sunnylin9999.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunnylin9999.gallery.model.PhotoItem;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private final String TAG = "GridAdapter";
    private Context context;
    private LayoutInflater layoutInflater;

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

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        grid = layoutInflater.inflate(R.layout.gridview_item, null);

        imageView = (ImageView) grid.findViewById(R.id.grid_image);
        textview = (TextView) grid.findViewById((R.id.grid_text));

        PhotoItem item = photoItems.get(position);

        Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(),
                Integer.valueOf(item.getPhotoId()),
                MediaStore.Images.Thumbnails.MICRO_KIND,
                null);
        imageView.setImageBitmap(bmp);
        textview.setText(item.getPhotoName());

        return grid;
    }
}
