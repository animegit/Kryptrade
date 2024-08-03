package com.Animesh.Kryptrade.Controller;

import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    private CoinService coinService;
    @Autowired
    private ObjectMapper objectMapper;

    // Fetch a paginated list of coins
    @GetMapping("/list")
    public ResponseEntity<List<Coin>> getCoinsList(@RequestParam(required = false,name = "page") int page) {
        try {
            List<Coin> coins = coinService.getCoinsList(page);
            return ResponseEntity.ok(coins);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Get market chart data for a specific coin over a number of days
    @GetMapping("/{coinId}/chart")
    public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId, @RequestParam("days") int days) throws Exception {

            String marketChart = coinService.getMarketchart(coinId, days);
            JsonNode jsonNode=objectMapper.readTree(marketChart);

            return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);

    }

    // Get detailed information about a specific coin
    @GetMapping("/details/{coinId}")
    public ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception {

            String details = coinService.getCoinDetails(coinId);
            JsonNode jsonNode=objectMapper.readTree(details);
            return ResponseEntity.ok(jsonNode);

    }

    // Find a coin by its ID
    @GetMapping("/{coinId}")
    public ResponseEntity<Coin> findById(@PathVariable String coinId) {
        try {
            Coin coin = coinService.findById(coinId);
            return ResponseEntity.ok(coin);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Search for coins by a keyword
    @GetMapping("/search")
    public ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws Exception {

            String result = coinService.searchCoin(keyword);
            JsonNode jsonNode=objectMapper.readTree(result);

            return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
    }

    // Get the top 50 coins by market cap
    @GetMapping("/top50")
    public ResponseEntity<JsonNode> getTop50Coins() throws Exception {

            String top50Coins = coinService.getTop50Coins();
        JsonNode jsonNode=objectMapper.readTree(top50Coins);

        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);

    }

    // Get trending coins
    @GetMapping("/trending")
    public ResponseEntity<JsonNode> getTradingCoins() throws Exception {

            String tradingCoins = coinService.getTradingCoins();
        JsonNode jsonNode=objectMapper.readTree(tradingCoins);


        return ResponseEntity.ok(jsonNode);

    }
}
