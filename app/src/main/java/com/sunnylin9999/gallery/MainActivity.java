package com.sunnylin9999.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private String TAG = "MainActivity";

    private static final String[] READ_STORGE =
            {Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int RC_STORAGE_PERM = 1000;

    private GridView gridView;
    private int[] imageList = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                               R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                               R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};
    private String[] titleList = {"title-1", "title-2", "title-3",
                                  "title-4", "title-5", "title-6",
                                  "title-7", "title-8", "title-9"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
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
        GridAdapter adapter = new GridAdapter(MainActivity.this, imageList, titleList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "click" + titleList[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
