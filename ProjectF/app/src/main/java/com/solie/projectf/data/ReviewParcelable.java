package com.solie.projectf.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewParcelable implements Parcelable {
    String profile;
    String id;
    String time;
    String review;
    int recommend;
    float rating;

    public ReviewParcelable(String profile, String id, String time, String review, int recommend, float rating) {
        this.profile = profile;
        this.id = id;
        this.time = time;
        this.review = review;
        this.recommend = recommend;
        this.rating = rating;
    }

    public String getProfile() {
        return profile;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getReview() {
        return review;
    }

    public int getRecommend() {
        return recommend;
    }

    public float getRating() {
        return rating;
    }

    protected ReviewParcelable(Parcel in) {
        profile = in.readString();
        id = in.readString();
        time = in.readString();
        review = in.readString();
        recommend = in.readInt();
        rating = in.readFloat();
    }

    public static final Creator<ReviewParcelable> CREATOR = new Creator<ReviewParcelable>() {
        @Override
        public ReviewParcelable createFromParcel(Parcel in) {
            return new ReviewParcelable(in);
        }

        @Override
        public ReviewParcelable[] newArray(int size) {
            return new ReviewParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.profile);
        dest.writeString(this.id);
        dest.writeString(this.time);
        dest.writeString(this.review);
        dest.writeInt(this.recommend);
        dest.writeFloat(this.rating);
    }
}
