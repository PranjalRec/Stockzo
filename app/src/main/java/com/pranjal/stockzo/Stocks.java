package com.pranjal.stockzo;


public class Stocks {
    String stockName,stockPrice;
    int stockCount = 0;

    int number = 0;

    public Stocks(String stockName, String stockPrice) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

    public String getStockName() {
        return stockName;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
