package com.example.andy.tutorialspoint;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class araclarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView;
    private araclarActivityAdapter araclarActivityAdapter;
    public ArrayList<HashMap<String, String>> contactList;
    List<String> listmenu;
    private List<araclarActivityData> araclarActivityDataList = new ArrayList<>();
    public ImageButton btnSil;
    public ImageButton btnDuzenle;
    public EditText etaracplakasi;
    public EditText etaracmarkasi;
    public Button btnKaydet;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.araclar_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        btnKaydet = (Button)findViewById(R.id.aracEkle);
        etaracmarkasi = (EditText)findViewById(R.id.aracmarkasi);
        etaracplakasi = (EditText)findViewById(R.id.aracplakasi);
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String eklenecekMarka = etaracmarkasi.getText().toString();
                final String eklenecekPlaka = etaracplakasi.getText().toString();
                new aracKaydiAssync(araclarActivity.this,eklenecekPlaka,eklenecekMarka).execute("http://iskenderunveterinerklinigi.com/api/araclar/create.php");
                Intent i = new Intent(araclarActivity.this, araclarActivity.class);
                startActivity(i);
            }
        });
        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);




        new araclarAdapterForCard(this).execute("http://iskenderunveterinerklinigi.com/api/araclar/read.php");




        //StudentDataPrepare();


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void StudentDataPrepare() {

     /*   Log.d("liste-control", String.valueOf(this.soforlerActivityDataList));
        Collections.sort(soforlerActivityDataList, new Comparator<soforlerActivityData>() {
                    @Override
                    public int compare(soforlerActivityData o1, soforlerActivityData o2) {
                        return o1.ad.compareTo(o2.ad);
            }
        });*/
    }
    public void setListAraclar(ArrayList<HashMap<String, String>> contactList) {
        this.contactList = contactList;
        araclarActivityData data = new araclarActivityData("","","");
        for (int i=0;i<this.contactList.size();i++)
        {

            HashMap<String, String> hashmap= this.contactList.get(i);
            data = new araclarActivityData(hashmap.get("id"),hashmap.get("plaka"),hashmap.get("marka"));
            araclarActivityDataList.add(data);

        }
        Log.d("cikan-liste", String.valueOf(araclarActivityDataList));
        //this.soforlerActivityDataList = soforlerActivityDataList;
        Collections.sort(araclarActivityDataList, new Comparator<araclarActivityData>() {
            @Override
            public int compare(araclarActivityData o1, araclarActivityData o2) {
                return o1.plaka.compareTo(o2.plaka);
            }
        });
        araclarActivityAdapter = new araclarActivityAdapter(araclarActivityDataList,getApplicationContext());
        recyclerView.setAdapter(araclarActivityAdapter);
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
            Intent i = new Intent(araclarActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.aracGonderimleri) {
            Intent i = new Intent(araclarActivity.this, aracGonderimleriListesi.class);
            startActivity(i);
        } else if (id == R.id.musteriler) {
            Intent i = new Intent(araclarActivity.this, musteriler.class);
            startActivity(i);
        }  else if (id == R.id.soforler) {
            Intent i = new Intent(araclarActivity.this, soforlerActivity.class);
            startActivity(i);
        } else if (id == R.id.araclar) {
            Intent i = new Intent(araclarActivity.this, araclarActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
