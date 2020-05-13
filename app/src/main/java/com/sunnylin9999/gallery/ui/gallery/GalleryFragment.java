package com.sunnylin9999.gallery.ui.gallery;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnylin9999.gallery.R;
import com.sunnylin9999.gallery.model.AlbumInfo;
import com.sunnylin9999.gallery.ui.home.HomeFragment;

import java.util.List;

public class GalleryFragment extends Fragment {

    private static String TAG = "GalleryFragment";

    private GalleryViewModel galleryViewModel;

    private Context context;

    private TextView textView;

    private GridView gridView;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        textView = root.findViewById(R.id.text_gallery);
        gridView = root.findViewById(R.id.grid_gallery);
        context = getActivity();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                        Log.d(TAG, "Album: " + albumName + ", Location: " + info.getAlbumName());
                        Toast.makeText(context, "Album: \"" + albumName + "\"", Toast.LENGTH_SHORT).show();

                        navigateToHomeWithAlbumInfo(info.getAlbumName());
                    }
                });
            }
        };
        galleryViewModel.loadAlbumNameList().observe(getViewLifecycleOwner(), albumUriListObserver);
    }

    public void navigateToHomeWithAlbumInfo(String albumName) {
        Bundle bundle = new Bundle();
//        bundle.putParcelable("album_info", albumInfo);
        bundle.putString("album_name_path", albumName);

        HomeFragment homeFragment = HomeFragment.newInstance();
        homeFragment.setArguments(bundle);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_gallery_to_nav_home, bundle);

        Log.d(TAG, "Navigate action by album name: " + albumName);
    }
}
