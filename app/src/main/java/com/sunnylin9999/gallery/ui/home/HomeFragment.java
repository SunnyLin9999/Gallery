package com.sunnylin9999.gallery.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.sunnylin9999.gallery.PhotoViewActivity;
import com.sunnylin9999.gallery.R;
import com.sunnylin9999.gallery.model.AlbumInfo;
import com.sunnylin9999.gallery.model.PhotoInfo;

import java.util.List;

public class HomeFragment extends Fragment {
    private String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;

    private Context context;

    private TextView textView;

    private GridView gridView;

    private List<PhotoInfo> mPhotoInfoList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        gridView = root.findViewById(R.id.grid_home);
        context = getActivity();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        Observer<String> textObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        };
        homeViewModel.getText().observe(getViewLifecycleOwner(), textObserver);

        Bundle bundle = getArguments();
        AlbumInfo info = bundle.getParcelable("album_info");
        if(info != null) {
            //showed by album name, from gallery
            mPhotoInfoList = info.getPhotoInfoList();
            Log.d(TAG, "Gallery->Home, PhotoInfo List size= " + mPhotoInfoList.size() +
                    ", Album name= " + info.getAlbumName());
            updateHomeAdapter(mPhotoInfoList);

        } else {
            //showed by all photos, default
            Observer<List<PhotoInfo>> loadPhotosObserver = new Observer<List<PhotoInfo>>() {
                @Override
                public void onChanged(@Nullable final List<PhotoInfo> photoInfoList) {
                    mPhotoInfoList = photoInfoList;
                    updateHomeAdapter(mPhotoInfoList);
                }
            };
            homeViewModel.loadAllPhotos().observe(getViewLifecycleOwner(), loadPhotosObserver);
        }
    }

    private void updateHomeAdapter(final List<PhotoInfo> photoInfoList) {
        HomeAdapter adapter = new HomeAdapter(context, photoInfoList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoInfo info = photoInfoList.get(position);
                Intent intent = new Intent(context, PhotoViewActivity.class);
                intent.putExtra("photo info", info);
                startActivity(intent);
            }
        });
    }
}
