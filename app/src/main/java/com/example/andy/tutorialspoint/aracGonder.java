package com.example.andy.tutorialspoint;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class aracGonder extends AsyncTask<String, Void, String> {
    public String soforId;
    public String aracId;
    public String saat;
    public String tarih;
    public String TelNo;
    public String Adres;
    public  aracGonder(String soforId, String aracId, String saat, String tarih, String TelNo, String Adres)
    {
        this.soforId = soforId;
        this.aracId = aracId;
        this.saat = saat;
        this.tarih = tarih;
        this.TelNo = TelNo;
        this.Adres = Adres;
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
        //tvResult.setText(result);
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


        return conn.getResponseMessage()+"";

    }


    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("saat", this.saat);
        jsonObject.accumulate("tarih",  this.tarih);
        jsonObject.accumulate("aracId",  this.aracId);
        jsonObject.accumulate("soforId", this.soforId);
        jsonObject.accumulate("Adres",  this.Adres);
        jsonObject.accumulate("TelNo",  this.TelNo);
        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        // Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }
}
