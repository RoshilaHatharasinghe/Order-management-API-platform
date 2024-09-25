package com.APIPlatform.API_platform.Service;

import com.APIPlatform.API_platform.DTO.OrderDTO;
import com.APIPlatform.API_platform.Entity.Order;
import com.APIPlatform.API_platform.Entity.OrderStatus;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Repository.OrderRepo;
import com.APIPlatform.API_platform.Repository.UserRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepository;

    private final UserRepo userRepository;

    public OrderService(OrderRepo orderRepository, UserRepo userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public long placeOrder(OrderDTO orderDTO, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setItemName(orderDTO.getItemName());
        order.setQuantity(orderDTO.getQuantity());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setStatus(OrderStatus.NEW);
        order.setPlacementTimestamp(LocalDateTime.now());
        order.setUser(user);
        orderRepository.save(order);
        return order.getId();
    }

    public String cancelOrder(Long orderId) {
        Order order = orderRepository.findByIdAndStatus(orderId, OrderStatus.NEW)
                .orElseThrow(() -> new RuntimeException("Order not found or cannot be cancelled"));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return "Order cancelled successfully";
    }

    public List<Order> getOrderHistory(int page, int size, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Pageable pageable = PageRequest.of(page-1, size);
        return orderRepository.findByUserId(user.getId(), pageable);
    }

    public void dispatchOrders() {
        List<Order> newOrders = orderRepository.findAllByStatus(OrderStatus.NEW);
        newOrders.forEach(order -> {
            order.setStatus(OrderStatus.DISPATCHED);
            orderRepository.save(order);
        });
    }

}
