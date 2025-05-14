package com.example.sendingdatafromactivitytofragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    EditText editWeight, editHieght;
    Button calculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
           FragmentManager fragmentManager= getSupportFragmentManager();
           FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
           MyFisrtFragment myFisrtFragment=new MyFisrtFragment();
            editHieght= findViewById(R.id.editTextWeight);
            editWeight=findViewById(R.id.editTextHieght);
            calculate=findViewById(R.id.buttonCalculate);
            calculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    int userWeight= Integer.parseInt(editWeight.getText().toString());
                    int userHieght= Integer.parseInt(editHieght.getText().toString());
                    bundle.putInt("weight",userWeight);
                    bundle.putInt("hieght",userHieght);
                    myFisrtFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.frame,myFisrtFragment);
                    fragmentTransaction.commit();
                }
            });

            return insets;
        });
    }
}