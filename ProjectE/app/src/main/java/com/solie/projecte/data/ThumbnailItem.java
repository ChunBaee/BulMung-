package com.solie.projecte.data;

import android.widget.ImageView;

public class ThumbnailItem {
    String thumbImage;
    boolean photoOrVideo;

    public ThumbnailItem(String thumbImage, boolean photoOrVideo) {
        this.thumbImage = thumbImage;
        this.photoOrVideo = photoOrVideo;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public boolean isPhotoOrVideo() {
        return photoOrVideo;
    }

    public void setPhotoOrVideo(boolean photoOrVideo) {
        this.photoOrVideo = photoOrVideo;
    }
}
