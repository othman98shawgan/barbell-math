package com.app.barbellmath.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.barbellmath.R;

import androidx.fragment.app.Fragment;

public class revPlatesFragment extends Fragment {

    TextView weight;
    Button calc;
    ImageButton edit, done;
    EditText in25, in20, in15, in10, in5, in2_5, in1_25;
    EditText count25, count20, count15, count10, count5, count2_5, count1_25;
    TextView enter_plates_text;
    int count25_int = 0, count20_int = 0, count15_int = 0, count10_int = 0, count5_int = 0, count2_5_int = 0, count1_25_int = 0;
    int in25_int = 0, in20_int = 0, in15_int = 0, in10_int = 0, in5_int = 0, in2_5_int = 0, in1_25_int = 0;
    Toast prev_toast = null;
    ImageView image25, image20, image15, image10, image5, image2_5, image1_25;
    boolean edit_mode = false;

    SharedPreferences mPrefs;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rev_plates, container, false);
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPrefs = getActivity().getSharedPreferences("Weights", Context.MODE_PRIVATE);
        editor = mPrefs.edit();
        setView(root);
        setNonEditView();
        getSP();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEdit();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDone();
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCalc();
            }
        });
        image25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_mode) click25edit();
                else click25input();

            }
        });
        image20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_mode) click20edit();
                else click20input();
            }
        });
        image15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_mode) click15edit();
                else click15input();
            }
        });
        image10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_mode) click10edit();
                else click10input();
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_mode) click5edit();
                else click5input();
            }
        });
        image2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_mode) click2_5edit();
                else click2_5input();
            }
        });
        image1_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_mode) click1_25edit();
                else click1_25input();
            }
        });

        return root;
    }

    private void setView(View root) {
        edit = root.findViewById(R.id.button_edit);
        done = root.findViewById(R.id.button_done);
        weight = root.findViewById(R.id.weight);
        calc = root.findViewById(R.id.calc);
        in25 = root.findViewById(R.id.in25kg);
        in20 = root.findViewById(R.id.in20kg);
        in15 = root.findViewById(R.id.in15kg);
        in10 = root.findViewById(R.id.in10kg);
        in5 = root.findViewById(R.id.in5kg);
        in2_5 = root.findViewById(R.id.in2_5kg);
        in1_25 = root.findViewById(R.id.in1_25kg);
        enter_plates_text = root.findViewById(R.id.enter_plates_text);

        count25 = root.findViewById(R.id.edit25kg);
        count20 = root.findViewById(R.id.edit20kg);
        count15 = root.findViewById(R.id.edit15kg);
        count10 = root.findViewById(R.id.edit10kg);
        count5 = root.findViewById(R.id.edit5kg);
        count2_5 = root.findViewById(R.id.edit2_5kg);
        count1_25 = root.findViewById(R.id.edit1_25kg);

        image25 = root.findViewById(R.id.imageView_25kg);
        image20 = root.findViewById(R.id.imageView_20kg);
        image15 = root.findViewById(R.id.imageView_15kg);
        image10 = root.findViewById(R.id.imageView_10kg);
        image5 = root.findViewById(R.id.imageView_5kg);
        image2_5 = root.findViewById(R.id.imageView_2_5kg);
        image1_25 = root.findViewById(R.id.imageView_1_25kg);

        image25.setClickable(true);
        image20.setClickable(true);
        image15.setClickable(true);
        image10.setClickable(true);
        image5.setClickable(true);
        image2_5.setClickable(true);
        image1_25.setClickable(true);
    }

    private void setEditView() {
        done.setVisibility(View.VISIBLE);
        count25.setVisibility(View.VISIBLE);
        count20.setVisibility(View.VISIBLE);
        count15.setVisibility(View.VISIBLE);
        count10.setVisibility(View.VISIBLE);
        count5.setVisibility(View.VISIBLE);
        count2_5.setVisibility(View.VISIBLE);
        count1_25.setVisibility(View.VISIBLE);
        enter_plates_text.setVisibility(View.VISIBLE);

        clearEditText(count25);
        clearEditText(count20);
        clearEditText(count15);
        clearEditText(count10);
        clearEditText(count5);
        clearEditText(count2_5);
        clearEditText(count1_25);

        clearEditText(in25);
        clearEditText(in20);
        clearEditText(in15);
        clearEditText(in10);
        clearEditText(in5);
        clearEditText(in2_5);
        clearEditText(in1_25);

        edit.setVisibility(View.INVISIBLE);
        in25.setVisibility(View.INVISIBLE);
        in20.setVisibility(View.INVISIBLE);
        in15.setVisibility(View.INVISIBLE);
        in10.setVisibility(View.INVISIBLE);
        in5.setVisibility(View.INVISIBLE);
        in2_5.setVisibility(View.INVISIBLE);
        in1_25.setVisibility(View.INVISIBLE);
        calc.setVisibility(View.INVISIBLE);
        weight.setVisibility(View.INVISIBLE);


        count25.setHint(Integer.toString(count25_int));
        count20.setHint(Integer.toString(count20_int));
        count15.setHint(Integer.toString(count15_int));
        count10.setHint(Integer.toString(count10_int));
        count5.setHint(Integer.toString(count10_int));
        count2_5.setHint(Integer.toString(count2_5_int));
        count1_25.setHint(Integer.toString(count1_25_int));

        /*
        image25.setClickable(true);
        image20.setClickable(true);
        image15.setClickable(true);
        image10.setClickable(true);
        image5.setClickable(true);
        image2_5.setClickable(true);
        image1_25.setClickable(true);
         */
    }

    private void setNonEditView() {

        done.setVisibility(View.INVISIBLE);
        count25.setVisibility(View.INVISIBLE);
        count20.setVisibility(View.INVISIBLE);
        count15.setVisibility(View.INVISIBLE);
        count10.setVisibility(View.INVISIBLE);
        count5.setVisibility(View.INVISIBLE);
        count2_5.setVisibility(View.INVISIBLE);
        count1_25.setVisibility(View.INVISIBLE);
        enter_plates_text.setVisibility(View.INVISIBLE);

        edit.setVisibility(View.VISIBLE);
        in25.setVisibility(View.VISIBLE);
        in20.setVisibility(View.VISIBLE);
        in15.setVisibility(View.VISIBLE);
        in10.setVisibility(View.VISIBLE);
        in5.setVisibility(View.VISIBLE);
        in2_5.setVisibility(View.VISIBLE);
        in1_25.setVisibility(View.VISIBLE);
        calc.setVisibility(View.VISIBLE);
        weight.setVisibility(View.VISIBLE);
        /*
        image25.setClickable(false);
        image20.setClickable(false);
        image15.setClickable(false);
        image10.setClickable(false);
        image5.setClickable(false);
        image2_5.setClickable(false);
        image1_25.setClickable(false);
         */
    }

    private void clearEditText(EditText et) {
        if (et.getText().toString().trim().length() > 0) {
            et.getText().clear();
        }
    }

    private void click25edit() {
        count25_int++;
        if (count25_int > 10) {
            count25_int = 0;
        }
        count25.setText(Integer.toString(count25_int));
    }

    private void click20edit() {
        count20_int++;
        if (count20_int > 10) {
            count20_int = 0;
        }
        count20.setText(Integer.toString(count20_int));
    }

    private void click15edit() {
        count15_int++;
        if (count15_int > 10) {
            count15_int = 0;
        }
        count15.setText(Integer.toString(count15_int));
    }

    private void click10edit() {
        count10_int++;
        if (count10_int > 10) {
            count10_int = 0;
        }
        count10.setText(Integer.toString(count10_int));
    }

    private void click5edit() {
        count5_int++;
        if (count5_int > 10) {
            count5_int = 0;
        }
        count5.setText(Integer.toString(count5_int));
    }

    private void click2_5edit() {
        count2_5_int++;
        if (count2_5_int > 10) {
            count2_5_int = 0;
        }
        count2_5.setText(Integer.toString(count2_5_int));
    }

    private void click1_25edit() {
        count1_25_int++;
        if (count1_25_int > 10) {
            count1_25_int = 0;
        }
        count1_25.setText(Integer.toString(count1_25_int));
    }

    private void click25input() {
        in25_int++;
        if (in25_int > (count25_int/2)) {
            in25_int = 0;
        }
        in25.setText(Integer.toString(in25_int));
    }

    private void click20input() {
        in20_int++;
        if (in20_int > count20_int/2) {
            in20_int = 0;
        }
        in20.setText(Integer.toString(in20_int));
    }

    private void click15input() {
        in15_int++;
        if (in15_int > (count15_int/2)) {
            in15_int = 0;
        }
        in15.setText(Integer.toString(in15_int));
    }

    private void click10input() {
        in10_int++;
        if (in10_int > count10_int/2) {
            in10_int = 0;
        }
        in10.setText(Integer.toString(in10_int));
    }

    private void click5input() {
        in5_int++;
        if (in5_int > (count5_int/2)) {
            in5_int = 0;
        }
        in5.setText(Integer.toString(in5_int));
    }

    private void click2_5input() {
        in2_5_int++;
        if (in2_5_int > (count2_5_int/2)) {
            in2_5_int = 0;
        }
        in2_5.setText(Integer.toString(in2_5_int));
    }

    private void click1_25input() {
        in1_25_int++;
        if (in1_25_int > (count1_25_int/2)) {
            in1_25_int = 0;
        }
        in1_25.setText(Integer.toString(in1_25_int));
    }

    private void clickEdit() {
        edit_mode = true;
        getSP();
        setEditView();
        printToScreen("Edit your plates");
        calc.setClickable(false);
        weight.setEnabled(false);
    }

    private void clickDone() {
        edit_mode = false;
        getValuesFromEditText(); //get the values from editText and put them to int vars.
        setSP();
        setNonEditView();
        calc.setClickable(true);
        weight.setEnabled(true);
        printToScreen("Edit Done!");
    }

    private void clickCalc() {
        hideKeyboard();
        getSP();
        double weight_on_side = in25_int * 25 + in20_int * 20 + in15_int * 15 + in10_int * 10 + in5_int * 5 + in2_5_int * 2.5 + in1_25_int * 1.25;
        double weight_double = weight_on_side * 2 + 20;
        String weight_str = String.format("%.1f", weight_double);
        weight.setText(weight_str);
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
        if (prev_toast != null) {
            prev_toast.cancel();
        }
        hideKeyboard();
        Toast toast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        prev_toast = toast;
        toast.show();

    }

    static double roundToNearest2Point5(double n) {
        return Math.round(n * 0.4) / 0.4;
    }


    private void setSP() {
        editor.putInt("25", count25_int);
        editor.putInt("20", count20_int);
        editor.putInt("15", count15_int);
        editor.putInt("10", count10_int);
        editor.putInt("5", count5_int);
        editor.putInt("2.5", count2_5_int);
        editor.putInt("1.25", count1_25_int);
        editor.apply();
    }

    private void getSP() {
        count25_int = mPrefs.getInt("25", 0);
        count20_int = mPrefs.getInt("20", 0);
        count15_int = mPrefs.getInt("15", 0);
        count10_int = mPrefs.getInt("10", 0);
        count5_int = mPrefs.getInt("5", 0);
        count2_5_int = mPrefs.getInt("2.5", 0);
        count1_25_int = mPrefs.getInt("1.25", 0);
    }

    private void getValuesFromEditText() {
        count25_int = getValueFromET(count25_int, count25);
        count20_int = getValueFromET(count20_int, count20);
        count15_int = getValueFromET(count15_int, count15);
        count10_int = getValueFromET(count10_int, count10);
        count5_int = getValueFromET(count5_int, count5);
        count2_5_int = getValueFromET(count2_5_int, count2_5);
        count1_25_int = getValueFromET(count1_25_int, count1_25);
    }

    private int getValueFromET(int prev, EditText editText) {
        String temp_str = editText.getText().toString().trim();
        if (temp_str.equals("")) {
            return prev;
        }
        return Integer.parseInt(temp_str);
    }
}
