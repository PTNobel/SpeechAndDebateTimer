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
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by parth on 7/17/16.
 */
public class StoreEventFormats {
    Set<String> setOfEvents;
    LinkedHashMap<String, EventAlertFormat> eventToFormatMap = new LinkedHashMap<>();
    Context context;
    SharedPreferences events;

    public StoreEventFormats(Context context) {
        this.context = context;
        loadEventFormats();
    }

    private void loadEventFormats() {
        events = context.getSharedPreferences("setOfEvents", 0);
        this.setOfEvents = events.getStringSet("setOfEvents", new HashSet<String>());

        if (setOfEvents.isEmpty()) {
            buildDefaults();
            loadEventFormats();
        }

        for (String event: setOfEvents) {
            EventAlertFormat eventFormat = new EventAlertFormat();
            SharedPreferences eventPrefs = context.getSharedPreferences(event, 0);


            eventFormat.setName(event);
            eventFormat.setLenCountDown(eventPrefs.getInt("LenCountDown", 0));
            eventFormat.setSpeechLen(eventPrefs.getInt("SpeechLen", 0));
            eventFormat.setQuestionLength(eventPrefs.getInt("QuestionLength", 0));

            String[] items = eventPrefs.getString("WarningPoints", "0").split(",");

            int[] warningPoints = new int[items.length];

            for (int i = 0; i < items.length; i++) {
                warningPoints[i] = Integer.parseInt(items[i]);
            }

            eventFormat.setWarningPoints(warningPoints);
            Log.d("StoreEventFormats", eventFormat.getName());
            this.eventToFormatMap.put(event, eventFormat);

        }
    }

    private void buildDefaults() {
        Set<String> defaultSetOfEvents = new HashSet<>();

        defaultSetOfEvents.add("Congress");
        SharedPreferences congressPrefs = context.getSharedPreferences("Congress", 0);
        SharedPreferences.Editor congressEdit = congressPrefs.edit();
        congressEdit.putInt("LenCountDown", 10);
        congressEdit.putInt("SpeechLen", 180);
        congressEdit.putInt("QuestionLength", 8);
        congressEdit.putString("WarningPoints", "60,120,150");
        congressEdit.apply();

        defaultSetOfEvents.add("Parli 7");
        SharedPreferences parli7Prefs = context.getSharedPreferences("Parli 7", 0);
        SharedPreferences.Editor parli7Edit = parli7Prefs.edit();
        parli7Edit.putInt("LenCountDown", 10);
        parli7Edit.putInt("SpeechLen", 420);
        parli7Edit.putInt("QuestionLength", 0);
        parli7Edit.putString("WarningPoints", "60,120,180,240,300,360,390");
        parli7Edit.apply();

        SharedPreferences.Editor editorOfEventsList = this.events.edit();
        editorOfEventsList.putStringSet("setOfEvents", defaultSetOfEvents);
        editorOfEventsList.apply();

    }

    private void saveEventFormats(String eventName, int speechLen, int lenCountDown, int questionLength, int[] warningPoints) {
        loadEventFormats();

        setOfEvents.add(eventName);

        SharedPreferences eventPrefs = context.getSharedPreferences(eventName, 0);
        SharedPreferences.Editor eventEdit = eventPrefs.edit();

        eventEdit.putInt("SpeechLen", speechLen);
        eventEdit.putInt("LenCountDown", lenCountDown);
        eventEdit.putInt("QuestionLength", 0);

        String warningString = "";

        for (int point: warningPoints) {
            warningString += Integer.toString(point) + ",";
        }
        warningString = warningString.substring(0, warningString.length()-1); // Remove last comma

        eventEdit.putString("WarningPoints", warningString);

        eventEdit.apply();
    }

    public Set<String> getEventNames() {
        return setOfEvents;
    }

    public EventAlertFormat getEventAlertFormat(String event) {
        return eventToFormatMap.get(event);
    }

}
