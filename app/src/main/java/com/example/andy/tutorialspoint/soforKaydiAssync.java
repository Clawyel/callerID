package com.example.andy.tutorialspoint;

import android.app.ProgressDialog;
import android.content.Context;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class soforKaydiAssync extends AsyncTask<String, Void, String> {

    public String gelenAd;
    public String gelenTelefon;
    Context context;
    private ProgressDialog p;
    public  soforKaydiAssync(Context context,String ad,String telefon)
    {
        this.context = context;
        this.gelenAd = ad;
        this.gelenTelefon = telefon;
        this.p=new ProgressDialog(this.context);
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
        super.onPreExecute();
        p.setMessage("Yeni Personel Oluşturuluyor");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
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
        jsonObject.accumulate("Ad", gelenAd);
        jsonObject.accumulate("Telefon",  gelenTelefon);
        Log.d("GeldiMi:","assyncEkle");

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
