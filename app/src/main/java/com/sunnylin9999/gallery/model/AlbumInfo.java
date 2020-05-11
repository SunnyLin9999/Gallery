package com.sunnylin9999.gallery.model;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AlbumInfo {

    private static final String TAG = "AlbumInfo";

    private String albumName;

    private List<PhotoInfo> photoInfoList;

    public AlbumInfo(String albumName) {
        this.albumName = albumName;
        this.photoInfoList = new ArrayList<>();
    }

    public void addToAlbumInfo(PhotoInfo photoInfo, AlbumInfo albumInfo) {
        albumInfo.photoInfoList.add(photoInfo);
        Log.d(TAG, "album= " + albumInfo.getAlbumName() + " add photo uri= " + photoInfo.getImageUri());
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Uri getCoverPhotoInfoUri() {
        return photoInfoList.get(0).getImageUri();
    }

    public List<PhotoInfo> getPhotoInfoList() {
        return photoInfoList;
    }
}
