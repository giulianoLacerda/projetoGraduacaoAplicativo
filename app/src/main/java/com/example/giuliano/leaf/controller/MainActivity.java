package com.example.giuliano.leaf.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuliano.leaf.ItemAdapter;
import com.example.giuliano.leaf.R;
import com.example.giuliano.leaf.api.Client;
import com.example.giuliano.leaf.api.Service;
import com.example.giuliano.leaf.model.Classification;
import com.example.giuliano.leaf.model.Item;
import com.example.giuliano.leaf.model.ItemResponse;
import com.example.giuliano.leaf.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    TextView Disconnected;
    private Item item;
    ProgressDialog pd;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeContainer;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager mViewPager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra("USER");

        initViews();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("Leaf");

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                loadJSON();
                Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set name and email drawer layout
        View hView =  navigationView.getHeaderView(0);
        TextView nav_userName = (TextView)hView.findViewById(R.id.textViewName);
        TextView nav_userEmail = (TextView)hView.findViewById(R.id.textView);
        nav_userName.setText(user.getDisplayName());
        nav_userEmail.setText(user.getEmail());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,CameraActivity.class);
                it.putExtra("USER",user);
                startActivity(it);

            }
        });
    }


    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON(){
        Disconnected = (TextView) findViewById(R.id.disconnected);
        try{
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<List<Classification>> call = apiService.getClassificacoes(user.getCookie());
            //Toast.makeText(MainActivity.this, "Sucesso:", Toast.LENGTH_SHORT).show();
            call.enqueue(new Callback<List<Classification>>() {
                @Override
                public void onResponse(Call<List<Classification>> call, Response<List<Classification>> response) {
                    List<Classification> items = response.body();
                    //Toast.makeText(MainActivity.this, "Primeiro"+items.get(0).get_id()+"Imagem:"+items.get(0).getImageUrl(), Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<List<Classification>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Erro ao carregar dados!"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();

                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this,MonthsActivity.class);
            i.putExtra("USER",user);
            startActivity(i);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}