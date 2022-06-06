package com.pranjal.stockzo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketViewHolder> {

    Context context;
    ArrayList<Stocks> stockList = new ArrayList<>();
    int totalPrice = 0;


    public static ArrayList<Stocks> saveStock = new ArrayList<>();


    public MarketAdapter(Context context, ArrayList<Stocks> stockList) {
        this.context = context;
        this.stockList = stockList;
    }


    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_card,parent,false);
        return new MarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewHolder holder, int position) {
        if(getArrayList("key") != null){
            saveStock = getArrayList("key");
        }
        holder.textViewStockName.setText(stockList.get(position).getStockName());
        holder.textViewStockPrice.setText(""+stockList.get(position).getStockPrice());

        holder.textViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockList.get(position).number++;
                holder.textViewStockCount.setText(""+stockList.get(position).number);
            }
        });

        holder.textViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stockList.get(position).number > 0){
                    stockList.get(position).number--;
                }
                holder.textViewStockCount.setText(""+stockList.get(position).number);
            }
        });

        holder.buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stockNumber = Integer.valueOf(holder.textViewStockCount.getText().toString());
                int stockPrice = Integer.valueOf(holder.textViewStockPrice.getText().toString());
                totalPrice = stockNumber * stockPrice;

                if(stockNumber == 0){
                    Toast.makeText(context.getApplicationContext(),
                            "Select at least one stock", Toast.LENGTH_SHORT).show();
                }
                else{
                    stockList.get(holder.getAdapterPosition()).setStockCount(stockNumber);
                    saveStock.add(stockList.get(holder.getAdapterPosition()));
                    saveArrayList(saveStock,"key");

                    Toast.makeText(context.getApplicationContext(),
                            "Stock Bought and total price "+totalPrice, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    class MarketViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStockName,textViewStockPrice,textViewStockCount,textViewMinus,textViewPlus;
        Button buttonBuy;

        public MarketViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewStockName = itemView.findViewById(R.id.textViewStockNameP);
            textViewStockPrice = itemView.findViewById(R.id.textViewPriceP);
            textViewStockCount = itemView.findViewById(R.id.textViewCountP);
            textViewMinus = itemView.findViewById(R.id.textViewMinus);
            textViewPlus = itemView.findViewById(R.id.textViewPlus);
            buttonBuy = itemView.findViewById(R.id.buttonSell);

        }
    }

    public void saveArrayList(ArrayList<Stocks> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<Stocks> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Stocks>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
