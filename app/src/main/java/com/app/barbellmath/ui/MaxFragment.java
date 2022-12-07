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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MaxFragment extends Fragment {

    EditText weight,reps;
    Button calc;
    TextView one_rep_max;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_max, container, false);
        weight = root.findViewById(R.id.weight);
        reps = root.findViewById(R.id.reps);
        calc = root.findViewById(R.id.cacl);
        one_rep_max = root.findViewById(R.id.oneRepMax);

        calc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {

                hideKeyboard();
                String weight_str = weight.getText().toString().trim();
                String reps_str = reps.getText().toString().trim();
                if(weight_str.equals("") || reps_str.equals("")){
                    //Toast.makeText(MainActivity.this, "Calculation Error", Toast.LENGTH_LONG).show();
                    printToScreen("Calculation Error");
                    return;
                }
                int weight_int = Integer.parseInt(weight_str);
                int reps_int = Integer.parseInt(reps_str);
                if(reps_int > 6){
                    printToScreen("Calculations are better when reps are in 1-6 range");
                }
                double max = weight_int/(1.0278-(0.0278*reps_int));
                String max_str = String.format("%.1f", roundToNearest2Point5(max));
                one_rep_max.setText(max_str);

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