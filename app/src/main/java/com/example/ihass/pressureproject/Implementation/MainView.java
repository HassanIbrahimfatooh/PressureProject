package com.example.ihass.pressureproject.Implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ihass.pressureproject.Classes.Measurement;
import com.example.ihass.pressureproject.Classes.MyAdapter;
import com.example.ihass.pressureproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toast toast;

    List<Measurement> measurelist = new ArrayList<>();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    // Data Base Instance
    DatabaseReference data_refrence = FirebaseDatabase.getInstance().getReference("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main View");
        setContentView(R.layout.activity_main_view);

        // Reading data from firebase then convert it into card views.
        Reading_Measures_From_FireBase();
        RecyclerViewMethod();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressLint("ShowToast")
    public void ShowToast(String message) {
        try {
            toast.getView().isShown();
            toast.setText(message);
        } catch (Exception e) {
            toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        }
        toast.show();
    }

    void Reading_Measures_From_FireBase() {
        String UserID = Objects.requireNonNull(FirebaseAuth.getInstance().getUid());
        data_refrence.child("Accounts").child(UserID).child("Measurements").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                // Looping on all instances from database
                Measurement Measure = dataSnapshot.getValue(Measurement.class);
                measurelist.add(Measure);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                Log.d(s, "onChildChanged:" + dataSnapshot.getKey());
                // A data item has changed
                Measurement Measure = dataSnapshot.getValue(Measurement.class);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void RecyclerViewMethod() {
        // Recycler View
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter
        mAdapter = new MyAdapter((ArrayList<Measurement>) measurelist);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void GoToMeasureFragment(View view) {
    }

    public void add_new_measure(View view) {
        ShowToast("Create New Measure");
        Intent NewMeasureIntent = new Intent(this, MeasureActivity.class);
        startActivity(NewMeasureIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped

        // Add code here to update the UI based on the item selected
        // For example, swap UI fragments here
        int id = menuItem.getItemId();

        if (id == R.id.nav_camera) {
            ShowToast("Nav Bar clicked");
        } else if (id == R.id.nav_gallery) {
            ShowToast("Gallery clicked");
        } else if (id == R.id.nav_slideshow) {
            ShowToast("Slide Show clicked");
        } else if (id == R.id.nav_manage) {
            ShowToast("Manage clicked");
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_view, menu);
        return true;
    }

}
