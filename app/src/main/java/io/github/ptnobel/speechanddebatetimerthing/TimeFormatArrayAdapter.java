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

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by parth on 7/17/16.
 */
public class TimeFormatArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] values;
    private StoreEventFormats storeEventFormats;

    public TimeFormatArrayAdapter(Context context, String[] values, StoreEventFormats storeEventFormats) {
        super(context, -1, values);

        this.context = context;
        this.values = values;
        this.storeEventFormats = storeEventFormats;

        Log.d("TimeFormatArrayAdapter", Arrays.toString(values));

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_list_of_events_format, parent, false);
        TextView eventName = (TextView) rowView.findViewById(R.id.eventName);
        TextView eventDescription = (TextView) rowView.findViewById(R.id.eventDescription);

        Log.d("TimeFormatArrayAdapter", "In getView()");
        eventName.setText(values[position]);
        eventDescription.setText(buildEventDescription(values[position]));

       return rowView;
    }

    public String makeTimeString(int time) {
        String timeString = "";

        if (time >= 60 ) {
            timeString += Integer.toString(time/60) + "m";
        }

        if (time % 60 != 0) {
            timeString += " " + Integer.toString(time % 60) + "s";
        }

        return timeString;
    }

    private String buildEventDescription(String eventName) {
        String eventDescription = "";
        EventAlertFormat eventAlertFormat = storeEventFormats.getEventAlertFormat(eventName);
        String speechLen = eventAlertFormat.getName();
        Log.d("TimeFormatArrayAdapter", speechLen + "");
        eventDescription += "Speech: " + makeTimeString(eventAlertFormat.getSpeechLen());

        eventDescription += ", Warnings: ";

        for (int time: eventAlertFormat.getWarningPoints()) {
            eventDescription += makeTimeString(time) + ", ";
        }

        eventDescription += "Questions: ";

        if (eventAlertFormat.getQuestionLength() != 0) {
            eventDescription += makeTimeString(eventAlertFormat.getQuestionLength());
        } else {
            eventDescription += "No";
        }

        return eventDescription;
    }
}
