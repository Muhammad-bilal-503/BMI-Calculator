package com.example.sendingdatafromactivitytofragment.db;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BmiViewModel extends AndroidViewModel {
    private BmiRepository repository;
    private final LiveData<List<BmiRecord>> allRecords;

    public BmiViewModel(Application application) {
        super(application);
        repository = new BmiRepository(application);
        allRecords = repository.getAllRecords();
    }

    public LiveData<List<BmiRecord>> getAllRecords() {
        return allRecords;
    }

    public void insert(BmiRecord bmiRecord) {
        repository.insert(bmiRecord);
    }
}