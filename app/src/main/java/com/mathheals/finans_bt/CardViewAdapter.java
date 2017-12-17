package com.mathheals.finans_bt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by g1409 on 5.12.2017.
 */

public class CardViewAdapter  extends RecyclerView.Adapter<CardViewAdapter.MyViewHolderr>{
      ArrayList<Esya> mDataList;
      LayoutInflater inflater;
      CustomItemClickListener listener;

    public CardViewAdapter(Context context, ArrayList<Esya> data,CustomItemClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.listener=listener;
        this.mDataList = data;
      }
    @Override
    public MyViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.list_item, parent, false);
        final MyViewHolderr holder = new MyViewHolderr(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());
                }
        });
        return holder;
       }
    @Override
    public void onBindViewHolder(MyViewHolderr holder, int position) {

        Esya tiklanilanAnimal = mDataList.get(position);
        holder.setData(tiklanilanAnimal, position);
      }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    class MyViewHolderr extends RecyclerView.ViewHolder {

        TextView esyaAdi;
        ImageView esyaResmi;

        public MyViewHolderr(View itemView) {
            super(itemView);
            esyaAdi = (TextView) itemView.findViewById(R.id.txv_row);
            esyaResmi = (ImageView) itemView.findViewById(R.id.img_row);
        }
        public void setData(Esya tiklanilanAnimal, int position) {
            this.esyaResmi.setImageResource(tiklanilanAnimal.getImageId());
            this.esyaAdi.setText(tiklanilanAnimal.getTitle());
        }
    }
}

