package com.example.sendingdatafromactivitytofragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sendingdatafromactivitytofragment.db.BmiRecord;
import com.example.sendingdatafromactivitytofragment.db.BmiViewModel;

public class MainActivity extends AppCompatActivity implements MyFirstFragment.OnBmiCalculatedListener {
    EditText editWeight, editHeight;
    Button calculate, historyButton;
    Switch unitSwitch;
    MyFirstFragment myFirstFragment;
    boolean isMetric = true;
    private BmiViewModel bmiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editWeight = findViewById(R.id.editTextWeight);
        editHeight = findViewById(R.id.editTextHieght);
        calculate = findViewById(R.id.buttonCalculate);
        unitSwitch = findViewById(R.id.unitSwitch);
        historyButton = findViewById(R.id.buttonHistory);

        bmiViewModel = new ViewModelProvider(this).get(BmiViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        myFirstFragment = new MyFirstFragment();
        fragmentTransaction.add(R.id.frame, myFirstFragment);
        fragmentTransaction.commit();

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMetric = !isChecked;
                if (isMetric) {
                    unitSwitch.setText("Metric (kg/cm)");
                    editWeight.setHint("Please Enter Your Weight (Kg)");
                    editHeight.setHint("Please Enter Your Height (cm)");
                } else {
                    unitSwitch.setText("Imperial (lbs/in)");
                    editWeight.setHint("Please Enter Your Weight (lbs)");
                    editHeight.setHint("Please Enter Your Height (in)");
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    double userWeight = Double.parseDouble(editWeight.getText().toString());
                    double userHeight = Double.parseDouble(editHeight.getText().toString());

                    if (!isMetric) {
                        // Convert lbs to kg and inches to cm
                        userWeight *= 0.453592;
                        userHeight *= 2.54;
                    }

                    bundle.putInt("weight", (int) userWeight);
                    bundle.putInt("height", (int) userHeight);
                    myFirstFragment.setArguments(bundle);

                    // To refresh the fragment view
                    getSupportFragmentManager().beginTransaction()
                            .detach(myFirstFragment)
                            .attach(myFirstFragment)
                            .commit();
                } catch (NumberFormatException e) {
                    // Handle the case where the user enters non-numeric input
                }
            }
        });
    }

    @Override
    public void onBmiCalculated(double bmi, String category) {
        BmiRecord record = new BmiRecord(System.currentTimeMillis(), bmi, category);
        bmiViewModel.insert(record);
    }
}