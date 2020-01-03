package com.example.andy.tutorialspoint;



import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class aglAdapterForCard extends AsyncTask<String, String, String> {
    public List<String> str;
    private aracGonderimleriListesi aracGonderimleriListesi;
    public ArrayList<HashMap<String, String>> contactList;
    public String tmp;
    public aglAdapterForCard(aracGonderimleriListesi a)
    {
        this.aracGonderimleriListesi = a;
    }



    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader br = null;
        try {
            URL url = new URL(params[0]); // url adresi ilk parametredir
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String satir;
            String dosya = "";
            while ((satir = br.readLine()) != null) {
                Log.d("satir: ", satir);
                dosya += satir;

            }
            return dosya;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "hata";
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);

        //Log.d("onPostExecute: ",s);
        try
        {
            contactList = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(s);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("records");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                tmp = "";
                JSONObject c = contacts.getJSONObject(i);
                String id = c.getString("id");
                String jAd = c.getString("musteri_adi");
                String jSoyad = c.getString("musteri_soyadi");
                String jSoforAdi = c.getString("soforAdi");
                String jAdres = c.getString("Adres");
                String jAracPlaka = c.getString("aracPlaka");
                String jAracMarka = c.getString("aracMarka");
                String jSaat = c.getString("saat");
                String jTarih = c.getString("tarih");
                // tmp hash map for single contact
                HashMap<String, String> contact = new HashMap<>();

                // adding each child node to HashMap key => value
                contact.put("id", id);
                contact.put("musteriAdi", jAd);
                contact.put("musteriSoyadi", jSoyad);
                contact.put("soforAdi", jSoforAdi);
                contact.put("aracPlaka", jAracPlaka);
                contact.put("aracMarka", jAracMarka);
                contact.put("saat", jSaat);
                contact.put("tarih", jTarih);
                contact.put("adres", jAdres);
                // adding contact to contact list
                contactList.add(contact);
            }
            this.aracGonderimleriListesi.setListGonderimler(contactList);
            //jo.getString("records");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }



}

