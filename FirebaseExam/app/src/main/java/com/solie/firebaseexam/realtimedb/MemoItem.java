package com.solie.firebaseexam.realtimedb;

public class MemoItem {
    private String user;
    private String memoTitle;
    private String memoContents;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public String getMemoContents() {
        return memoContents;
    }

    public void setMemoContents(String memoContents) {
        this.memoContents = memoContents;
    }
}
