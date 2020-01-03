package com.example.andy.tutorialspoint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class InterceptCall extends BroadcastReceiver {
    public String numaraKayitJson;

    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
            {
                String no= intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String ads="";
                this.numaraKayitJson = no;


                new gelenAramayiKaydet(this.numaraKayitJson).execute("http://iskenderunveterinerklinigi.com/api/aramalar/create.php");

                Toast.makeText(context,ads, Toast.LENGTH_SHORT).show();
            }
            else if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {

            }
            else if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE))
            {
                String no= intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String ads="";
                this.numaraKayitJson = no;
                caldi = true;

                new gelenAramayiKaydet(this.numaraKayitJson).execute("http://iskenderunveterinerklinigi.com/api/aramalar/create.php");

                Toast.makeText(context,ads, Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
