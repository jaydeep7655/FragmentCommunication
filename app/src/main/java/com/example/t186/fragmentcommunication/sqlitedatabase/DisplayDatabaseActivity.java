package com.example.t186.fragmentcommunication.sqlitedatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.t186.fragmentcommunication.R;

public class DisplayDatabaseActivity extends AppCompatActivity {
    DisplayDatabaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_database);
        activity = this;
    }
}
