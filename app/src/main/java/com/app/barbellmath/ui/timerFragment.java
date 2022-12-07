package com.app.barbellmath.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.barbellmath.R;

import java.util.Locale;

public class timerFragment extends Fragment {

    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning, mEditing = false;
    long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long prevTimer1 = START_TIME_IN_MILLIS;
    private ImageButton mButtonReset, mButtonPause,mButtonStart;
    private ImageButton mButtonEdit, mButtonDone;
    private EditText mEditText1;
    Toast prev_toast = null;
    private ProgressBar progressBarCircle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_timer, container, false);

        progressBarCircle = root.findViewById(R.id.progressBarCircle);
        mTextViewCountDown = root.findViewById(R.id.text_view_countdown);
        mButtonReset = root.findViewById(R.id.button_reset);
        mButtonPause = root.findViewById(R.id.button_pause);
        mButtonStart = root.findViewById(R.id.button_start);
        mEditText1 = root.findViewById(R.id.text_edit_countdown);
        mButtonEdit = root.findViewById(R.id.button_edit);
        mButtonDone = root.findViewById(R.id.button_done);
        mButtonPause.setVisibility(View.INVISIBLE);

        setProgressBarValues();


        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        mTextViewCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTimer();
                updateCountDownText();

            }
        });

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDone();
                updateCountDownText();
            }
        });


        updateCountDownText();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                setProgressBarValues();
                mTimerRunning = false;
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 500, 20, 500, 100, 500, 20, 500, 100, 500, 20, 500, 100};
                v.vibrate(pattern, -1);
            }
        }.start();
        mTimerRunning = true;
        mButtonStart.setVisibility(View.INVISIBLE);
        mButtonPause.setVisibility(View.VISIBLE);
    }

    private void pauseTimer() {
        if (mTimerRunning) {
            mCountDownTimer.cancel();
            mTimerRunning = false;
        }
        updateCountDownText();
        mButtonStart.setVisibility(View.VISIBLE);
        mButtonPause.setVisibility(View.INVISIBLE);
    }

    private void resetTimer() {
        if (mTimerRunning) {
            pauseTimer();
        }
        mTimeLeftInMillis = prevTimer1;
        setProgressBarValues();
        updateCountDownText();
    }

    private void setProgressBarValues() {
        progressBarCircle.setMax((int) mTimeLeftInMillis / 1000);
        progressBarCircle.setProgress((int) mTimeLeftInMillis / 1000);
    }


    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void editDone() {
        String time1_str = mEditText1.getText().toString().trim();
        if (time1_str.equals("")) {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            prevTimer1 = mTimeLeftInMillis;
        } else {
            if (mTimeLeftInMillis / 1000 != stringToMilli(time1_str) / 1000) {
                mTimeLeftInMillis = stringToMilli(time1_str);
                prevTimer1 = mTimeLeftInMillis;
            }
        }
        mEditText1.setVisibility(View.INVISIBLE);
        mTextViewCountDown.setVisibility(View.VISIBLE);
        updateCountDownText();
        setProgressBarValues();
        mButtonDone.setVisibility(View.INVISIBLE);
        mButtonEdit.setVisibility(View.VISIBLE);

    }

    private void editTimer() {
        printToScreen("Tap on a timer to edit");
        pauseTimer();
        mEditText1.setVisibility(View.VISIBLE);
        mTextViewCountDown.setVisibility(View.INVISIBLE);
        mEditing = !mEditing;
        mButtonDone.setVisibility(View.VISIBLE);
        mButtonEdit.setVisibility(View.INVISIBLE);
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        clearEditText(mEditText1);
        mEditText1.setHint(timeLeftFormatted);

    }

    private void clearEditText(EditText et) {
        if (et.getText().toString().trim().length() > 0) {
            et.getText().clear();
        }
    }


    private int stringToMilli(String time_str) {
        int index = time_str.indexOf(":");
        int min = Integer.valueOf(time_str.substring(0, index));
        int sec = Integer.valueOf(time_str.substring(index + 1));
        return (min * 60000 + sec * 1000);
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
}