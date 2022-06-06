package com.pranjal.stockzo.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pranjal.stockzo.MarketAdapter;
import com.pranjal.stockzo.R;
import com.pranjal.stockzo.Stocks;
import com.pranjal.stockzo.databinding.FragmentMarketBinding;

import java.util.ArrayList;

public class MarketFragment extends Fragment {

    private FragmentMarketBinding binding;
    RecyclerView recyclerViewMarket;

    ArrayList<Stocks> stockList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMarketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewMarket =  container.findViewById(R.id.recyclerViewMarket);

        if(stockList != null){
            stockList = new ArrayList<>();
        }

        stockList.add(new Stocks("Tata","250"));
        stockList.add(new Stocks("Tesla","1000"));
        stockList.add(new Stocks("Apple","400"));
        stockList.add(new Stocks("SpaceX","500"));
        stockList.add(new Stocks("Airtel","300"));
        stockList.add(new Stocks("Samsung","800"));
        stockList.add(new Stocks("Google","10000"));

        binding.recyclerViewMarket.setLayoutManager(new LinearLayoutManager(requireContext()));
        MarketAdapter marketAdapter = new MarketAdapter(container.getContext(),stockList);
        binding.recyclerViewMarket.setAdapter(marketAdapter);
        marketAdapter.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}