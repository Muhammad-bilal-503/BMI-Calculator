package com.example.sendingdatafromactivitytofragment;

import android.content.Context;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFirstFragment extends Fragment {
    TextView result, category;
    private OnBmiCalculatedListener listener;

    public interface OnBmiCalculatedListener {
        void onBmiCalculated(double bmi, String category);
    }

    public MyFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBmiCalculatedListener) {
            listener = (OnBmiCalculatedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBmiCalculatedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_first, container, false);
        result = view.findViewById(R.id.textViewResult);
        category = view.findViewById(R.id.textViewCategory);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int userWeight = bundle.getInt("weight");
            int userHeight = bundle.getInt("height");

            if (userHeight > 0) { // Avoid division by zero
                // BMI formula: weight (kg) / (height (m))^2
                double heightInMeters = userHeight / 100.0;
                double userBmi = userWeight / (heightInMeters * heightInMeters);
                result.setText(String.format("Your BMI is: %.2f", userBmi));

                String bmiCategory;
                int color;

                if (userBmi < 18.5) {
                    bmiCategory = "Underweight";
                    color = R.color.underweight;
                } else if (userBmi < 25) {
                    bmiCategory = "Normal";
                    color = R.color.normal;
                } else if (userBmi < 30) {
                    bmiCategory = "Overweight";
                    color = R.color.overweight;
                } else {
                    bmiCategory = "Obese";
                    color = R.color.obese;
                }

                category.setText(bmiCategory);
                category.setTextColor(ContextCompat.getColor(getContext(), color));

                if (listener != null) {
                    listener.onBmiCalculated(userBmi, bmiCategory);
                }

            } else {
                result.setText("Invalid height");
            }
        } else {
            result.setText("Please calculate your BMI");
        }

        return view;
    }
}