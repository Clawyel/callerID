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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class gelenAramayiKaydet extends AsyncTask<String, Void, String> {

    public String gelenNo;
    public  gelenAramayiKaydet(String args)
    {

        this.gelenNo = args;
    }
    @Override
    protected String doInBackground(String... strings) {

        boolean durumReturn=false;
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
        /*Intent i1 = new Intent (context, MainActivity.class);
        context.startActivity(i1);*/
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

        Date tarih = new Date();
        SimpleDateFormat bugun = new SimpleDateFormat("yyyy/MM/dd");
        bugun.setTimeZone(TimeZone.getTimeZone("GMT+03:00"));
        /*TimeZone tz = TimeZone.getTimeZone("GMT+03:00");
        Calendar calendar = Calendar.getInstance(tz);
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = "Current Time : " + mdformat.format(calendar.getTime());*/
        TimeZone tz = TimeZone.getTimeZone("GMT+03:00");
        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE))+":"+
                   String.format("%02d" , c.get(Calendar.SECOND))+":"+
               String.format("%03d" , c.get(Calendar.MILLISECOND));
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("saat", time);
        jsonObject.accumulate("tarih",  bugun.format(tarih));
        jsonObject.accumulate("numara",  this.gelenNo);

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
