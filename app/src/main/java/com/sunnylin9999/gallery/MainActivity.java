package com.sunnylin9999.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.sunnylin9999.gallery.model.PhotoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private String TAG = "MainActivity";

    private static final String[] READ_STORGE =
            {Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int RC_STORAGE_PERM = 1000;

    private GridView gridView;
    private static ContentResolver mContRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        mContRes = getContentResolver();

    }

    private boolean hasStoragePermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // Some permissions have been granted
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());

        if (requestCode == RC_STORAGE_PERM) {
            showPhotos();
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // Some permissions have been denied
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasStoragePermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(hasStoragePermission()) {
            Toast.makeText(this, "TODO: Read Storage things", Toast.LENGTH_LONG).show();
            showPhotos();
        } else {
            //Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_storage),
                    RC_STORAGE_PERM,
                    READ_STORGE
            );
        }
    }

    private void showPhotos() {
        final List<PhotoItem> photoItems = getAllPhotos();

        GridAdapter adapter = new GridAdapter(MainActivity.this, photoItems);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoItem item = photoItems.get(position);
                Toast.makeText(MainActivity.this, "Name: \"" + item.getPhotoName() + "\"" +
                        ", \n\nLocation: \"" + item.getPhotoPath() + "\"" , Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("imageUri", String.valueOf(Uri.fromFile(new File(item.getPhotoPath()))));
                Intent intent = new Intent(MainActivity.this, PhotoViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private List<PhotoItem> getAllPhotos() {
        String[] projection = new String[] {
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DATE_ADDED
                };

        Cursor cursor = mContRes.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        List<PhotoItem> photoItems= new ArrayList<>();
        String photoId;
        String photoName;
        String photoPath;
        String photoDateAdded;

        while (cursor.moveToNext()) {
            photoId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            photoName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
            photoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            photoDateAdded = cursor.getString((cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)));

            Log.d(TAG, photoId + ", " + photoName + ", " + photoPath);
            PhotoItem photoItem = new PhotoItem(photoId, photoPath, photoName, photoDateAdded);

            photoItems.add((photoItem));
        }
        cursor.close();
        cursor = null;

        return photoItems;
    }
}
