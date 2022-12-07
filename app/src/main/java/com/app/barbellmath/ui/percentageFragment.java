package com.app.barbellmath.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.barbellmath.R;

import androidx.fragment.app.Fragment;

public class percentageFragment extends Fragment {

    EditText weight,percent;
    Button calc;
    TextView res;
    TextView res90,res80,res70,res60,res50;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_percentage, container, false);
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        weight = root.findViewById(R.id.weight);
        percent = root.findViewById(R.id.percent);
        calc = root.findViewById(R.id.cacl);
        res = root.findViewById(R.id.oneRepMax);
        res90 =  root.findViewById(R.id.res90);
        res80 =  root.findViewById(R.id.res80);
        res70 =  root.findViewById(R.id.res70);
        res60 =  root.findViewById(R.id.res60);
        res50 =  root.findViewById(R.id.res50);

        calc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {

                hideKeyboard();
                String weight_str = weight.getText().toString().trim();
                String percent_str = percent.getText().toString().trim();
                if(weight_str.equals("") || percent_str.equals("")){
                    //Toast.makeText(MainActivity.this, "Calculation Error", Toast.LENGTH_LONG).show();
                    printToScreen("Calculation Error");
                    return;
                }else if(percent_str.endsWith("%")){
                    percent_str = percent_str.substring(0,percent_str.length()-1);
                }
                int weight_int = Integer.parseInt(weight_str);
                int percent_int = Integer.parseInt(percent_str);

                double ans = (weight_int * percent_int)/100;
                String ans_str = String.format("%.1f", roundToNearest2Point5(ans));
                res.setText(ans_str);
            }
        });


        return root;
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public void printToScreen(String str){
        Toast toast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v1 != null) v1.setGravity(Gravity.CENTER);
        toast.show();

    }
    static double roundToNearest2Point5(double n) {
        return Math.round(n * 0.4) / 0.4;
    }

}