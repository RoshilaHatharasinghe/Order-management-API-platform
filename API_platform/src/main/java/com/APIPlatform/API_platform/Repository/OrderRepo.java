package com.APIPlatform.API_platform.Repository;

import com.APIPlatform.API_platform.Entity.Order;
import com.APIPlatform.API_platform.Entity.OrderStatus;
import com.APIPlatform.API_platform.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Repository
@EnableJpaRepositories
public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(Long user_id, Pageable pageable);

    Optional<Order> findByReferenceNumberAndUser(String referenceNumber, User user);

    List<Order> findAllByStatus(OrderStatus orderStatus);

}
