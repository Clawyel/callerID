package com.example.andy.tutorialspoint;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class musteriyiKaydet extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public ArrayList<HashMap<String, String>> contactList;

    public EditText adresText;
    public EditText konumText;
    public EditText adText;
    public EditText soyadText;
    public String adresAl;
    public String adAl;
    public String konumAl;
    public String soyadAl;
    public int aracGonderimSayisi;
    public String numara;
    public static String musteriID;
    private String URLline = "http://iskenderunveterinerklinigi.com/api/musteriler/create.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musteriyikaydet);
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
        numara = extras.getString("numara");
        TextView textView2 = (TextView)findViewById(R.id.numara);
        textView2.setText(numara);
        adresText = (EditText)findViewById(R.id.adres);
        konumText = (EditText)findViewById(R.id.konum);
        adText = (EditText)findViewById(R.id.ad);
        soyadText = (EditText)findViewById(R.id.soyad);
        aracGonderimSayisi = 0;
        Button konumcopy = (Button)findViewById(R.id.konumcopy) ;
        konumcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Konum Kopyalandı", konumText.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });
        Button btn = (Button)findViewById(R.id.kaydet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adresAl = adresText.getText().toString();
                adAl = adText.getText().toString();
                soyadAl = soyadText.getText().toString();
                konumAl = konumText.getText().toString();
                Log.d("ad: ",adAl);
                Log.d("soyad: ",soyadAl);
                Log.d("adres: ",adresAl);
                Log.d("konum: ",konumAl);

                getir2(adAl,soyadAl);

                AlertDialog alertDialog = new AlertDialog.Builder(musteriyiKaydet.this).create();

                alertDialog.setTitle("Müşteri Kaydı");

                alertDialog.setMessage("Müşteri Kaydı Başarılı ");





                alertDialog.show();


                Intent intcontent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intcontent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intcontent
                        .putExtra(ContactsContract.Intents.Insert.PHONE,numara)
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                        .putExtra(ContactsContract.Intents.Insert.NAME,adAl+" "+soyadAl);

                //Log.d("sonuc-alindi",musteriID);
               // Intent i = new Intent(musteriyiKaydet.this, MainActivity.class);
                startActivity(intcontent);
                /*String textSTR ="arac: "+secilenAracID+"sofor: "+secilenSoforID+"saat: "+time+"tarih: "+bugun.format(tarih)+"numara: "+NumaraID+"adres: "+adresAl;
                makeText(aracGonderimleri.this,"araç gönder : " + textSTR, LENGTH_SHORT).show();*/
            }
        });

    }


    private void getir(final String adAl, final String soyadAl){

        final String Adi = adAl;
        final String Soyadi = soyadAl;
        final String AracGonderimSayisi = "1";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(musteriyiKaydet.this,response, Toast.LENGTH_LONG).show();
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(musteriyiKaydet.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("Adi",adAl);
                params.put("Soyadi",soyadAl);
                params.put("AracGonderimSayisi","1");
                Log.d("params-alindi", String.valueOf(params));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void parseData(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

                JSONArray dataArray = jsonObject.getJSONArray("records");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    musteriyiKaydet.this.musteriID = dataobj.getString("id");
                }
                Log.d("Musteri-alindi",musteriID);
/*
                Intent intent = new Intent(MainActivity.this,HobbyActivity.class);
                startActivity(intent);*/
            new musteriAdresKaydi(musteriID,adresAl,musteriyiKaydet.this).execute("http://iskenderunveterinerklinigi.com/api/adresler/create.php");
            new musteriKonumKaydi(musteriID,konumAl,musteriyiKaydet.this).execute("http://iskenderunveterinerklinigi.com/api/konumlar/create.php");
            new musteriNumaraKaydi(musteriID,numara,musteriyiKaydet.this).execute("http://iskenderunveterinerklinigi.com/api/numaralar/create.php");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getir2(String ad, String soyad)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("Adi", ad);
        postParam.put("Soyadi", soyad);
        postParam.put("AracGonderimSayisi", "1");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URLline, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //musteriID = response.toString();

                        try
                        {
                            contactList = new ArrayList<>();
                            JSONObject jsonObj = new JSONObject(response.toString());

                            // Getting JSON Array node
                            JSONArray contacts = jsonObj.getJSONArray("records");

                            // looping through All Contacts
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                musteriID = c.getString("id");

                            }

                            //jo.getString("records");
                        }catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }



                        new musteriAdresKaydi(musteriID,adresAl,musteriyiKaydet.this).execute("http://iskenderunveterinerklinigi.com/api/adresler/create.php");
                        new musteriKonumKaydi(musteriID,konumAl,musteriyiKaydet.this).execute("http://iskenderunveterinerklinigi.com/api/konumlar/create.php");
                        new musteriNumaraKaydi(musteriID,numara,musteriyiKaydet.this).execute("http://iskenderunveterinerklinigi.com/api/numaralar/create.php");

                        Log.d("son-alindi", musteriID);
                       // msgResponse.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               // VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        // Adding request to request queue
        queue.add(jsonObjReq);

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
            Intent i = new Intent(musteriyiKaydet.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.aracGonderimleri) {
            Intent i = new Intent(musteriyiKaydet.this, aracGonderimleriListesi.class);
            startActivity(i);
        } else if (id == R.id.musteriler) {
            Intent i = new Intent(musteriyiKaydet.this, musteriler.class);
            startActivity(i);
        }  else if (id == R.id.soforler) {
            Intent i = new Intent(musteriyiKaydet.this, soforlerActivity.class);
            startActivity(i);
        }
        else if (id == R.id.araclar) {
            Intent i = new Intent(musteriyiKaydet.this, araclarActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
