package com.sunnylin9999.gallery.ui.home;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sunnylin9999.gallery.model.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private String TAG = "HomeViewModel";

    private MutableLiveData<String> mText;

    private MutableLiveData<List<PhotoInfo>> mPhotoInfos;

    public HomeViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<String> getText() {
        if(mText == null) {
            mText = new MutableLiveData<>();
            mText.setValue("This is home fragment");
        }
        return mText;
    }

    public MutableLiveData<List<PhotoInfo>> loadAllPhotos() {
        if(mPhotoInfos == null) {
            mPhotoInfos = new MutableLiveData<>();
        }

        List<PhotoInfo> allPhotoInfoList = new ArrayList<>();

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

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            String filename = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
            String imageUri = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            File f = new File(imageUri);
            PhotoInfo photoInfo = new PhotoInfo(id, filename, imageUri, f.getParent());
            allPhotoInfoList.add(photoInfo);
        }
        cursor.close();
        cursor = null;

        mPhotoInfos.setValue(allPhotoInfoList);
        return mPhotoInfos;
    }

    public MutableLiveData<List<PhotoInfo>> loadAlbumPhotos(String albumPath) {
        if(mPhotoInfos == null) {
            mPhotoInfos = new MutableLiveData<>();
        }

        if(albumPath == null) {
            return null;
        }

        List<PhotoInfo> albumPhotoLsit = new ArrayList<>();

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

        File albumFile = new File(albumPath);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            String filename = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
            String imageUri = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            File f = new File(imageUri);
            PhotoInfo photoInfo = new PhotoInfo(id, filename, imageUri, f.getParent());
            if((albumFile.getPath()).equals(f.getParent())) {
                albumPhotoLsit.add(photoInfo);
                Log.d(TAG, "add " + filename + ", current size " + albumPhotoLsit.size());
            }
        }
        cursor.close();
        cursor = null;

        mPhotoInfos.setValue(albumPhotoLsit);
        return mPhotoInfos;
    }
}
