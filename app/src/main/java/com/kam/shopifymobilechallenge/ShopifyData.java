package com.kam.shopifymobilechallenge;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class ShopifyData extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    List<myData> data= Collections.emptyList();
    myData current;
    int currentPos = 0;

    // create constructor to innitilize context and data sent from MainActivity
    public ShopifyData(Context context, List<myData> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.activity_shopify_data, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        myData current = data.get(position);
        myHolder.myTitle.setText(current.myTitle);
        myHolder.amountLeft.setText("Left:" + (current.amountLeft / 100000));
        myHolder.myDesc.setText(current.myDesc);

        //TODO: Add proper ImageViews and change/fix api parser
        // load image into imageview using glide
        Log.e("KAME", current.srcImage);
        Glide.with(context).load(current.srcImage)
                .placeholder(R.drawable.err)
                .error(R.drawable.err)
                .into(myHolder.myImg);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView myTitle;
        ImageView myImg;
        TextView myDesc;
        TextView amountLeft;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            myTitle = itemView.findViewById(R.id.textFishName);
            myImg = itemView.findViewById(R.id.myImg);
            myDesc = itemView.findViewById(R.id.textType);
            amountLeft = itemView.findViewById(R.id.textPrice);
        }

    }

}