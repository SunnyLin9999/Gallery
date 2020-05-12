package com.sunnylin9999.gallery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.sunnylin9999.gallery.model.PhotoInfo;

import java.io.File;
import java.io.FileNotFoundException;

public class PhotoViewActivity extends AppCompatActivity {
    private final String TAG = "PhotoViewActivity";

    private Context mContext;

    private ActionBar mActionBar;

    private ImageView mPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        mContext = this;

//        Bundle bundle = getIntent().getExtras();
//        String path = bundle.getString("imageUri");
//        Uri imageUri = Uri.fromFile(new File(path));
//        Log.d(TAG, String.valueOf(imageUri));

        Intent intent = getIntent();
        PhotoInfo photoInfo = intent.getParcelableExtra("photo info");
        File file = new File(String.valueOf(photoInfo.getImageUri()));
        Uri imageUri = Uri.fromFile(file.getAbsoluteFile());
        Log.d(TAG, String.valueOf(imageUri));

        mPhotoView = (ImageView) findViewById(R.id.photoView);
        try {

            Bitmap bmp = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(imageUri));
            mPhotoView.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
