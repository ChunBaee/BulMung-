package com.solie.mrio.data;

public class AcceptListData {
    int listImage;
    String listName;

    public AcceptListData(int listImage, String listName) {
        this.listImage = listImage;
        this.listName = listName;
    }

    public int getListImage() {
        return listImage;
    }

    public void setListImage(int listImage) {
        this.listImage = listImage;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
