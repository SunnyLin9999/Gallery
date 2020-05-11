package com.sunnylin9999.gallery.ui.home;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.sunnylin9999.gallery.model.PhotoItem;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private String TAG = "HomeViewModel";

    private MutableLiveData<String> mText;

    private MutableLiveData<List<PhotoItem>> mPhotoItems;

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


    public MutableLiveData<List<PhotoItem>> loadAllPhotos() {
        if(mPhotoItems == null) {
            mPhotoItems = new MutableLiveData<>();
        }

        List<PhotoItem> items = new ArrayList<>();

        ContentResolver contentResolver = getApplication().getContentResolver();

        String[] projection = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED
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
            items.add(photoItem);
        }
        cursor.close();
        cursor = null;

//        for (PhotoItem item: items) {
//            Log.i(TAG, item.getPhotoId() + ", " + item.getPhotoName());
//        }

        mPhotoItems.setValue(items);
        return mPhotoItems;
    }
}
