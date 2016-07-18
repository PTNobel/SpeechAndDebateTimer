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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Set;

public class ListOfEventsActivity extends AppCompatActivity {
    public final static String EXTRA_TIME_NAME = "io.github.ptnobel.speechanddebatetimerthing.TIME_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listview = (ListView) findViewById(R.id.listOfFormats);

        final StoreEventFormats storeEventFormats = new StoreEventFormats(this);
        Set<String> setOfValues = storeEventFormats.getEventNames();
        String[] values = setOfValues.toArray(new String[setOfValues.size()]);


        final TimeFormatArrayAdapter adapter = new TimeFormatArrayAdapter(this, values, storeEventFormats);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), Timer.class);
                intent.putExtra(EXTRA_TIME_NAME, item);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateTimeFormatActivity.class);
                startActivity(intent);
            }
        });
    }

    // @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), Timer.class);
        intent.putExtra(EXTRA_TIME_NAME, position);
        startActivity(intent);
    }
}
