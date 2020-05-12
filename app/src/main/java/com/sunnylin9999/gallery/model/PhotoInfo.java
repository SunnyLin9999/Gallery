package com.sunnylin9999.gallery.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class PhotoInfo implements Parcelable {

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

    protected PhotoInfo(Parcel in) {
        id = in.readString();
        filename = in.readString();
        imageUri = in.readString();
        parentAlbumName = in.readString();
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel in) {
            return new PhotoInfo(in);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(filename);
        dest.writeString(imageUri);
        dest.writeString(parentAlbumName);
    }
}
