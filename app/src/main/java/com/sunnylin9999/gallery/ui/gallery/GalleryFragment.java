package com.sunnylin9999.gallery.ui.gallery;

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
import android.widget.Toast;

import com.sunnylin9999.gallery.PhotoViewActivity;
import com.sunnylin9999.gallery.R;
import com.sunnylin9999.gallery.model.AlbumInfo;
import java.util.List;

public class GalleryFragment extends Fragment {
    private String TAG = "GalleryFragment";

    private GalleryViewModel galleryViewModel;

    private Context context;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        final GridView gridView = root.findViewById(R.id.grid_gallery);

        context = getActivity();

        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        Observer<String> textObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        };
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textObserver);

        Observer<List<AlbumInfo>> albumUriListObserver = new Observer<List<AlbumInfo>>() {
            @Override
            public void onChanged(final List<AlbumInfo> albumInfos) {

                GalleryAdapter adapter = new GalleryAdapter(getActivity(), albumInfos);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AlbumInfo info = albumInfos.get(position);
                        String[] splitAlbumUri = info.getAlbumName().split("/");
                        String albumName = splitAlbumUri[splitAlbumUri.length-1];

                        Toast.makeText(context, "Album: \"" + albumName + "\"" +
                                ", \n\nLocation: \"" + info.getAlbumName() + "\"" , Toast.LENGTH_SHORT).show();

                        Bundle bundle = new Bundle();
                        bundle.putString("imageUri", String.valueOf(info.getCoverPhotoInfoUri()));
                        Intent intent = new Intent(context, PhotoViewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        galleryViewModel.loadAlbumNameList().observe(getViewLifecycleOwner(), albumUriListObserver);

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}
