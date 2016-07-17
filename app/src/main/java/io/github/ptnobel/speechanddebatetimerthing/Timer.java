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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

public class Timer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        RelativeLayout touch = (RelativeLayout) findViewById(R.id.touch);
        final Chronometer mainTimer =  (Chronometer) findViewById(R.id.mainTimer);
        final Button startStopButton = (Button) findViewById(R.id.startStopButton);
        final Button resetButton = (Button) findViewById(R.id.resetButton);

        final AlertFormat eventAlertFormat = new AlertFormat(fromUser(), mainTimer);

        mainTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                eventAlertFormat.doAction();
            }
        });

        startStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eventAlertFormat.toggleChronometer(startStopButton, resetButton);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eventAlertFormat.resetChronometer(startStopButton);
            }
        });
    }

    public EventAlertFormat fromUser() {
        EventAlertFormat output = new EventAlertFormat();

        // setContentView(R.layout.activity_timer);

        return output;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
