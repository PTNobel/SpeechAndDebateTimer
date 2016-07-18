/*

    Copyright 2016 Parth Nobel

    This file is part of Speech and Debate Timer.

    Speech and Debate Timer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Speech and Debate Timer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Speech and Debate Timer.  If not, see <http://www.gnu.org/licenses/>.

 */


package io.github.ptnobel.speechanddebatetimerthing;

import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;

/**
 * Created by parth on 7/16/16.
 */
public class AlertFormat {
    int speechLen;
    int startCountDown;
    int lenCountDown;
    int[] warningPoints;
    int mostRecentWarning;
    int questionLength;
    Chronometer timer;
    boolean isTiming = false;
    long timeWhenPaused = 0;
    String nameOfTimerFormat;
    int questionEndWarning = 0;


    int neutralColor = Color.argb(100, 0, 0, 0);
    int warningColor = Color.MAGENTA;
    int overTimeColor = Color.RED;
    int questionColor = Color.argb(200, 200, 100, 100);

    public AlertFormat(EventAlertFormat event, Chronometer timer){
        this.speechLen = event.getSpeechLen();
        this.startCountDown = event.getSpeechLen() - event.getLenCountDown();
        this.lenCountDown = event.getLenCountDown();
        this.warningPoints = event.getWarningPoints();
        this.timer = timer;
        this.timer.setTextColor(this.neutralColor);
        this.nameOfTimerFormat = event.getName();
        this.questionLength = event.getQuestionLength();
        Log.d("AlertFormat", "AlertFormat was initialized. speechLen: " + this.speechLen);
    }

    private int totalSecondsElapsed() {
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

    public String getNameOfTimerFormat() {
        return this.nameOfTimerFormat;
    }

    public void doAction() {
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
            if (questionEndWarning != 0 && currentTime > questionEndWarning)
            {
                timer.setTextColor(this.questionColor);
            }
            for (int time : this.warningPoints){
                if (currentTime + 2 == time)
                {
                    timer.setTextColor(this.warningColor);
                    this.mostRecentWarning = time;
                }
            }
        } else if (currentTextColor == this.questionColor) {
            if (currentTime >= questionEndWarning + 10) {
                timer.setTextColor(this.neutralColor);
                this.questionEndWarning = 0;
            } else if (currentTime < questionEndWarning)
            {
                timer.setTextColor(this.neutralColor);
            }
        }
        if (currentTime >= startCountDown && currentTime < speechLen)
        {
                timer.setTextColor(Color.argb(100, 255, 0, 0));
        } else if (currentTime > speechLen) {
            timer.setTextColor(this.overTimeColor);
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
            startStopButton.setText("Pause Timer");
            resetButton.setText("Pause to Reset");
        }
    }

    public void resetChronometer(Button startStopButton) {
        if (!isTiming) {
            this.timeWhenPaused = 0;
            this.timer.setBase(SystemClock.elapsedRealtime());
            startStopButton.setText("Start Timer");
        }
    }

    public void questionStarted() {
        this.questionEndWarning = totalSecondsElapsed() + questionLength;
        Log.d("questionStarted", "questionEndWarning = " + this.questionEndWarning);
    }
}
