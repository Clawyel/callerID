package com.example.andy.tutorialspoint;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG ="";
    public ArrayList<HashMap<String, String>> contactList;
    private List<String> list = new ArrayList<String>();
    public String[][] dizi;
    public boolean durum;
    public String gNo;
    SwipeRefreshLayout yenile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        yenile = (SwipeRefreshLayout)findViewById(R.id.yenile);
        yenile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE))
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }
        else
        {
            //hiç bişi yapma
        }
        new aramalarAdapter(this).execute("http://iskenderunveterinerklinigi.com/api/aramalar/read_day.php");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case 1:
            {
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this,"İzin verildi", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this,"İzin verilmedi", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        if(requestCode == 101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhoneNumber(this.gNo);
            }
            else
            {
                Log.e(TAG, "Permission not Granted");
            }
        }
    }
    public void setList(ArrayList<HashMap<String, String>> contactList) {
        List<String> listmenu = new ArrayList<String>();
        this.contactList = contactList;
        this.dizi = new String[this.contactList.size()][8];
        String s1;
        for (int i=0;i<this.contactList.size();i++)
        {
            s1="";
            HashMap<String, String> hashmap= this.contactList.get(i);
            String Adi = hashmap.get("adi");
            if(Adi != null)
            {
                s1 += Adi;
                Log.d("adi",Adi);
            }
            String Soyadi = hashmap.get("soyadi");
            if(Soyadi != null)
            {
                s1 += " ";
                s1 += Soyadi;
            }
            s1 += "\n";
            s1 += hashmap.get("numara");
            s1+="\n";
            s1+= hashmap.get("saat");
            s1+=" | ";
            s1+= hashmap.get("tarih");
            s1+="\n";
            String idGelen = hashmap.get("id");
            String musteriIdGelen = hashmap.get("musteriID");
            String numara = hashmap.get("numara");
            String saat = hashmap.get("saat");
            String tarih = hashmap.get("tarih");
            String numaraID = hashmap.get("numaraID");

            this.dizi[i][0] = idGelen;
            this.dizi[i][1] = musteriIdGelen;
            this.dizi[i][2] = saat;
            this.dizi[i][3] = tarih;
            this.dizi[i][4] = numara;
            this.dizi[i][5] = Adi;
            this.dizi[i][6] = Soyadi;
            this.dizi[i][7] = numaraID;
            listmenu.add(s1);
        }

        //Toast.makeText(getActivity(),s1,Toast.LENGTH_SHORT).show();
        Log.d("gelen Liste:", String.valueOf(this.contactList));
        String[] menuItems = {"asdf","asdf"};
        final ListView listView = this.findViewById(R.id.mainList);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                listmenu
        );
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
        int a = (int) id;
                String arf = dizi[a][1];
                final String gidenNumara = dizi[a][4];
                gNo = gidenNumara;
                final String gidenAdSoyad = dizi[a][5]+dizi[a][6];
                final String numaraID = dizi[a][7];
                Log.d("sınıf", arf);
                if(arf != "null")
                {
                    final String GidenID = arf;
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                    alertDialog.setTitle("Araç Gönderimi");

                    //alertDialog.setMessage("Müşteriyi Kaydedin Ya Da Araç Gönderin");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Araç Gönderimi Yap", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(MainActivity.this, aracGonderimleri.class);
                            Bundle extras = new Bundle();
                            extras.putString("musteriID",GidenID);
                            extras.putString("numara",gidenNumara);
                            extras.putString("adSoyad",gidenAdSoyad);
                            extras.putString("numaraID",numaraID);
                            intent.putExtras(extras);
                            startActivity(intent);
                            //...

                        } });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Kişiyi Ara", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            callPhoneNumber(gidenNumara);


                        }});


                    alertDialog.show();
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                    alertDialog.setTitle("Araç Gönderimi");

                    alertDialog.setMessage("Müşteri Kayıtlı Değil");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Müşteriyi Kaydet", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(MainActivity.this, musteriyiKaydet.class);
                            Bundle extras = new Bundle();
                            extras.putString("numara",gidenNumara);
                            intent.putExtras(extras);
                            startActivity(intent);

                        } });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "İşlemi İptal Et", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            //...

                        }});


                    alertDialog.show();
                }

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
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.aracGonderimleri) {
            Intent i = new Intent(MainActivity.this, aracGonderimleriListesi.class);
            startActivity(i);
        } else if (id == R.id.musteriler) {
            Intent i = new Intent(MainActivity.this, musteriler.class);
            startActivity(i);
        }  else if (id == R.id.soforler) {
            Intent i = new Intent(MainActivity.this, soforlerActivity.class);
            startActivity(i);
        } else if (id == R.id.araclar) {
            Intent i = new Intent(MainActivity.this, araclarActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void yenile()
    {
        new aramalarAdapter(this).execute("http://iskenderunveterinerklinigi.com/api/aramalar/read_day.php");

        startActivity(getIntent());
    }
    public void callPhoneNumber(String numAra)
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + numAra));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + numAra));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
