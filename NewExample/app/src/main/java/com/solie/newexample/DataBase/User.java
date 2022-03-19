package com.solie.newexample.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;
    private String address;
    private int phone_number;

    public User(int id, String name, String address, int phone_number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }
}
