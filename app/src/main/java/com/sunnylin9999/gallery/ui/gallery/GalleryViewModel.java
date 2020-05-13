package com.sunnylin9999.gallery.ui.gallery;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sunnylin9999.gallery.model.AlbumInfo;
import com.sunnylin9999.gallery.model.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {
    private String TAG = "GalleryViewModel";

    private MutableLiveData<String> mText;

    private MutableLiveData<List<AlbumInfo>> mAlbumInfos;

    public GalleryViewModel(Application application) {
        super(application);
        if(mAlbumInfos == null) {
            mAlbumInfos = new MutableLiveData<>();
        }
    }

    public MutableLiveData<String> getText() {
        if(mText == null) {
            mText = new MutableLiveData<>();
            mText.setValue("This is Gallery Fragment");
        }
        return mText;
    }

    public MutableLiveData<List<AlbumInfo>> loadAlbumNameList() {
        ContentResolver contentResolver = getApplication().getContentResolver();

        String[] projection = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA
        };

        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        List<String> albumUriList = new ArrayList<>();

        List<AlbumInfo> albumInfos = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            String filename = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
            String imageUri = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            File f = new File(imageUri);
            PhotoInfo photoInfo = new PhotoInfo(id, filename, imageUri, f.getParent());

            if (!albumUriList.contains(f.getParent())) {
                albumUriList.add(f.getParent());

                AlbumInfo albumInfo = new AlbumInfo(f.getParent());
                albumInfo.addToAlbumInfo(photoInfo, albumInfo); //first photo
                albumInfos.add(albumInfo);
                Log.e(TAG, "album name=" + f.getParent() + ", size= " + albumUriList.size());
            }
        }
        cursor.close();
        cursor = null;

        mAlbumInfos.setValue(albumInfos);
        return mAlbumInfos;
    }
}
