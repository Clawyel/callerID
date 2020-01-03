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

class soforlerActivityAdapter extends RecyclerView.Adapter<soforlerActivityAdapter.MyViewHolder> {
    List<soforlerActivityData> soforlerActivityDataList;
    public List<soforlerActivityData> soforlerActivityDataList2;
    Context context;
    public soforlerActivityAdapter(List<soforlerActivityData> soforlerActivityDataList, Context context) {
        this.context = context;
        this.soforlerActivityDataList = soforlerActivityDataList;
        this.soforlerActivityDataList2 = soforlerActivityDataList;
        Log.d("adapter-listesi", String.valueOf(this.soforlerActivityDataList));
        soforlerActivityData data1= soforlerActivityDataList.get(1);
        Log.d("adapter-data-ad", String.valueOf(data1.ad));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_list_row, viewGroup, false);


        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
    final int getPosition = i;
        soforlerActivityData data= soforlerActivityDataList.get(i);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.name.setText(data.ad);
        Log.d("adapter-ad",data.ad);
        viewHolder.age.setText(String.valueOf(data.telefon));

        ImageButton btnSil = (ImageButton)viewHolder.itemView.findViewById(R.id.btnSil);
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soforlerActivityData personelSil= soforlerActivityDataList.get(getPosition);
                new soforSil(personelSil.id).execute("http://iskenderunveterinerklinigi.com/api/soforler/delete.php");
                Log.d("personel-sil-id: ",personelSil.id);
                Intent intent = new Intent(context, soforlerActivity.class);
                context.startActivity(intent);
            }
        });
        ImageButton btnDuzenle = (ImageButton)viewHolder.itemView.findViewById(R.id.btnDuzenle);
        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get position 0'dan başlıyor
                soforlerActivityData personelDuzenle= soforlerActivityDataList.get(getPosition);
                final String duzenlenecekID = personelDuzenle.id;
                final String duzenlenecekAd = personelDuzenle.ad;
                final String duzenlenecekTelefon = personelDuzenle.telefon;
                Intent intent = new Intent(context, soforDuzenleActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id",duzenlenecekID);
                extras.putString("ad",duzenlenecekAd);
                extras.putString("telefon",duzenlenecekTelefon);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("adapter-size", String.valueOf(soforlerActivityDataList.size()));
        return soforlerActivityDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,age;
        LinearLayout parent;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            name=itemView.findViewById(R.id.name);
            age=itemView.findViewById(R.id.age);
        }
    }
}
