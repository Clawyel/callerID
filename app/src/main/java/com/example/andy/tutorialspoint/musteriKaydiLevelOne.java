package com.example.andy.tutorialspoint;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class musteriKaydiLevelOne extends AsyncTask<String, Void, String> {
    public String gelenAd;
    public String gelenSoyad;
    public int gelenAracGonderimSayisi;
    private musteriyiKaydet m;
    public  musteriKaydiLevelOne(String ad, String soyad, int aracGonderimSayisi, musteriyiKaydet m)
    {
        this.gelenAd = ad;
        this.gelenSoyad =soyad;
        this.gelenAracGonderimSayisi = aracGonderimSayisi;
        this.m = m;
    }
    @Override
    protected String doInBackground(String... strings) {


        try {
            try {
                return HttpPost(strings[0]);
            } catch (JSONException e) {
                e.printStackTrace();
                return "Error!";
            }
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }


    }
    @Override
    protected void onPostExecute(String result) {



    Log.d("musteri-alimi",result);


    }
    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        Log.d("response-meg:",conn.getResponseMessage());
        return conn.getResponseMessage()+"";

    }


    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("AracGonderimSayisi", "1");
        jsonObject.accumulate("Adi",  this.gelenAd.toString());
        jsonObject.accumulate("Soyadi",  this.gelenSoyad.toString());

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.d("json-id", jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }
}
