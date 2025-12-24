package com.example.sendingdatafromactivitytofragment.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bmi_records")
public class BmiRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long timestamp;
    public double bmi;
    public String category;

    public BmiRecord(long timestamp, double bmi, String category) {
        this.timestamp = timestamp;
        this.bmi = bmi;
        this.category = category;
    }
}