package com.imeet.bartp.imeetadminandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventFillerActivity extends AppCompatActivity {

    //DECLARATION
    //Global variables
    String eventKey;
    String visitorKey;
    String eventvisitorKey;

    Event selectedEvent;
    Visitor selectedVisitor;
    EventVisitor selectedEventVisitor;

    //IO elements
    RecyclerView rcv_eventvisitors;

    //Firebase
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference eventDatabaseReference;
    DatabaseReference visitorDatabaseReference;
    DatabaseReference eventVisitorDatabaseReference;

    //Recycleadapter
    FirebaseRecyclerOptions<EventVisitor> options;
    FirebaseRecyclerAdapter<EventVisitor,EventVisitorRecyclerViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventfiller);
        setTitle("Bezoekers van");

        //INTENT RECEIVE
        Intent intent = getIntent();
        eventKey = intent.getStringExtra(VisitorActivity.EXTRA_MESSAGE);

        //DEFENITION VARIABLES
        //IO elements
        rcv_eventvisitors = (RecyclerView)findViewById(R.id.rcv_EventVisitors);
        rcv_eventvisitors.setLayoutManager(new LinearLayoutManager(this));

        //FIREBASE CONNECTION AND LISTENERS
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        eventDatabaseReference = mFirebaseDatabase.getReference("Event");
        visitorDatabaseReference = mFirebaseDatabase.getReference("Visitor");
        eventVisitorDatabaseReference = mFirebaseDatabase.getReference("EventVisitor");

        eventDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getEventObject(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        eventVisitorDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refreshRecycleView();
    }

    //Get the object of the eventKey sent by the previous activity
    private void getEventObject(DataSnapshot dataSnapshot) {

        //Arrays
        ArrayList<Event> mEventArray;
        mEventArray = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Event event = new Event();
            event.setName(ds.getValue(Event.class).getName());
            event.setDescription(ds.getValue(Event.class).getDescription());
            event.setDate(ds.getValue(Event.class).getDate());
            event.setStarttime(ds.getValue(Event.class).getStarttime());
            event.setEndtime(ds.getValue(Event.class).getEndtime());
            event.setKey(ds.getKey());

            mEventArray.add(event);
        }

        for (Event event : mEventArray) {
            if (event.getKey().equals(eventKey)) {
                selectedEvent = event;
                setTitle("Bezoekers van " + selectedEvent.getName());
            }
        }
    }

    //Get the object of the visitorKey that is given
    private Visitor getVisitorObject(DataSnapshot dataSnapshot, String key) {

        //Arrays
        ArrayList<Visitor> mVisitorArray;
        mVisitorArray = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Visitor visitor = new Visitor();
            visitor.setFirst_name(ds.getValue(Visitor.class).getFirst_name());
            visitor.setLast_name(ds.getValue(Visitor.class).getLast_name());
            visitor.setStreet(ds.getValue(Visitor.class).getStreet());
            visitor.setHousenr(ds.getValue(Visitor.class).getHousenr());
            visitor.setPostal(ds.getValue(Visitor.class).getPostal());
            visitor.setPhonenr(ds.getValue(Visitor.class).getPhonenr());
            visitor.setComments(ds.getValue(Visitor.class).getComments());
            visitor.setKey(ds.getKey());

            mVisitorArray.add(visitor);
        }

        for (Visitor visitor : mVisitorArray) {
            if (visitor.getKey().equals(key)) {
                selectedVisitor = visitor;
            }
        }
        return selectedVisitor;
    }

    @Override
    protected void onStop() {
        if (adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshRecycleView();

    }

    private void refreshRecycleView() {
        options =
                new FirebaseRecyclerOptions.Builder<EventVisitor>()
                        .setQuery(eventVisitorDatabaseReference,EventVisitor.class)
                        .build();

        adapter=
                new FirebaseRecyclerAdapter<EventVisitor,EventVisitorRecyclerViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull EventVisitorRecyclerViewHolder holder, int position, @NonNull final EventVisitor eventvisitor) {
                        String temp = "";
                        visitorDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (eventvisitor.getEventid().equals(eventKey)) {
                                    final String temp = getVisitorObject(dataSnapshot, eventvisitor.getVisitorid()).getFirst_name();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        if (eventvisitor.getEventid().equals(eventKey)) {
                            holder.txt_name.setText(temp);
                        }

                        /*holder.setiItemClickListener(new IItemClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                selectedEventVisitor = eventvisitor;
                                eventvisitorKey = getSnapshots().getSnapshot(position).getKey();
                                Log.d("Key Item","" + eventvisitorKey);

                                openEventFiller(eventvisitorKey);
                            }
                        });*/
                    }

                    @NonNull
                    @Override
                    public EventVisitorRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.eventvisitor_item,parent,false);
                        return new EventVisitorRecyclerViewHolder(itemView);
                    }
                };

        adapter.startListening();
        rcv_eventvisitors.setAdapter(adapter);
    }
}
