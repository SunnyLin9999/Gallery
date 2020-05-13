package com.sunnylin9999.gallery.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunnylin9999.gallery.R;
import com.sunnylin9999.gallery.model.AlbumInfo;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    private final String TAG = "GalleryAdapter";

    private Context context;

    private LayoutInflater layoutInflater;

    private List<AlbumInfo> albumInfoList;

    public GalleryAdapter(Context context, List<AlbumInfo> albumInfos) {
        this.context = context;
        this.albumInfoList = albumInfos;
    }

    @Override
    public int getCount() {
        return albumInfoList == null ? null : albumInfoList.size();

    }

    @Override
    public Object getItem(int position) {
        return albumInfoList == null ? null : albumInfoList.get(position);
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

        if(convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            grid = layoutInflater.inflate(R.layout.gridview_item, null);

        } else {
            grid = (View) convertView;
        }

        imageView = (ImageView) grid.findViewById(R.id.grid_image);
        textview = (TextView) grid.findViewById((R.id.grid_text));

        AlbumInfo info = albumInfoList.get(position);

        Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(),
                Integer.valueOf(info.getPhotoInfoList().get(0).getId()),
                MediaStore.Images.Thumbnails.MICRO_KIND,
                null);
        imageView.setImageBitmap(bmp);

        textview.setTextSize(20);
        textview.setText(info.getAlbumName().split("/")[info.getAlbumName().split("/").length-1]);

        return grid;
    }
}
