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

public class aramalarAdapter extends AsyncTask<String, String, String> {
    public List<String> str;
    private MainActivity classMainActivity;
    public ArrayList<HashMap<String, String>> contactList;
    public String tmp;
    public aramalarAdapter(MainActivity a)
    {
            this.classMainActivity = a;
            //new aramalarAdapter.execute("http://iskenderunveterinerklinigi.com/api/aramalar/read.php");

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
                String jSaat = c.getString("saat");
                String jTarih = c.getString("tarih");
                String jNumara =  c.getString("numara");
                String jMusteriID = c.getString("musteriID");
                String jNumaraID = c.getString("numaraID");
                String jAdi = c.getString("adi");
                String jSoyadi = c.getString("soyadi");

                // tmp hash map for single contact
                HashMap<String, String> contact = new HashMap<>();

                // adding each child node to HashMap key => value
                contact.put("id", id);
                contact.put("saat", jSaat);
                contact.put("tarih", jTarih);
                contact.put("numara", jNumara);
                contact.put("musteriID", jMusteriID);
                contact.put("numaraID", jNumaraID);
                contact.put("adi", jAdi);
                contact.put("soyadi", jSoyadi);

                // adding contact to contact list
                contactList.add(contact);
            }
            this.classMainActivity.setList(contactList);
            //jo.getString("records");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }



}

