package com.imeet.bartp.imeetadminandroid;

import android.app.Activity;
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
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

    //string used to transfer data
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    //DECLARATION
    Button btn_AddEvent;
    RecyclerView rcv_Event;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Event> options;
    FirebaseRecyclerAdapter<Event,EventRecyclerViewHolder> adapter;

    //Global variables
    Event selectedEvent;
    String selectedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setTitle("Evenementen");

        //IO ELEMENTS
        btn_AddEvent = (Button)findViewById(R.id.btn_AddEvent);
        rcv_Event = (RecyclerView)findViewById(R.id.rcv_Event);
        rcv_Event.setLayoutManager(new LinearLayoutManager(this));

        //FIREBASE VARIABLES AND LISTENERS
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Event");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        displayComment();
    }

    //When the app is closed
    @Override
    protected void onStop() {
        if (adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }

    //When the app is resumed
    @Override
    public void onResume(){
        super.onResume();
        displayComment();

    }

    //Refresh recycleview
    private void displayComment() {
        options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(databaseReference,Event.class)
                        .build();

        adapter=
                new FirebaseRecyclerAdapter<Event,EventRecyclerViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull EventRecyclerViewHolder holder, int position, @NonNull final Event event) {
                        //Setting item fields
                        holder.txt_name.setText(event.getName());
                        holder.txt_date.setText(event.getDate());

                        //Clicking an recycleview item
                        holder.setiItemClickListener(new IItemClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                selectedEvent = event;
                                selectedKey = getSnapshots().getSnapshot(position).getKey();
                                Log.d("Key Item","" + selectedKey);

                                openEventFiller(selectedKey);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public EventRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.event_item,parent,false);
                        return new EventRecyclerViewHolder(itemView);
                    }
                };

        adapter.startListening();
        rcv_Event.setAdapter(adapter);
    }

    //When the evenement toevoegen button is clicked
    public void btnAddEventClick(View view) {
        Intent intent = new Intent(this, EventDetailActivity.class);
        startActivity(intent);
    }

    //When a recycleview item is clicked, sending key as extra String value;
    public void openEventFiller(String data) {
        Intent intent = new Intent(this, EventFillerActivity.class);
        intent.putExtra(EXTRA_MESSAGE, data);
        startActivity(intent);
    }
}
