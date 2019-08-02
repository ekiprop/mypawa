package com.matrix.mypawa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "power_table")
public class Power {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String phone_no;

    private String device0;
    private String device1;
    private String device2;
    private String device3;

    public void setId(int id) {
        this.id = id;
    }

    public Power(String name, String phone_no, String device0, String device1, String device2, String device3) {
        this.name = name;
        this.phone_no = phone_no;
        this.device0 = device0;
        this.device1 = device1;
        this.device2 = device2;
        this.device3 = device3;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getDevice0() {
        return device0;
    }

    public String getDevice1() {
        return device1;
    }

    public String getDevice2() {
        return device2;
    }

    public String getDevice3() {
        return device3;
    }
}
