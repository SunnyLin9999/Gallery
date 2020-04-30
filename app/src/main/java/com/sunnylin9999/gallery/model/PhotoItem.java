package com.sunnylin9999.gallery.model;

public class PhotoItem {

    private String mPhotoId;

    private String mPhotoPath;

    private String mPhotoName;

    private String mPhotoDateAdded;

    public PhotoItem(String photoId, String photoPath, String photoName, String photoDateAdded) {
        this.mPhotoId = photoId;
        this.mPhotoPath = photoPath;
        this.mPhotoName = photoName;
        this.mPhotoDateAdded = photoDateAdded;
    }

    public String getPhotoId() {
        return mPhotoId;
    }

    public void setPhotoId(String mPhotoId) {
        this.mPhotoId = mPhotoId;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String mPhotoPath) {
        this.mPhotoPath = mPhotoPath;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setmPhotoName(String mPhotoName) {
        this.mPhotoName = mPhotoName;
    }

    public String getPhotoDateAdded() {
        return mPhotoDateAdded;
    }

    public void setPhotoDateAdded(String mPhotoDateAdded) {
        this.mPhotoDateAdded = mPhotoDateAdded;
    }
}
