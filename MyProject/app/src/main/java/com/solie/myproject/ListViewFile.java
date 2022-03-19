package com.solie.myproject;

public class ListViewFile {

    public String writer;
    public String writer_image;
    public String time;
    public Float rating;
    public String contents;
    public int recommend;

    public ListViewFile(String writer, String writer_image, String time, Float rating, String contents, int recommend) {
        this.writer = writer;
        this.writer_image = writer_image;
        this.time = time;
        this.rating = rating;
        this.contents = contents;
        this.recommend = recommend;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter_image() {
        return writer_image;
    }

    public void setWriter_image(String writer_image) {
        this.writer_image = writer_image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
}
