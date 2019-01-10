package com.imeet.bartp.imeetadminandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class VisitorDetailActivity extends AppCompatActivity {

    //DECLARATION
    //Tag
    private static final String TAG = "VisitorDetailActivity";

    //Global variables
    String data;
    Visitor selectedVisitor;

    //IO elements
    EditText edt_firstname;
    EditText edt_lastname;
    EditText edt_street;
    EditText edt_housenr;
    EditText edt_postal;
    EditText edt_phonenr;
    EditText edt_comments;

    Button btn_visitorDetail;

    //Firebase
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitordetail);

        //INTENT RECEIVE
        Intent intent = getIntent();
        data = intent.getStringExtra(VisitorActivity.EXTRA_MESSAGE);

        //DEFENITION VARIABLES
        //IO elements
        edt_firstname = findViewById(R.id.edt_Firstname);
        edt_lastname = findViewById(R.id.edt_Lastname);
        edt_street = findViewById(R.id.edt_Street);
        edt_housenr = findViewById(R.id.edt_Housenr);
        edt_postal = findViewById(R.id.edt_Postal);
        edt_phonenr = findViewById(R.id.edt_Phonenr);
        edt_comments = findViewById(R.id.edt_Comments);

        btn_visitorDetail = findViewById(R.id.btn_VisitorDetail);

        //FIREBASE CONNECTION AND LISTENERS
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Visitor");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //OTHER FEATURES
        //Set button and title text depending on activity function
        if (data != null) {
            btn_visitorDetail.setText("Bijwerken");
            setTitle("Info van");
        }
        else {
            btn_visitorDetail.setText("Toevoegen");
            setTitle("Nieuwe bezoeker toevoegen");
        }
    }

    private void showData(DataSnapshot dataSnapshot) {

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
            if (visitor.getKey().equals(data)) {
                edt_firstname.setText(visitor.getFirst_name());
                edt_lastname.setText(visitor.getLast_name());
                edt_street.setText(visitor.getStreet());
                edt_housenr.setText(visitor.getHousenr());
                edt_postal.setText(visitor.getPostal());
                edt_phonenr.setText(visitor.getPhonenr());
                edt_comments.setText(visitor.getComments());
                selectedVisitor = visitor;
                setTitle("Info van " + selectedVisitor.getFirst_name());
            }
        }
    }

    private void updateVisitor() {
        mDatabaseReference
                .child(selectedVisitor.getKey())
                .setValue(new Visitor(
                        edt_firstname.getText().toString()
                        ,edt_lastname.getText().toString()
                        ,edt_street.getText().toString()
                        ,edt_housenr.getText().toString()
                        ,edt_postal.getText().toString()
                        ,edt_phonenr.getText().toString()
                        ,edt_comments.getText().toString()
                        ,null
                ))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VisitorDetailActivity.this, "Bijgewerkt", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VisitorDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createVisitor() {
        String firstname = edt_firstname.getText().toString();
        String lastname = edt_lastname.getText().toString();
        String street = edt_street.getText().toString();
        String housenr = edt_housenr.getText().toString();
        String postal = edt_postal.getText().toString();
        String phonenr = edt_phonenr.getText().toString();
        String comments = edt_comments.getText().toString();
        String key = null;

        Visitor visitor = new Visitor(firstname,lastname,street,housenr,postal,phonenr,comments,key);

        mDatabaseReference.push() //Use this method to create unique id of comment
                .setValue(visitor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VisitorDetailActivity.this, "Toegevoegd", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VisitorDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void btnVisitorDetailClick(View view) {
        if (data != null) {
            updateVisitor();
        }
        else {
            createVisitor();
        }
    }
}
