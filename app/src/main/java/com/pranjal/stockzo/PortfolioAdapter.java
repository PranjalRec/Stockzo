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
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pranjal.stockzo.ui.portfolio.PortfolioFragment;

import java.util.ArrayList;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioViewHolder> {


    Context context;
    ArrayList<Stocks> stockList = new ArrayList<>();


    public PortfolioAdapter(Context context, ArrayList<Stocks> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.portfolio_card,parent,false);
        return new PortfolioViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
        if(stockList != null){
            holder.textViewStockName.setText(stockList.get(position).getStockName());
            int stockPrice = Integer.valueOf(stockList.get(position).getStockPrice());
            int stockNumber = stockList.get(position).getStockCount();
            holder.textViewStockPrice.setText(""+stockPrice * stockNumber);
            holder.textViewStockCount.setText(stockNumber+"");
        }
        else{
            Toast.makeText(context, "No Stocks bought yet.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        if(stockList != null){
            return stockList.size();
        }
        return 0;
    }


}

class PortfolioViewHolder extends RecyclerView.ViewHolder {

    TextView textViewStockName,textViewStockPrice,textViewStockCount,textViewMinus,textViewPlus;
    Button buttonSell;
    int totalPrice = 0;

    private PortfolioAdapter adapter;

    public PortfolioViewHolder(@NonNull View itemView) {
        super(itemView);


        textViewStockName = itemView.findViewById(R.id.textViewStockNameP);
        textViewStockPrice = itemView.findViewById(R.id.textViewPriceP);
        textViewStockCount = itemView.findViewById(R.id.textViewCountP);
        buttonSell = itemView.findViewById(R.id.buttonSell);

        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalPrice = Integer.valueOf(textViewStockPrice.getText().toString());
                Toast.makeText(adapter.context,
                        "Stock Sold and total price Rs. "+totalPrice, Toast.LENGTH_SHORT).show();

                MarketAdapter.saveStock.remove(getAdapterPosition());
                saveArrayList(MarketAdapter.saveStock,"key");

                adapter.stockList.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            }
        });

    }

    public PortfolioViewHolder linkAdapter(PortfolioAdapter adapter){
        this.adapter = adapter;
        return this;
    }

    public void saveArrayList(ArrayList<Stocks> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(adapter.context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

}
