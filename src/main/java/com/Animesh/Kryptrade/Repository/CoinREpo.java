package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinREpo  extends JpaRepository<Coin,String> {
}
