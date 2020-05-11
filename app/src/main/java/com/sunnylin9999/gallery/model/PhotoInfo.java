package com.sunnylin9999.gallery.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class PhotoInfo {

    private String id;
    private String filename;
    private String imageUri;
    private String parentAlbumName;

    public PhotoInfo(String id, String filename, String imageUri, String parentAlbumName) {
        this.id = id;
        this.filename = filename;
        this.imageUri = imageUri;
        this.parentAlbumName = parentAlbumName;
    }

    public String getId() {
        return id;
    }

    public Uri getImageUri() {
        return imageUri == null ? null : Uri.parse(imageUri);
    }

    public void setImageUri(String tempUri) {
        this.imageUri = tempUri;
    }

    public String getFilename() {
        return filename;
    }

    public String getParentAlbumName() {
        return parentAlbumName;
    }

    public void setParentAlbumName(String parentAlbumName) {
        this.parentAlbumName = parentAlbumName;
    }
}
