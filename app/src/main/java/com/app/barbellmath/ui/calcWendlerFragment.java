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

public class calcWendlerFragment extends Fragment {

    EditText weight;
    Button calc;
    TextView res40, res50, res60, res65, res70, res75, res80, res85, res90, res95, res75_2, res85_2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_calc_wendler, container, false);


        weight = root.findViewById(R.id.weight);
        calc = root.findViewById(R.id.calc);
        res40 = root.findViewById(R.id.res40);
        res50 = root.findViewById(R.id.res50);
        res60 = root.findViewById(R.id.res60);
        res65 = root.findViewById(R.id.res65);
        res70 = root.findViewById(R.id.res70);
        res75 = root.findViewById(R.id.res75);
        res80 = root.findViewById(R.id.res80);
        res85 = root.findViewById(R.id.res85);
        res90 = root.findViewById(R.id.res90);
        res95 = root.findViewById(R.id.res95);
        res75_2 = root.findViewById(R.id.res75_2);
        res85_2 = root.findViewById(R.id.res85_2);



        calc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String weight_str = weight.getText().toString().trim();
                if (weight_str.equals("")) {
                    //Toast.makeText(MainActivity.this, "Calculation Error", Toast.LENGTH_LONG).show();
                    printToScreen("Calculation Error");
                    return;
                }
                double weight_double = Double.parseDouble(weight_str);
                res40.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.4)));
                res50.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.5)));
                res60.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.6)));
                res65.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.65)));
                res70.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.7)));
                res75.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.75)));
                res75_2.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.75)));
                res80.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.8)));
                res85.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.85)));
                res85_2.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.85)));
                res90.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.9)));
                res95.setText(String.format("%.1f", roundToNearest2Point5(weight_double * 0.95)));
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

    public void printToScreen(String str) {
        Toast toast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
        if (v1 != null) v1.setGravity(Gravity.CENTER);
        toast.show();

    }

    static double roundToNearest2Point5(double n) {
        return Math.round(n * 0.4) / 0.4;
    }
}
