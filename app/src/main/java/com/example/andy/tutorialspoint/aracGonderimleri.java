package com.example.andy.tutorialspoint;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class aracGonderimleri extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public ArrayList<HashMap<String, String>> contactList;
    public ArrayList<HashMap<String, String>> contactListAraclar;
    public ArrayList<HashMap<String, String>> adresList;
    private Spinner spinnerSoforler, spinnerAraclar;
    public TextView adresText;
    public int secileAracDiziIndis;
    public int secilenSoforDiziIndis;
    public String[][] soforDizi;
    public String[][] aracDizi;
    public String secilenAracID;
    public String secilenSoforID;
    public String NumaraID;
    public String adresAl;
    public String musteriID;
    private String URLline = "http://iskenderunveterinerklinigi.com/api/aracGonderimleri/create.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aracgonderimleri);
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
        final String numara = extras.getString("numara");
        String adSoyad = extras.getString("adSoyad");
        String musteriID = intent.getStringExtra("musteriID");
        this.NumaraID  = intent.getStringExtra("numaraID");
        TextView textView = (TextView)findViewById(R.id.adSoyad);
        textView.setText(adSoyad);
        TextView textView2 = (TextView)findViewById(R.id.numara);
        textView2.setText(numara);
        adresText = (TextView)findViewById(R.id.adres);
        spinnerAraclar = (Spinner) findViewById(R.id.araclar);
        new soforlerAdapter(this).execute("http://iskenderunveterinerklinigi.com/api/soforler/read.php");
        new araclarAdapter(this).execute("http://iskenderunveterinerklinigi.com/api/araclar/read.php");
        new tekAdresAdapter(this).execute("http://iskenderunveterinerklinigi.com/api/adresler/read_musterinin_tum_adresleri.php?musteriId="+musteriID);
        Button btn = (Button)findViewById(R.id.aracGonder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adresAl = adresText.getText().toString();
                Date tarih = new Date();
                SimpleDateFormat bugun = new SimpleDateFormat("yyyy/MM/dd");
                bugun.setTimeZone(TimeZone.getTimeZone("GMT+03:00"));
                TimeZone tz = TimeZone.getTimeZone("GMT+03:00");
                Calendar c = Calendar.getInstance(tz);
                String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                        String.format("%02d" , c.get(Calendar.MINUTE))+":"+
                        String.format("%02d" , c.get(Calendar.SECOND))+":"+
                        String.format("%03d" , c.get(Calendar.MILLISECOND));
                final String numara2 = numara;
                final String mesaj = "Aracınız Yola Çıkmıştır, Bizi Tercih Ettiğiniz İçin Teşekkür Ederiz. Şoför Adı: "+soforDizi[secilenSoforDiziIndis][1]+", Araç Bilgileri: "+aracDizi[secileAracDiziIndis][1]+"  "+aracDizi[secileAracDiziIndis][2];


                new aracGonder(secilenSoforID,secilenAracID,time,bugun.format(tarih),NumaraID,adresAl).execute("http://iskenderunveterinerklinigi.com/api/aracGonderimi/create.php");
                //musteri id al
                //arac gonderim sayısını arttır

                new sendSMS(numara,mesaj).execute("http://iskenderunveterinerklinigi.com/api/smsGonder/create.php");

                Intent i = new Intent(aracGonderimleri.this, MainActivity.class);
                startActivity(i);
                /*String textSTR ="arac: "+secilenAracID+"sofor: "+secilenSoforID+"saat: "+time+"tarih: "+bugun.format(tarih)+"numara: "+NumaraID+"adres: "+adresAl;
                makeText(aracGonderimleri.this,"araç gönder : " + textSTR, LENGTH_SHORT).show();*/
            }
        });
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
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
    public void setListAraclar(ArrayList<HashMap<String, String>> contactListAraclar) {
        List<String> listmenuAraclar = new ArrayList<String>();
        this.contactListAraclar = contactListAraclar;
        aracDizi = new String[this.contactListAraclar.size()][3];
        String s1;
        for (int i=0;i<this.contactListAraclar.size();i++)
        {
            s1="";
            HashMap<String, String> hashmap= this.contactListAraclar.get(i);
            String aracMarka = hashmap.get("marka");
            String aracPlaka = hashmap.get("plaka");
            String aracID = hashmap.get("id");
            s1+= aracMarka;
            s1 += "  |  ";
            s1+= aracPlaka;
            s1+="\n";
            aracDizi[i][0] = aracID;
            aracDizi[i][1] = aracMarka;
            aracDizi[i][2] = aracPlaka;
            listmenuAraclar.add(s1);
        }



        ArrayAdapter<String> adapterAraclar = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listmenuAraclar);

        spinnerAraclar.setAdapter(adapterAraclar);

    }
    public void setListSoforler(ArrayList<HashMap<String, String>> contactList) {
        List<String> listmenu = new ArrayList<String>();
        this.contactList = contactList;
        soforDizi = new String[this.contactList.size()][2];
        String s1;
        for (int i=0;i<this.contactList.size();i++)
        {
            s1="";
            HashMap<String, String> hashmap= this.contactList.get(i);
            String adiGelen = hashmap.get("ad");
            String idGelen = hashmap.get("id");
            s1+= adiGelen;
            s1+="\n";
            soforDizi[i][0] = idGelen;
            soforDizi[i][1] = adiGelen;


            listmenu.add(s1);
        }


        spinnerSoforler = (Spinner) findViewById(R.id.soforler);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listmenu);
        spinnerSoforler.setAdapter(adapter);

    }
    public void setAdres(ArrayList<HashMap<String, String>> adresList)
    {

        this.adresList = adresList;

        String s1;
        for (int i=0;i<this.adresList.size();i++)
        {
            s1="";
            HashMap<String, String> hashmap= this.adresList.get(i);
            String adiGelen = hashmap.get("adres");
            String idGelen = hashmap.get("id");
            s1+= adiGelen;
            s1+="\n";
            Log.d("adres",s1);
            adresText.setText(s1);

        }
    }
    public void addListenerOnSpinnerItemSelection() {
        spinnerAraclar = (Spinner) findViewById(R.id.araclar);
        spinnerAraclar.setOnItemSelectedListener(new CustomOnItemSelectedListener(this));
        spinnerSoforler = (Spinner) findViewById(R.id.soforler);
        spinnerSoforler.setOnItemSelectedListener(new CustomOnItemSelectedListenerS(this));
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinnerAraclar = (Spinner) findViewById(R.id.araclar);
        spinnerSoforler = (Spinner) findViewById(R.id.soforler);


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
            Intent i = new Intent(aracGonderimleri.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.aracGonderimleri) {
            Intent i = new Intent(aracGonderimleri.this, aracGonderimleriListesi.class);
            startActivity(i);
        } else if (id == R.id.musteriler) {
            Intent i = new Intent(aracGonderimleri.this, musteriler.class);
            startActivity(i);
        }
        else if (id == R.id.soforler) {
            Intent i = new Intent(aracGonderimleri.this, soforlerActivity.class);
            startActivity(i);
        }
        else if (id == R.id.araclar) {
            Intent i = new Intent(aracGonderimleri.this, araclarActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class updateData extends AsyncTask<String, String, String> {
        public String numara;
        public String mesaj;
        /*public updateData(String numara,String mesaj)
        {
            this.mesaj = mesaj;
            this.numara = numara;
        }*/
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {
                URL url;
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                } else {
                    InputStream err = conn.getErrorStream();
                }
                return "Done";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }
}
