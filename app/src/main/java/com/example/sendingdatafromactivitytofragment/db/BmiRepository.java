package com.example.sendingdatafromactivitytofragment.db;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BmiRepository {
    private BmiRecordDao bmiRecordDao;
    private LiveData<List<BmiRecord>> allRecords;

    public BmiRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        bmiRecordDao = db.bmiRecordDao();
        allRecords = bmiRecordDao.getAllRecords();
    }

    public LiveData<List<BmiRecord>> getAllRecords() {
        return allRecords;
    }

    public void insert(BmiRecord bmiRecord) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            bmiRecordDao.insert(bmiRecord);
        });
    }
}