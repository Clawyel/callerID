package com.example.andy.tutorialspoint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

class aracGonderimListesiActivityAdapter extends RecyclerView.Adapter<aracGonderimListesiActivityAdapter.MyViewHolder> {
    List<aracGonderimListesiData> aracGonderimListesiDataList;
    public List<aracGonderimListesiData> aracGonderimListesiDataList2;
    Context context;
    public aracGonderimListesiActivityAdapter(List<aracGonderimListesiData> aracGonderimListesiDataList, Context context) {
        this.context = context;
        this.aracGonderimListesiDataList = aracGonderimListesiDataList;
        this.aracGonderimListesiDataList2 = aracGonderimListesiDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.arac_gonderimleri_list_row, viewGroup, false);


        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        final int getPosition = i;
        aracGonderimListesiData data= aracGonderimListesiDataList.get(i);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.musteriAdiSoyadi.setText(data.musteriAdi+ " "+data.musteriSoyadi);
        viewHolder.gonderimSaati.setText(String.valueOf(data.saat));
        viewHolder.gonderimTarihi.setText(String.valueOf(data.tarih));
        viewHolder.adres.setText("Adres: "+String.valueOf(data.adres));
        viewHolder.soforAdi.setText("Şoför: "+String.valueOf(data.soforAdi));
        viewHolder.aracMarka.setText(String.valueOf(data.aracMarka));
        viewHolder.aracPlaka.setText(String.valueOf(data.aracPlaka));
    }

    @Override
    public int getItemCount() {
        Log.d("adapter-size", String.valueOf(aracGonderimListesiDataList.size()));
        return aracGonderimListesiDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView musteriAdiSoyadi,gonderimSaati,gonderimTarihi,adres,soforAdi,aracMarka,aracPlaka;
        LinearLayout parent;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            musteriAdiSoyadi=itemView.findViewById(R.id.musteriAdSoyad);
            gonderimSaati=itemView.findViewById(R.id.gonderimSaati);
            gonderimTarihi=itemView.findViewById(R.id.gonderimTarihi);
            adres=itemView.findViewById(R.id.adres);
            soforAdi=itemView.findViewById(R.id.soforAdi);
            aracMarka=itemView.findViewById(R.id.aracMarka);
            aracPlaka=itemView.findViewById(R.id.aracPlaka);
        }
    }
}
