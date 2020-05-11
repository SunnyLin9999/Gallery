package com.sunnylin9999.gallery.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        gridView = root.findViewById(R.id.grid_home);

        context = getActivity();

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        Observer<String> textObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        };
        homeViewModel.getText().observe(getViewLifecycleOwner(), textObserver);

        Observer<List<PhotoItem>> loadPhotosObserver = new Observer<List<PhotoItem>>() {
            @Override
            public void onChanged(@Nullable final List<PhotoItem> photoItems) {
                GridAdapter adapter = new GridAdapter(context, photoItems);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PhotoItem item = photoItems.get(position);
                        Toast.makeText(context, "Name: \"" + item.getPhotoName() + "\"" +
                                ", \n\nLocation: \"" + item.getPhotoPath() + "\"" , Toast.LENGTH_SHORT).show();

                        Bundle bundle = new Bundle();
                        bundle.putString("imageUri", item.getPhotoPath());
                        Intent intent = new Intent(context, PhotoViewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        homeViewModel.loadAllPhotos().observe(getViewLifecycleOwner(), loadPhotosObserver);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
