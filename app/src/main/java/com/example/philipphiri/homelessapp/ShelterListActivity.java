package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShelterListActivity extends AppCompatActivity {
    private Button logout;
    Dialog myDialog;

    ListView listViewShelters;
    List<Shelter> shelters;
    DatabaseReference databaseShelters;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        //ref of shelters node
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        //getting views
        listViewShelters = (ListView) findViewById(R.id.shelterListView);


        logout = findViewById(R.id.logoutButton);
        myDialog = new Dialog(this);
        //need a way so that when a specific shelter is clicked the popup will open corresponding data
        //right now there's no way to open popup

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShelterListActivity.this, WelcomePageActivity.class ));
            }
        });
    }

    protected void onStart() {
        super.onStart();
        databaseShelters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters = new ArrayList<>();
                Shelter e = new Shelter("address", "capacity", "latitude", "longitude",
                "phoneNumber", "restrictions", "shelterName", "specialNotes", "uniqueKey");
                shelters.add(e);
                //not going into for loop!
                for (DataSnapshot dsp: dataSnapshot.getChildren()) {
                    Map singleShelter = (Map) dsp.getValue();
                    Shelter s = new Shelter((String) singleShelter.get("Address"),(String) singleShelter.get("Capacity"), (String)singleShelter.get("Latitude "),
                    (String) singleShelter.get("Longitude "), (String)singleShelter.get("Phone Number"), (String) singleShelter.get("Restrictions"),
                    (String) singleShelter.get("Shelter Name"), (String)singleShelter.get("Special Notes"), (String)singleShelter.get("UniqueKey"));
                    shelters.add(s);
                }
                //creating adapter
                ShelterList shelterAdapter = new ShelterList(ShelterListActivity.this, shelters);
                //attaching adapter to the listview
                listViewShelters.setAdapter(shelterAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //this will do the retrieving -- website way ??
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //attaching value event listener
//        databaseShelters.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //shelters.clear(); //?clearing previous shelter list
//
//                //iterating through all the nodes
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    //getting shelter
//                    Shelter shelter = postSnapshot.getValue(Shelter.class);
//                    //adding shelter to the list
//                    shelters.add(shelter);
//                }
//                //creating adapter
//                ShelterList shelterAdapter = new ShelterList(ShelterListActivity.this, shelters);
//                //attaching adapter to the listview
//                listViewShelters.setAdapter(shelterAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    //for shelter details popup
//    private void ShowDetails(View v) {
//        TextView detailclose;
//        myDialog.setContentView(R.layout.shelter_detail);
//        detailclose = (TextView) myDialog.findViewById(R.id.detailclose);
//        detailclose.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//
//    }
}
