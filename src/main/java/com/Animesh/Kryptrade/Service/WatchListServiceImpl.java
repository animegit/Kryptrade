package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.WatchList;
import com.Animesh.Kryptrade.Repository.WatchListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService {

    @Autowired
    private WatchListRepo watchListRepo;



    @Override
    public WatchList findUserWatchList(Long userId) throws Exception {
        WatchList watchList=watchListRepo.findByUserId(userId);
        if(watchList==null){
            throw  new Exception("WatchList not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        // Create a new watchlist for a user
        WatchList watchList = new WatchList();
        watchList.setUser(user);
        return watchListRepo.save(watchList);
    }

    @Override
    public WatchList findById(Long id) {
        // Find a watchlist by its ID
        return watchListRepo.findById(id)
                .orElse(null); // Return null if no watchlist found
    }

    @Override
    public Coin addItemtoWatchList(Coin coin, User user) throws Exception {
        WatchList watchList=findUserWatchList(user.getId());
        if(watchList.getCoins().contains(coin)){
            watchList.getCoins().remove(coin);
        }
        else{
            watchList.getCoins().add(coin);
        }
        watchListRepo.save(watchList);
        return coin;
    }


}
