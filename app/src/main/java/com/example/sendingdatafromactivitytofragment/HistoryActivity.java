package com.example.sendingdatafromactivitytofragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendingdatafromactivitytofragment.db.BmiViewModel;

public class HistoryActivity extends AppCompatActivity {

    private BmiViewModel bmiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final BmiHistoryAdapter adapter = new BmiHistoryAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bmiViewModel = new ViewModelProvider(this).get(BmiViewModel.class);
        bmiViewModel.getAllRecords().observe(this, adapter::setRecords);

        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}