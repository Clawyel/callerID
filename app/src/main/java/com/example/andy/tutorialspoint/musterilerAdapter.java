package com.example.andy.tutorialspoint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

class musterilerAdapter extends RecyclerView.Adapter<musterilerAdapter.MyViewHolder> {
    List<musterilerData> musterilerDataList;
    public List<musterilerData> musterilerDataList2;
    Context context;
    public musterilerAdapter(List<musterilerData> musterilerDataList, Context context) {
        this.context = context;
        this.musterilerDataList = musterilerDataList;
        this.musterilerDataList2 = musterilerDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.musteri_card, viewGroup, false);


        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        final int getPosition = i;
        musterilerData data= musterilerDataList.get(i);
        Random rnd = new Random();
        int currentColor = Color.argb(200, rnd.nextInt(125), rnd.nextInt(125), rnd.nextInt(125));
        viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.adSoyad.setText("Ad: "+data.ad+" "+data.soyad);
        viewHolder.telefon.setText("Telefon: "+data.telefon);
        viewHolder.adres.setText("Adres: "+data.adres);
        viewHolder.aracGonderimSayisi.setText("A.G.Sayısı: "+data.aracGonderimSayisi);
        viewHolder.bakiye.setText("bakiye: "+data.bakiye+" TL");
        final String aranacakNo = data.telefon;



        ImageButton btnSil = (ImageButton)viewHolder.itemView.findViewById(R.id.btnSil);
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                musterilerData personelSil= musterilerDataList.get(getPosition);
                new musteriSil(personelSil.id).execute("http://iskenderunveterinerklinigi.com/api/musteriler/delete.php");
                Log.d("personel-sil-id: ",personelSil.id);
                Intent intent = new Intent(context, musteriler.class);
                context.startActivity(intent);
            }
        });
        ImageButton btnDuzenle = (ImageButton)viewHolder.itemView.findViewById(R.id.btnDuzenle);
        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get position 0'dan başlıyor
                musterilerData data= musterilerDataList.get(getPosition);
                final String duzenlenecekID = data.id;
                final String duzenlenecekAd = data.ad;
                final String duzenlenecekSoyad = data.soyad;
                Intent intent = new Intent(context, musteriyiDuzenleActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id",duzenlenecekID);
                extras.putString("ad",duzenlenecekAd);
                extras.putString("soyad",duzenlenecekSoyad);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });

        ImageButton btnAramaYap = (ImageButton)viewHolder.itemView.findViewById(R.id.btnMusteriyiAra);
        btnAramaYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+aranacakNo));
                context.startActivity(callIntent);

            }
        });
        Button btnBakiyeDuzenle = (Button)viewHolder.itemView.findViewById(R.id.bakiyedegistir);
        btnBakiyeDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musterilerData data= musterilerDataList.get(getPosition);
                final String duzenlenecekID = data.id;
                Intent intent = new Intent(context, bakiyeActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id",duzenlenecekID);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("adapter-size", String.valueOf(musterilerDataList.size()));
        return musterilerDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView adSoyad,telefon,adres,aracGonderimSayisi,bakiye;
        LinearLayout parent;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            adSoyad=itemView.findViewById(R.id.adSoyad);
            telefon=itemView.findViewById(R.id.telefon);
            adres=itemView.findViewById(R.id.adres);
            aracGonderimSayisi=itemView.findViewById(R.id.aracGonderimSayisi);
            bakiye=itemView.findViewById(R.id.bakiye);
        }
    }
}
