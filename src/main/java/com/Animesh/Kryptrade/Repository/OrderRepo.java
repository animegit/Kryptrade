package com.Animesh.Kryptrade.Repository;

import com.Animesh.Kryptrade.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
