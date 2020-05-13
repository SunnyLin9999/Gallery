package com.sunnylin9999.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.sunnylin9999.gallery.model.PhotoInfo;

import java.io.File;
import java.io.FileNotFoundException;

public class PhotoViewActivity extends AppCompatActivity {
    private final String TAG = "PhotoViewActivity";

    private Context mContext;

    private ImageView mPhotoView;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        mContext = this;

        Intent intent = getIntent();
        PhotoInfo photoInfo = intent.getParcelableExtra("photo info");
        File file = new File(String.valueOf(photoInfo.getImageUri()));
        Uri imageUri = Uri.fromFile(file.getAbsoluteFile());
        Log.d(TAG, String.valueOf(imageUri));

        mPhotoView = (ImageView) findViewById(R.id.photoView);
        try {

            mBitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(imageUri));
            mPhotoView.setImageBitmap(mBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photo_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.e(TAG, "item.getItemId " + item.getItemId() + ", R.menu.menu_photo_view " + R.id.share_to_fb);
        switch(item.getItemId()) {
            case R.id.share_to_fb:
                sharePhotoToFacebook();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sharePhotoToFacebook() {
        Log.e(TAG, "sharePhotoToFacebook");
        Bitmap image = mBitmap;
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }
}
