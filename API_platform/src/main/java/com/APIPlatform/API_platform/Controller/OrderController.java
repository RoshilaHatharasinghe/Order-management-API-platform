package com.APIPlatform.API_platform.Controller;

import com.APIPlatform.API_platform.DTO.OrderDTO;
import com.APIPlatform.API_platform.Entity.Order;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/orders")
@RestController
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<Long> placeOrder(@RequestBody OrderDTO orderDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Long orderReferenceNumber = orderService.placeOrder(orderDTO, currentUser.getEmail());
        return ResponseEntity.ok(orderReferenceNumber);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam Long orderId) {
        String result = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getOrderHistory(@RequestParam int page, @RequestParam int size, @AuthenticationPrincipal UserDetails userDetails) {
        List<Order> orders = orderService.getOrderHistory(page, size, userDetails.getUsername());
        return ResponseEntity.ok(orders);
    }
}
