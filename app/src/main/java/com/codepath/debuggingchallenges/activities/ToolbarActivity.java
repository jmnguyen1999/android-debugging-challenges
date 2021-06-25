package com.codepath.debuggingchallenges.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
//Original: import android.widget.Toolbar;                    // Step 1.)  Notice this is the wrong toolbar! import the androidx.appcompat.widget.Toolbar instead!
//Revised:
import androidx.appcompat.widget.Toolbar;

import com.codepath.debuggingchallenges.R;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Step 2.)  setActionBar() doesn't seem to like this new toolbar -> use setSupportActionBar() instead!
        //setActionBar(toolbar);
        setSupportActionBar(toolbar);


        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setText(R.string.hello);
    }
}
