package com.solie.mrio.data;

import android.graphics.Bitmap;

public class ProfileSettingData {
    Bitmap image;

    public ProfileSettingData(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
