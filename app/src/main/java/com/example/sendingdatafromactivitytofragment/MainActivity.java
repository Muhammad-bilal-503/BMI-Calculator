package com.example.sendingdatafromactivitytofragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.sendingdatafromactivitytofragment.db.BmiRecord;
import com.example.sendingdatafromactivitytofragment.db.BmiViewModel;

public class MainActivity extends AppCompatActivity {
    EditText editWeight, editHeight;
    Button calculate, historyButton;
    Switch unitSwitch;
    TextView textViewResult, textViewCategory;
    CardView resultCard;
    boolean isMetric = true;
    private BmiViewModel bmiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editWeight = findViewById(R.id.editTextWeight);
        editHeight = findViewById(R.id.editTextHieght);
        calculate = findViewById(R.id.buttonCalculate);
        unitSwitch = findViewById(R.id.unitSwitch);
        historyButton = findViewById(R.id.buttonHistory);
        resultCard = findViewById(R.id.resultCard);
        textViewResult = findViewById(R.id.textViewResult);
        textViewCategory = findViewById(R.id.textViewCategory);

        bmiViewModel = new ViewModelProvider(this).get(BmiViewModel.class);

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        unitSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
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
        });

        calculate.setOnClickListener(v -> {
            try {
                double userWeight = Double.parseDouble(editWeight.getText().toString());
                double userHeight = Double.parseDouble(editHeight.getText().toString());

                if (!isMetric) {
                    // Convert lbs to kg and inches to cm
                    userWeight *= 0.453592;
                    userHeight *= 2.54;
                }

                if (userHeight > 0) {
                    calculateAndDisplayBmi(userWeight, userHeight);
                }

            } catch (NumberFormatException e) {
                // Handle empty or invalid input
            }
        });
    }

    private void calculateAndDisplayBmi(double weight, double height) {
        double heightInMeters = height / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);

        String bmiCategory;
        int colorResId;

        if (bmi < 18.5) {
            bmiCategory = "Underweight";
            colorResId = R.color.underweight;
        } else if (bmi < 25) {
            bmiCategory = "Normal";
            colorResId = R.color.normal;
        } else if (bmi < 30) {
            bmiCategory = "Overweight";
            colorResId = R.color.overweight;
        } else {
            bmiCategory = "Obese";
            colorResId = R.color.obese;
        }

        textViewResult.setText(String.format("Your BMI is: %.2f", bmi));
        textViewCategory.setText(bmiCategory);
        textViewCategory.setTextColor(ContextCompat.getColor(this, colorResId));
        resultCard.setVisibility(View.VISIBLE);

        // Save to database
        BmiRecord record = new BmiRecord(System.currentTimeMillis(), bmi, bmiCategory);
        bmiViewModel.insert(record);
    }
}