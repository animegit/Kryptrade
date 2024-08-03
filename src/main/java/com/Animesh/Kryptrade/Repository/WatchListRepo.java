package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepo extends JpaRepository<WatchList,Long> {
    WatchList findByUserId(Long userId);
}
