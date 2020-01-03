package com.example.andy.tutorialspoint;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class smsGonder extends AsyncTask<String, String, String> {
    public List<String> str;
    private aracGonderimleri aracGonderimleri;
    public ArrayList<HashMap<String, String>> contactList;
    public String tmp;
    public smsGonder(aracGonderimleri a)
    {
        this.aracGonderimleri = a;
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


    }



}

