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

class araclarActivityAdapter extends RecyclerView.Adapter<araclarActivityAdapter.MyViewHolder> {
    List<araclarActivityData> araclarActivityDataList;
    public List<araclarActivityData> araclarActivityDataList2;
    Context context;
    public araclarActivityAdapter(List<araclarActivityData> araclarActivityDataList, Context context) {
        this.context = context;
        this.araclarActivityDataList = araclarActivityDataList;
        this.araclarActivityDataList2 = araclarActivityDataList;
        Log.d("adapter-listesi", String.valueOf(this.araclarActivityDataList));
        araclarActivityData data1= araclarActivityDataList.get(1);
        Log.d("adapter-data-ad", String.valueOf(data1.plaka));
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
        araclarActivityData data= araclarActivityDataList.get(i);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.name.setText(data.marka);
        Log.d("adapter-ad",data.plaka);
        viewHolder.age.setText(String.valueOf(data.plaka));

        ImageButton btnSil = (ImageButton)viewHolder.itemView.findViewById(R.id.btnSil);
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                araclarActivityData personelSil= araclarActivityDataList.get(getPosition);
                new aracSil(personelSil.id).execute("http://iskenderunveterinerklinigi.com/api/araclar/delete.php");
                Log.d("personel-sil-id: ",personelSil.id);
                Intent intent = new Intent(context, araclarActivity.class);
                context.startActivity(intent);
            }
        });
        ImageButton btnDuzenle = (ImageButton)viewHolder.itemView.findViewById(R.id.btnDuzenle);
        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get position 0'dan başlıyor
                araclarActivityData aracDuzenle= araclarActivityDataList.get(getPosition);
                final String duzenlenecekID = aracDuzenle.id;
                final String duzenlenecekPlaka = aracDuzenle.plaka;
                final String duzenlenecekMarka = aracDuzenle.marka;
                Intent intent = new Intent(context, aracDuzenleActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id",duzenlenecekID);
                extras.putString("plaka",duzenlenecekPlaka);
                extras.putString("marka",duzenlenecekMarka);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("adapter-size", String.valueOf(araclarActivityDataList.size()));
        return araclarActivityDataList.size();
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
