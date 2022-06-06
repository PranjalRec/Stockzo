package com.pranjal.stockzo.ui.portfolio;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pranjal.stockzo.MarketAdapter;
import com.pranjal.stockzo.PortfolioAdapter;
import com.pranjal.stockzo.R;
import com.pranjal.stockzo.Stocks;
import com.pranjal.stockzo.databinding.FragmentPortfolioBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PortfolioFragment extends Fragment {

    private FragmentPortfolioBinding binding;
    RecyclerView recyclerViewPortfolio;
    ArrayList<Stocks> stockList = new ArrayList<>();
    public PortfolioAdapter portfolioAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPortfolioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        stockList = getArrayList("key");

        recyclerViewPortfolio = container.findViewById(R.id.recyclerViewPortfolio);

        binding.recyclerViewPortfolio.setLayoutManager(new LinearLayoutManager(requireContext()));
        portfolioAdapter = new PortfolioAdapter(requireContext(),stockList);
        binding.recyclerViewPortfolio.setAdapter(portfolioAdapter);
        portfolioAdapter.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<Stocks> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Stocks>>() {}.getType();
        return gson.fromJson(json, type);
    }
}