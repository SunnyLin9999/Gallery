package com.sunnylin9999.gallery.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunnylin9999.gallery.R;
import com.sunnylin9999.gallery.model.PhotoInfo;

import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private final String TAG = "GridAdapter";

    private Context context;

    private LayoutInflater layoutInflater;

    private List<PhotoInfo> photoInfos;

    public HomeAdapter(Context context, List<PhotoInfo> photoInfoList) {
        this.context = context;
        this.photoInfos = photoInfoList;
    }

    @Override
    public int getCount() {
        return photoInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return photoInfos.get(position);
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

        PhotoInfo info = photoInfos.get(position);

        Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(),
                Integer.valueOf(info.getId()),
                MediaStore.Images.Thumbnails.MICRO_KIND,
                null);
        imageView.setImageBitmap(bmp);
        textview.setText(info.getFilename());

        return grid;
    }
}
