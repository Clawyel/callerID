package com.example.andy.tutorialspoint;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class aracDuzenleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String duzenlenecekID;
    public String duzenlenecekPlaka;
    public String duzenlenecekMarka;
    public EditText myPlaka;
    public EditText myMarka;
    public Button myGuncelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arac_duzenle_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        duzenlenecekID = extras.getString("id");
        duzenlenecekPlaka = extras.getString("plaka");
        duzenlenecekMarka = extras.getString("marka");
        myPlaka = (EditText)findViewById(R.id.tPlaka) ;
        myPlaka.setText(duzenlenecekPlaka);
        myMarka = (EditText)findViewById(R.id.tMarka) ;
        myMarka.setText(duzenlenecekMarka);
        myGuncelle = (Button)findViewById(R.id.guncelle);
        myGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cekPlaka = myPlaka.getText().toString();
                final String cekMarka = myMarka.getText().toString();
                new aracDuzenleAssync(aracDuzenleActivity.this,duzenlenecekID,cekPlaka,cekMarka).execute("http://iskenderunveterinerklinigi.com/api/araclar/update.php");
                Intent i = new Intent(aracDuzenleActivity.this, araclarActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.aramalar) {
            // Handle the camera action
            Intent i = new Intent(aracDuzenleActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.aracGonderimleri) {
            Intent i = new Intent(aracDuzenleActivity.this, aracGonderimleriListesi.class);
            startActivity(i);
        } else if (id == R.id.musteriler) {
            Intent i = new Intent(aracDuzenleActivity.this, musteriler.class);
            startActivity(i);
        } else if (id == R.id.soforler) {
            Intent i = new Intent(aracDuzenleActivity.this, soforlerActivity.class);
            startActivity(i);
        } else if (id == R.id.araclar) {
            Intent i = new Intent(aracDuzenleActivity.this, araclarActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
