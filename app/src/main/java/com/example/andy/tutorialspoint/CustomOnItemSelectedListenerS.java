package com.example.andy.tutorialspoint;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListenerS implements OnItemSelectedListener {

    private aracGonderimleri a;
    public CustomOnItemSelectedListenerS(aracGonderimleri b)
    {
        this.a=b;
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
        a.secilenSoforDiziIndis=(int)id;
        a.secilenSoforID = a.soforDizi[a.secilenSoforDiziIndis][0];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}