package com.imeet.bartp.imeetadminandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Hoofdmenu");
    }

    // wanneer de knop evenement wordt ingedrukt
    public void btnEventClick(View view) {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

    //wanneer de knop bezoeker wordt ingedrukt
    public void btnVisitorClick(View view) {
        Intent intent = new Intent(this, VisitorActivity.class);
        startActivity(intent);
    }

}
