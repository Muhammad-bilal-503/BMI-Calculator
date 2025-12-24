package com.example.sendingdatafromactivitytofragment.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BmiRecordDao {
    @Insert
    void insert(BmiRecord bmiRecord);

    @Query("SELECT * FROM bmi_records ORDER BY timestamp DESC")
    LiveData<List<BmiRecord>> getAllRecords();
}