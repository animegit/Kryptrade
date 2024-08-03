package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinsList(int page) throws Exception;
    String getMarketchart(String coinId,int days) throws Exception;
    String getCoinDetails(String coinId) throws Exception;
    Coin findById(String id) throws Exception;
    String searchCoin(String keyword) throws Exception;
    String getTop50Coins() throws Exception;
    String getTradingCoins() throws Exception;
}
