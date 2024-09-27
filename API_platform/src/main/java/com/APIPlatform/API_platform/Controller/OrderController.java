package com.APIPlatform.API_platform.Controller;

import com.APIPlatform.API_platform.DTO.OrderRequestDTO;
import com.APIPlatform.API_platform.DTO.OrderResponseDTO;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Response.SuccessResponse;
import com.APIPlatform.API_platform.Service.OrderService;
import jakarta.validation.Valid;
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

    // Constructor-based dependency injection for OrderService
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Handles placing an order with data from the request body for the authenticated user
    @PostMapping(produces = "application/json")
    public ResponseEntity<SuccessResponse> placeOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        OrderResponseDTO order = orderService.placeOrder(orderRequestDTO, currentUser.getEmail());
        return ResponseEntity.ok(new SuccessResponse(order));
    }

    // Cancels an order by its reference number for the authenticated user
    @PostMapping(path="/cancel/{referenceNumber}", produces = "application/json")
    public ResponseEntity<SuccessResponse> cancelOrder(@PathVariable("referenceNumber") String referenceNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        String result = orderService.cancelOrder(referenceNumber, currentUser);
        return ResponseEntity.ok(new SuccessResponse(result));
    }

    // Retrieves paginated order history for the authenticated user
    @GetMapping(path="/getAll", produces = "application/json")
    public ResponseEntity<SuccessResponse> getOrderHistory(@RequestParam int page, @RequestParam int size, @AuthenticationPrincipal UserDetails userDetails) {
        List<OrderResponseDTO> orders = orderService.getOrderHistory(page, size, userDetails.getUsername());
        return ResponseEntity.ok(new SuccessResponse(orders));
    }
}
