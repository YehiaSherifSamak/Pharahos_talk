package org.tensorflow.demo;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.tensorflow.demo.Interface.ItemClickListener;
import org.tensorflow.demo.ViewHolder.RoomViewHolder;
import org.tensorflow.demo.model.Rooms;

public class MonumentsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference monuments;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public static String x="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monuments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_monuments);
        x  = getIntent().getStringExtra("rest name");
        toolbar.setTitle(" "+ x + " ");
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance();
        monuments = database.getReference(x);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_monument);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_monument);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadroom1();
    }

    private void loadroom1() {
        FirebaseRecyclerAdapter<Rooms,RoomViewHolder> adapter =  new FirebaseRecyclerAdapter<Rooms, RoomViewHolder>(Rooms.class,R.layout.room_item,RoomViewHolder.class,monuments) {
            @Override
            protected void populateViewHolder(RoomViewHolder viewHolder, Rooms model, int position) {
                viewHolder.txtroomnumber.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.roomimageview);
                final Rooms clickitem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongclick) {
                        String  monimentName= clickitem.getName();
                        Toast.makeText(getApplicationContext(),""+clickitem.getName(),Toast.LENGTH_LONG).show();
                        Intent infoIntent = new Intent(MonumentsActivity.this, Info.class);
                        infoIntent.putExtra("Monument_name", monimentName);
                        infoIntent.putExtra("came_from", "catalog");
                        startActivity(infoIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_menu);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent  =new Intent(MonumentsActivity.this,CatalogMainActivity.class);
            startActivity(intent);
            super.onBackPressed();*/
        startActivity(new Intent(MonumentsActivity.this, MainMenu.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.monument, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_menu);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    }


