package com.example.ihass.pressureproject.Implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
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
import com.example.ihass.pressureproject.Fragments.Heart_Activity;
import com.example.ihass.pressureproject.Fragments.Sugar_Activity;
import com.example.ihass.pressureproject.Fragments.testFrag;
import com.example.ihass.pressureproject.MainActivity;
import com.example.ihass.pressureproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    // Fabs
    ConstraintLayout transitionsContainer;
    FloatingActionButton add_new_action_fab, new_photo_fab, new_measure_fab;

    // Instances
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toast toast;

    // RecyclerView
    List<Measurement> measurelist = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    // Data Base Instance
    DatabaseReference data_refrence = FirebaseDatabase.getInstance().getReference();

    // Fire base Authentication
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("History");
        setContentView(R.layout.activity_main_view);

        // RecyclerView
        Reading_Measures_From_FireBase();
        RecyclerViewMethod();

        // Fabs
        transitionsContainer = findViewById(R.id.ConstraintLayout);
        add_new_action_fab = transitionsContainer.findViewById(R.id.add_new_action_fab);
        new_photo_fab = transitionsContainer.findViewById(R.id.new_photo_fab);
        new_measure_fab = transitionsContainer.findViewById(R.id.new_measure_fab);

        // Action bar
        mDrawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Nav bar
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    // This method is for getting back to the start layout if pressed back
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    public void add_new_action_measure(View view) {
        if (new_measure_fab.getVisibility() == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(transitionsContainer);
            new_photo_fab.setVisibility(View.GONE);
            new_measure_fab.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(transitionsContainer);
            new_photo_fab.setVisibility(View.VISIBLE);
            new_measure_fab.setVisibility(View.VISIBLE);
        }
    }

    public void new_measure_fab(View view) {
        ShowToast("Create New Measure");
        Intent NewMeasureIntent = new Intent(this, MeasureActivity.class);
        startActivity(NewMeasureIntent);
    }

    public void new_photo_fab(View view) {
        ShowToast("Taking new photo");
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped

        // Add code here to update the UI based on the item selected
        // For example, swap UI fragments here
        int id = menuItem.getItemId();

        if (id == R.id.nav_PressureMeasure) {
//            startActivity(new Intent(this, Pressure_Activity.class));
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new testFrag()).commit();
            ShowToast("nav_PressureMeasure");
        } else if (id == R.id.nav_SugarMeasure) {
            startActivity(new Intent(this, Sugar_Activity.class));
            ShowToast("nav_SugarMeasure");
        } else if (id == R.id.nav_HeartMeasure) {
            startActivity(new Intent(this, Heart_Activity.class));
            ShowToast("nav_HeartMeasure");
        } else if (id == R.id.nav_share) {
            ShowToast("nav_share");
        } else if (id == R.id.nav_send) {
            ShowToast("nav_send");
        } else if (id == R.id.nav_Logout) {
            ShowToast("nav_Logout");
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_view, menu);
        return true;
    }

}
