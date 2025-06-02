package com.example.sendingdatafromactivitytofragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyFisrtFragment extends Fragment {
    TextView result;

    public MyFisrtFragment() {
        // Required empty public constructor
        /// abnc
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               View view= inflater.inflate(R.layout.fragment_my_fisrt,container,false);
               result=view.findViewById(R.id.textViewResult);

              Bundle bundle=getArguments();
              int userweight=bundle.getInt("weight");
              int userheight= bundle.getInt("height");
              double userBmi= (userweight * 1000)/(userheight * userweight);
              result.setText("Your BMI is :"+ userBmi);
              return view;

    }
}