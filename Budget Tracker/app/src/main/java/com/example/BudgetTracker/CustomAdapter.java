package com.example.BudgetTracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id, category, date, amount;


    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList category, ArrayList date,
                  ArrayList amount){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.category_txt.setText(String.valueOf(category.get(position)));
        if (String.valueOf(category.get(position)).equals("Income")) {
            //setting color
            holder.amount_txt.setTextColor(Color.parseColor("#2AC924"));
            //setting amount
            holder.amount_txt.setText("+" + amount.get(position) + "TND");
            //setting image
            holder.imageView.setImageResource(R.drawable.ic_income);
        } else  {
            //setting color
            holder.amount_txt.setTextColor(Color.parseColor("#DD0000"));
            //setting amount
            holder.amount_txt.setText("-" + amount.get(position) + "TND");
            //setting image
            if (String.valueOf(category.get(position)).equals("Food and Drinks")){
                holder.imageView.setImageResource(R.drawable.ic_foodanddrinks);
            } else {
                if (String.valueOf(category.get(position)).equals("Home Essentials")){
                    holder.imageView.setImageResource(R.drawable.ic_homeessentials);
                } else {
                    if (String.valueOf(category.get(position)).equals("Transportation")){
                        holder.imageView.setImageResource(R.drawable.ic_transportation);
                    } else {
                        if (String.valueOf(category.get(position)).equals("Leisure Activities")){
                            holder.imageView.setImageResource(R.drawable.ic_leisureactivity);
                        } else {
                            if (String.valueOf(category.get(position)).equals("Bills and Taxes")){
                                holder.imageView.setImageResource(R.drawable.ic_billsandtaxes);
                            } else {
                                if (String.valueOf(category.get(position)).equals("Investments")){
                                    holder.imageView.setImageResource(R.drawable.ic_investments);
                                } else {
                                    holder.imageView.setImageResource(R.drawable.ic_other);
                                }
                            }
                        }
                    }
                }
            }
        }
        holder.date_txt.setText(String.valueOf(date.get(position)));

        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("category", String.valueOf(category.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("amount", String.valueOf(amount.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView  category_txt, date_txt, amount_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            category_txt = itemView.findViewById(R.id.category_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            amount_txt = itemView.findViewById(R.id.amount_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }


}
