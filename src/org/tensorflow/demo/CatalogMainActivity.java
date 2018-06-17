package org.tensorflow.demo;


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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.tensorflow.demo.Interface.ItemClickListener;
import org.tensorflow.demo.ViewHolder.RoomViewHolder;
import org.tensorflow.demo.model.Rooms;

public class CatalogMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference roooms;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" "+ "Catalog");
        setSupportActionBar(toolbar);


        database = FirebaseDatabase.getInstance();
        roooms = database.getReference("rooms");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerrest);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadrest();
    }
    private void loadrest() {
        FirebaseRecyclerAdapter<Rooms,RoomViewHolder> adapter =  new FirebaseRecyclerAdapter<Rooms, RoomViewHolder>(Rooms.class,R.layout.room_item,RoomViewHolder.class,roooms) {
            @Override
            protected void populateViewHolder(RoomViewHolder viewHolder, Rooms model, int position) {
                viewHolder.txtroomnumber.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.roomimageview);
                final Rooms clickitem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongclick) {
                        Toast.makeText(CatalogMainActivity.this,""+clickitem.getName(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CatalogMainActivity.this,MonumentsActivity.class);
                        String roomnum= clickitem.getName();
                        intent.putExtra("rest name",roomnum);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }


  /*  @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}