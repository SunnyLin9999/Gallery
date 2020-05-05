package com.sunnylin9999.gallery.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnylin9999.gallery.GridAdapter;
import com.sunnylin9999.gallery.PhotoViewActivity;
import com.sunnylin9999.gallery.R;
import com.sunnylin9999.gallery.model.PhotoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;

    private Context context;

    private GridView gridView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        context = getContext();


        gridView = (GridView) root.findViewById(R.id.grid_home);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        showPhotos();
    }

    private void showPhotos() {
        final List<PhotoItem> photoItems = getAllPhotos();

        GridAdapter adapter = new GridAdapter(context, photoItems);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoItem item = photoItems.get(position);
                Toast.makeText(context, "Name: \"" + item.getPhotoName() + "\"" +
                        ", \n\nLocation: \"" + item.getPhotoPath() + "\"" , Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("imageUri", String.valueOf(Uri.fromFile(new File(item.getPhotoPath()))));
                Intent intent = new Intent(context, PhotoViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private List<PhotoItem> getAllPhotos() {
        List<PhotoItem> photoItems= new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED,
        };

        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()) {
            String photoId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            String photoName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
            String photoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            String photoDateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED));
            Log.d(TAG, photoId + ", " + photoName + ", " + photoPath + ", " + photoPath);

            PhotoItem photoItem = new PhotoItem(photoId, photoPath, photoName, photoDateAdded);
            photoItems.add(photoItem);
        }
        cursor.close();
        cursor = null;

        return photoItems;
    }

}
