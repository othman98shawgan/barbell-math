package com.app.barbellmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText weight;
    Button calc;
    ImageButton edit, done;
    TextView res25, res20, res15, res10, res5, res2_5, res1_25, enter_plates_text;
    EditText count25, count20, count15, count10, count5, count2_5, count1_25;
    int count25_int = 0, count20_int = 0, count15_int = 0, count10_int = 0, count5_int = 0, count2_5_int = 0, count1_25_int = 0;
    Toast prev_toast = null;

    SharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPrefs = getSharedPreferences("Weights", Context.MODE_PRIVATE);

        edit = findViewById(R.id.button_edit);
        done = findViewById(R.id.button_done);
        weight = findViewById(R.id.weight);
        calc = findViewById(R.id.calc);
        res25 = findViewById(R.id.res25kg);
        res20 = findViewById(R.id.res20kg);
        res15 = findViewById(R.id.res15kg);
        res10 = findViewById(R.id.res10kg);
        res5 = findViewById(R.id.res5kg);
        res2_5 = findViewById(R.id.res2_5kg);
        res1_25 = findViewById(R.id.res1_25kg);
        enter_plates_text = findViewById(R.id.enter_plates_text);

        count25 = findViewById(R.id.edit25kg);
        count20 = findViewById(R.id.edit20kg);
        count15 = findViewById(R.id.edit15kg);
        count10 = findViewById(R.id.edit10kg);
        count5 = findViewById(R.id.edit5kg);
        count2_5 = findViewById(R.id.edit2_5kg);
        count1_25 = findViewById(R.id.edit1_25kg);

        setNonEditView();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSP();
                setEditView();
                printToScreen("Edit your plates");
                calc.setClickable(false);
                weight.setEnabled(false);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromEditText(); //get the values from editText and put them to int vars.
                setSP();
                setNonEditView();
                calc.setClickable(true);
                weight.setEnabled(true);
                printToScreen("Edit Done!");
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();
                getSP();
                String weight_str = weight.getText().toString().trim();
                if (weight_str.equals("")) {
                    //Toast.makeText(MainActivity.this, "Calculation Error", Toast.LENGTH_LONG).show();
                    printToScreen("Calculation Error");
                    return;
                }
                double weight_double = Double.parseDouble(weight_str);
                if (weight_double < 20) {
                    printToScreen("Not Enough Weight!");
                    return;
                }
                double weight_without_bar = weight_double - 20;
                double weight_on_side = weight_without_bar / 2;
                String weight_on_side_str = Double.toString(weight_on_side);
                int res25_int = 0, res20_int = 0, res15_int = 0, res10_int = 0, res5_int = 0, res2_5_int = 0, res1_25_int = 0;
                int count25_int_cp = count25_int, count20_int_cp = count20_int, count15_int_cp = count15_int,
                        count10_int_cp = count10_int, count5_int_cp = count5_int, count2_5_int_cp = count2_5_int, count1_25_int_cp = count1_25_int;

                while (true) {
                    if (weight_on_side >= 25 && count25_int_cp > 0) {
                        res25_int++;
                        weight_on_side -= 25;
                        count25_int_cp -= 2;
                    } else if (weight_on_side >= 20 && count20_int_cp > 0) {
                        res20_int++;
                        weight_on_side -= 20;
                        count20_int_cp -= 2;
                    } else if (weight_on_side >= 15 && count15_int_cp > 0) {
                        res15_int++;
                        weight_on_side -= 15;
                        count15_int_cp -= 2;
                    } else if (weight_on_side >= 10 && count10_int_cp > 0) {
                        res10_int++;
                        weight_on_side -= 10;
                        count10_int_cp -= 2;
                    } else if (weight_on_side >= 5 && count5_int_cp > 0) {
                        res5_int++;
                        weight_on_side -= 5;
                        count5_int_cp -= 2;
                    } else if (weight_on_side >= 2.5 && count2_5_int_cp > 0) {
                        res2_5_int++;
                        weight_on_side -= 2.5;
                        count2_5_int_cp -= 2;
                    } else if (weight_on_side >= 1.25 && count1_25_int_cp > 0) {
                        res1_25_int++;
                        weight_on_side -= 1.25;
                        count1_25_int_cp -= 2;
                    } else {
                        break;
                    }
                }
                if (weight_on_side > 0) {
                    printToScreen("GO BUY MORE PLATES !!!");
                } else {
                    res25.setText(String.valueOf(res25_int));
                    res20.setText(String.valueOf(res20_int));
                    res15.setText(String.valueOf(res15_int));
                    res10.setText(String.valueOf(res10_int));
                    res5.setText(String.valueOf(res5_int));
                    res2_5.setText(String.valueOf(res2_5_int));
                    res1_25.setText(String.valueOf(res1_25_int));
                    printToScreen("Load "+weight_on_side_str+"KG on each side");
                }
            }
        });
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void printToScreen(String str) {
        if (prev_toast != null) {
            prev_toast.cancel();
        }
        hideKeyboard();
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        prev_toast = toast;
        toast.show();
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

        edit.setVisibility(View.INVISIBLE);
        res25.setVisibility(View.INVISIBLE);
        res20.setVisibility(View.INVISIBLE);
        res15.setVisibility(View.INVISIBLE);
        res10.setVisibility(View.INVISIBLE);
        res5.setVisibility(View.INVISIBLE);
        res2_5.setVisibility(View.INVISIBLE);
        res1_25.setVisibility(View.INVISIBLE);
        calc.setVisibility(View.INVISIBLE);
        weight.setVisibility(View.INVISIBLE);


        count25.setHint(Integer.toString(count25_int));
        count20.setHint(Integer.toString(count20_int));
        count15.setHint(Integer.toString(count15_int));
        count10.setHint(Integer.toString(count10_int));
        count5.setHint(Integer.toString(count10_int));
        count2_5.setHint(Integer.toString(count2_5_int));
        count1_25.setHint(Integer.toString(count1_25_int));
    }

    private void clearEditText(EditText et) {
        if (et.getText().toString().trim().length() > 0) {
            et.getText().clear();
        }
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
        res25.setVisibility(View.VISIBLE);
        res20.setVisibility(View.VISIBLE);
        res15.setVisibility(View.VISIBLE);
        res10.setVisibility(View.VISIBLE);
        res5.setVisibility(View.VISIBLE);
        res2_5.setVisibility(View.VISIBLE);
        res1_25.setVisibility(View.VISIBLE);
        calc.setVisibility(View.VISIBLE);
        weight.setVisibility(View.VISIBLE);
    }

    private void setSP() {
        SharedPreferences.Editor editor = mPrefs.edit();
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