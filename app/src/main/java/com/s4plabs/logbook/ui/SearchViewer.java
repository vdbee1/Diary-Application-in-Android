package com.s4plabs.logbook.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.s4plabs.logbook.R;
import com.s4plabs.logbook.db.DBContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class SearchViewer extends Activity {
    TextToSpeech tts;
    protected String log;
    EditText search_main;
    TextView searchDateView;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        search_main = (EditText) findViewById(R.id.searchMain);
        searchDateView = (TextView) findViewById(R.id.searchDateView);
        formattedDate = getIntent().getStringExtra("formatted_date");
        Cursor cursor = Editor.searchLogs(formattedDate, getApplicationContext());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy");

        if (cursor != null) {
            cursor.moveToFirst();
            log = cursor.getString(cursor.getColumnIndex(DBContract.DayLogs.COLUMN_NAME_LOG));
            search_main.setText(log);
            try {
                searchDateView.setText(format.format(dateFormat.parse(formattedDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            searchDateView.setText("No Log Found   :(");
        }
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int Status) {
                if (tts != null) {
                    tts.setLanguage(Locale.getDefault());
                    tts.setPitch(1.0f);

                } else
                    Toast.makeText(getApplicationContext(), "no device", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void speakout() {
        tts.speak(search_main.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_speak) {
            speakout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
