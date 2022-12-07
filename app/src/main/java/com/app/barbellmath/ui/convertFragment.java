package com.app.barbellmath.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.barbellmath.R;

import org.w3c.dom.Text;

import java.util.Locale;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class convertFragment extends Fragment {

    private static final int KGS_TO_LBS = 0;
    private static final int LBS_TO_KGS = 1;
    EditText kgs_to_lbs_in, lbs_to_kgs_in;
    TextView kgs_to_lbs_out, lbs_to_kgs_out;
    TextView lbs_is_tv, kgs_is_tv, kgs_tv, lbs_tv;
    ImageView lbs_to_kgs_box, kgs_to_lbs_box;
    Button to_lbs, to_kgs;
    Toast prev_toast = null;
    Switch mSwith;
    int switch_state = KGS_TO_LBS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_convert, container, false);

        setView(root);
        clearEditText(kgs_to_lbs_in);
        clearEditText(lbs_to_kgs_in);
        setStateKGSToLBS();
        to_kgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clacKGS();
            }
        });

        to_lbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clacLBS();
            }
        });

        mSwith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_state = LBS_TO_KGS;
                    setStateLBSoKGS();
                    clearEditText(lbs_to_kgs_in);
                    clearTextView(lbs_to_kgs_out);
                } else {
                    switch_state = KGS_TO_LBS;
                    setStateKGSToLBS();
                    clearEditText(kgs_to_lbs_in);
                    clearTextView(kgs_to_lbs_out);
                }
            }
        });

        return root;
    }

    private void setView(View root) {
        kgs_to_lbs_in = root.findViewById(R.id.kgs_to_lbs_edit_text);
        kgs_to_lbs_out = root.findViewById(R.id.lbs_out_text);
        lbs_to_kgs_out = root.findViewById(R.id.kgs_out_text);
        lbs_to_kgs_in = root.findViewById(R.id.lbs_to_kgs_edit_text);
        to_lbs = root.findViewById(R.id.kg_to_lbs_button);
        to_kgs = root.findViewById(R.id.lbs_to_kgs_button);
        mSwith = root.findViewById(R.id.switch1);
        lbs_is_tv = root.findViewById(R.id.lbs_is_text);
        kgs_is_tv = root.findViewById(R.id.kgs_is_text);
        kgs_tv = root.findViewById(R.id.kgs_text);
        lbs_tv = root.findViewById(R.id.lbs_text);
        kgs_to_lbs_box = root.findViewById(R.id.kg_to_lbs_box);
        lbs_to_kgs_box = root.findViewById(R.id.lbs_to_kg_box);
    }

    public void setStateKGSToLBS() {
        kgs_to_lbs_in.setVisibility(View.VISIBLE);
        kgs_to_lbs_out.setVisibility(View.VISIBLE);
        to_lbs.setVisibility(View.VISIBLE);
        kgs_is_tv.setVisibility(View.VISIBLE);
        lbs_tv.setVisibility(View.VISIBLE);
        kgs_to_lbs_box.setVisibility(View.VISIBLE);

        lbs_to_kgs_out.setVisibility(View.INVISIBLE);
        lbs_to_kgs_in.setVisibility(View.INVISIBLE);
        to_kgs.setVisibility(View.INVISIBLE);
        lbs_is_tv.setVisibility(View.INVISIBLE);
        kgs_tv.setVisibility(View.INVISIBLE);
        lbs_to_kgs_box.setVisibility(View.INVISIBLE);

        to_lbs.setClickable(true);
        to_kgs.setClickable(false);

    }

    public void setStateLBSoKGS() {
        kgs_to_lbs_in.setVisibility(View.INVISIBLE);
        kgs_to_lbs_out.setVisibility(View.INVISIBLE);
        to_lbs.setVisibility(View.INVISIBLE);
        kgs_is_tv.setVisibility(View.INVISIBLE);
        lbs_tv.setVisibility(View.INVISIBLE);
        kgs_to_lbs_box.setVisibility(View.INVISIBLE);

        lbs_to_kgs_out.setVisibility(View.VISIBLE);
        lbs_to_kgs_in.setVisibility(View.VISIBLE);
        to_kgs.setVisibility(View.VISIBLE);
        lbs_is_tv.setVisibility(View.VISIBLE);
        kgs_tv.setVisibility(View.VISIBLE);
        lbs_to_kgs_box.setVisibility(View.VISIBLE);

        to_lbs.setClickable(false);
        to_kgs.setClickable(true);
    }

    public void clacLBS() {
        String kgs_str = kgs_to_lbs_in.getText().toString().trim();
        if (kgs_str.equals("")) {
            //Toast.makeText(MainActivity.this, "Calculation Error", Toast.LENGTH_LONG).show();
            printToScreen("Calculation Error");
            return;
        }
        double kgs_double = Double.parseDouble(kgs_str);
        double lbs_double = kgs_double * 2.2;
        String ans_str = String.format("%.1f", (lbs_double));
        kgs_to_lbs_out.setText(String.valueOf(ans_str));
    }

    public void clacKGS() {
        String lbs_str = lbs_to_kgs_in.getText().toString().trim();
        if (lbs_str.equals("")) {
            //Toast.makeText(MainActivity.this, "Calculation Error", Toast.LENGTH_LONG).show();
            printToScreen("Calculation Error");
            return;
        }
        double lbs_double = Double.parseDouble(lbs_str);
        double kgs_double = lbs_double / 2.2;
        String ans_str = String.format("%.1f", (kgs_double));
        lbs_to_kgs_out.setText(String.valueOf(ans_str));


    }

    private void clearEditText(EditText et) {
        if (et.getText().toString().trim().length() > 0) {
            et.getText().clear();
        }
    }

    private void clearTextView(TextView tv) {
        tv.setText("_______");
    }


    public void printToScreen(String str) {
        if (prev_toast != null) {
            prev_toast.cancel();
        }
        Toast toast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        prev_toast = toast;
        toast.show();

    }

    static double roundToNearest2Point5(double n) {
        return Math.round(n * 0.4) / 0.4;
    }
}