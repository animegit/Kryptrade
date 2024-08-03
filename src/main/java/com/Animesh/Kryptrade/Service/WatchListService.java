package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.WatchList;

public interface WatchListService {


    WatchList findUserWatchList(Long userId) throws Exception;

    WatchList createWatchList(User user);
    WatchList findById(Long id);
    Coin addItemtoWatchList(Coin coin,User user) throws Exception;
}
