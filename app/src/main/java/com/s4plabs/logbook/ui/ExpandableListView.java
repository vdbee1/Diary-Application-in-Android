package com.s4plabs.logbook.ui;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.s4plabs.logbook.R;
import com.s4plabs.logbook.db.DBContract;
import com.s4plabs.logbook.db.DBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ExpandableListView extends Activity {

    ExpandableListAdapter listAdapter;
    android.widget.ExpandableListView expListView;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();


    protected void getAllLogs(){
        Cursor cursor ;
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "Select * from " + DBContract.DayLogs.TABLE_NAME;

        cursor = db.rawQuery(query, null);

        formatLogs(cursor);
    }

    protected void formatLogs(Cursor cursor){
        if(cursor != null){
            listDataHeader.clear();
            listDataChild.clear();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                int date = cursor.getInt(cursor.getColumnIndex(DBContract.DayLogs.COLUMN_NAME_ID));
                String date1 = Integer.toString(date);
                String formatted_date = date1.substring(0, 2)+"/"+date1.substring(2, 4)+"/"+date1.substring(4);
                String log = cursor.getString(cursor.getColumnIndex(DBContract.DayLogs.COLUMN_NAME_LOG));
                ArrayList<String> logList = new ArrayList<>();
                logList.add(log);
                listDataHeader.add(formatted_date);
                listDataChild.put(formatted_date, logList);

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_list_view);
        expListView = (android.widget.ExpandableListView) findViewById(R.id.lvExp);

        getAllLogs();


        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);


        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

}

