package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Model.WatchList;
import com.Animesh.Kryptrade.Service.CoinService;
import com.Animesh.Kryptrade.Service.UserService;
import com.Animesh.Kryptrade.Service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    // Get the user's watchlist based on JWT token
    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        // Extract user from JWT token
        User user = userService.findUserProfileByJwt(jwt);

        // Handle invalid token or user not found
        if (user == null) {
            return ResponseEntity.status(401).build(); // Return 401 Unauthorized
        }

        // Retrieve the user's watchlist
        WatchList watchList = watchListService.findUserWatchList(user.getId());

        // Handle watchlist not found
        if (watchList == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }

        return ResponseEntity.ok(watchList);
    }

    // Create a new watchlist for the user based on JWT token
    @PostMapping("/create")
    public ResponseEntity<WatchList> createWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        // Extract user from JWT token
        User user = userService.findUserProfileByJwt(jwt);

        // Handle invalid token or user not found
        if (user == null) {
            return ResponseEntity.status(401).build(); // Return 401 Unauthorized
        }

        // Create a new watchlist for the user
        WatchList newWatchList = watchListService.createWatchList(user);

        // Return the newly created watchlist
        return ResponseEntity.ok(newWatchList);
    }

    // Get a watchlist by its ID
    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchList> getWatchListById(
            @PathVariable Long watchlistId
            ) throws Exception {


        // Retrieve the watchlist by ID
        WatchList watchList = watchListService.findById(watchlistId);



        return ResponseEntity.ok(watchList);
    }
    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(
            @PathVariable String coinId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Coin coin=coinService.findById(coinId);
        Coin addedcoin=watchListService.addItemtoWatchList(coin,user);


        return ResponseEntity.ok(addedcoin); // Return the updated watchlist
    }
}
