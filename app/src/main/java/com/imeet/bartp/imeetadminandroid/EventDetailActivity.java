package com.imeet.bartp.imeetadminandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventDetailActivity extends AppCompatActivity {

    //Global variables
    String data;

    //IO elements
    EditText edt_eventName;
    EditText edt_eventDescription;
    EditText edt_eventDate;
    EditText edt_startTime;
    EditText edt_endTime;

    Button btn_addEvent;

    //Firebase
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);
        setTitle("Evenement toevoegen");

        //INTENT RECEIVE
        Intent intent = getIntent();
        data = intent.getStringExtra(VisitorActivity.EXTRA_MESSAGE);

        //DEFENITION VARIABLES
        //IO elements
        edt_eventName = findViewById(R.id.edt_EventName);
        edt_eventDescription = findViewById(R.id.edt_EventDescription);
        edt_eventDate = findViewById(R.id.edt_EventDate);
        edt_startTime = findViewById(R.id.edt_StartTime);
        edt_endTime = findViewById(R.id.edt_EndTime);

        btn_addEvent = findViewById(R.id.btn_AddEvent);

        //FIREBASE CONNECTION AND LISTENERS
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Event");
    }

    public void createEvent(View view) {
        String eventname = edt_eventName.getText().toString();
        String eventdescription = edt_eventDescription.getText().toString();
        String eventdate = edt_eventDate.getText().toString();
        String eventstarttime = edt_startTime.getText().toString();
        String eventendtime = edt_endTime.getText().toString();
        String key = null;

        Event event = new Event(eventname,eventdescription,eventdate,eventstarttime,eventendtime,key);

        mDatabaseReference.push() //Use this method to create unique id of comment
                .setValue(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventDetailActivity.this, "Toegevoegd", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
