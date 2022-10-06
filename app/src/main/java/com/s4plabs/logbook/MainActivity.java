package com.s4plabs.logbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.s4plabs.logbook.ui.Editor;

public class MainActivity extends Activity
{ @Override
    protected void onCreate(Bundle savedInstanceState)
{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), Editor.class);
        startActivity(intent);
        finish();
    }
}
