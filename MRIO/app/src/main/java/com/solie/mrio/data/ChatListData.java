package com.solie.mrio.data;

public class ChatListData {
    int ListImage;
    String ListName;
    String ListTime;
    String ListContent;

    public ChatListData(int listImage, String listName, String listTime, String listContent) {
        ListImage = listImage;
        ListName = listName;
        ListTime = listTime;
        ListContent = listContent;
    }

    public int getListImage() {
        return ListImage;
    }

    public void setListImage(int listImage) {
        ListImage = listImage;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        ListName = listName;
    }

    public String getListTime() {
        return ListTime;
    }

    public void setListTime(String listTime) {
        ListTime = listTime;
    }

    public String getListContent() {
        return ListContent;
    }

    public void setListContent(String listContent) {
        ListContent = listContent;
    }
}
