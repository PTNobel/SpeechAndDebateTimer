package io.github.ptnobel.speechanddebatetimerthing;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

/**
 * Created by parth on 7/16/16.
 */
public class AlertFormat {
    int speechLen;
    int startCountDown;
    int lenCountDown;
    int[] warningPoints;
    int mostRecentWarning;
    Chronometer timer;
    boolean isTiming = false;
    long timeWhenPaused = 0;


    int neutralColor = Color.argb(100, 0, 0, 0);
    int warningColor = Color.MAGENTA;
    int overTimeColor = Color.RED;

    public AlertFormat(EventAlertFormat event, Chronometer timer){
        this.speechLen = event.getSpeechLen();
        this.startCountDown = event.getSpeechLen() - event.getLenCountDown();
        this.lenCountDown = event.getLenCountDown();
        this.warningPoints = event.getWarningPoints();
        this.timer = timer;
        this.timer.setTextColor(this.neutralColor);
        Log.d("AlertFormat", "AlertFormat was initialized. speechLen: " + this.speechLen);
    }

    private int totalSecondsElapsed(){
        int secondsElapsed = 0;
        String chronoText = timer.getText().toString();
        String array[] = chronoText.split(":");

        if (array.length == 2) {
            secondsElapsed = Integer.parseInt(array[0]) * 60
                    + Integer.parseInt(array[1]);
        } else if (array.length == 3) {
            secondsElapsed = Integer.parseInt(array[0]) * 60 * 60
                    + Integer.parseInt(array[1]) * 60
                    + Integer.parseInt(array[2]);
        }

        return secondsElapsed;
    }

    public void doAction()
    {
        int currentTime = totalSecondsElapsed();
        int currentTextColor = timer.getCurrentTextColor();

        if (currentTextColor == this.warningColor)
        {
            if (currentTime > this.mostRecentWarning + 2)
            {
                timer.setTextColor(this.neutralColor);
            }
        } else if (currentTextColor == this.neutralColor)
        {
            for (int time : this.warningPoints){
                if (currentTime + 2 == time)
                {
                    timer.setTextColor(this.warningColor);
                    this.mostRecentWarning = time;
                }
            }
        }
        if (currentTime >= startCountDown && currentTime < speechLen)
        {
                timer.setTextColor(Color.argb(100, 255, 0, 0));
        } else if (currentTime > speechLen) {
            timer.setTextColor(Color.RED);
        }
    }

    private void pauseChronometer() {
        timeWhenPaused = this.timer.getBase() - SystemClock.elapsedRealtime();
        isTiming = false;
        this.timer.stop();
    }

    private void startChronometer() {
        isTiming = true;
        this.timer.setBase(SystemClock.elapsedRealtime() + timeWhenPaused);
        this.timer.start();
    }

    public void toggleChronometer(Button startStopButton, Button resetButton) {
        if (isTiming) {
            pauseChronometer();
            startStopButton.setText("Resume Timer");
            resetButton.setText("Reset Timer");
        } else
        {
            Log.d("ElseBlock", "Inside the Else Block");
            startChronometer();
            startStopButton.setText("Stop Timer");
            resetButton.setText("Pause to Reset");
        }
    }

    public void resetChronometer(Button startStopButton) {
        if (isTiming) {

        } else {
            this.timeWhenPaused = 0;
            this.timer.setBase(SystemClock.elapsedRealtime());
            startStopButton.setText("Start Timer");
        }
    }
}
