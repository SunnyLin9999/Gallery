package com.sunnylin9999.gallery.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AlbumInfo implements Parcelable {

    private static final String TAG = "AlbumInfo";

    private String albumName;

    private List<PhotoInfo> photoInfoList;

    public AlbumInfo(String albumName) {
        this.albumName = albumName;
        this.photoInfoList = new ArrayList<>();
    }

    protected AlbumInfo(Parcel in) {
        albumName = in.readString();
        photoInfoList = in.createTypedArrayList(PhotoInfo.CREATOR);
    }

    public static final Creator<AlbumInfo> CREATOR = new Creator<AlbumInfo>() {
        @Override
        public AlbumInfo createFromParcel(Parcel in) {
            return new AlbumInfo(in);
        }

        @Override
        public AlbumInfo[] newArray(int size) {
            return new AlbumInfo[size];
        }
    };

    public void addToAlbumInfo(PhotoInfo photoInfo, AlbumInfo albumInfo) {
        albumInfo.photoInfoList.add(photoInfo);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(albumName);
        dest.writeTypedList(photoInfoList);
    }
}
