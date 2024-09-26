package com.APIPlatform.API_platform.Service;

import com.APIPlatform.API_platform.DTO.OrderRequestDTO;
import com.APIPlatform.API_platform.DTO.OrderResponseDTO;
import com.APIPlatform.API_platform.Entity.Order;
import com.APIPlatform.API_platform.Entity.OrderStatus;
import com.APIPlatform.API_platform.Entity.User;
import com.APIPlatform.API_platform.Exception.OrderAlreadyCancelledException;
import com.APIPlatform.API_platform.Exception.OrderDispatchedException;
import com.APIPlatform.API_platform.Exception.OrderNotFoundException;
import com.APIPlatform.API_platform.Repository.OrderRepo;
import com.APIPlatform.API_platform.Repository.UserRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing orders within the application.

 * This class provides methods to:
 * - Place a new order.
 * - Cancel an existing order.
 * - Retrieve the order history for a specific user.
 * - Dispatch all new orders.

 * It interacts with `OrderRepo` and `UserRepo` for database operations,
 * ensuring that orders are associated with the correct user and managed according to business rules.
 */
@Service
public class OrderService {

    private final OrderRepo orderRepository;
    private final UserRepo userRepository;


    public OrderService(OrderRepo orderRepository, UserRepo userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Places a new order for the specified user.
     *
     * @param orderRequestDTO the order request details.
     * @param email the email of the user placing the order.
     * @return an OrderResponseDTO containing the details of the placed order.
     * @throws UsernameNotFoundException if the user is not found by email.
     */
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LocalDateTime placementTimestamp = LocalDateTime.now();

        Order order = new Order();
        order.setReferenceNumber("");
        order.setItemName(orderRequestDTO.getItemName());
        order.setQuantity(orderRequestDTO.getQuantity());
        order.setShippingAddress(orderRequestDTO.getShippingAddress());
        order.setStatus(OrderStatus.NEW);
        order.setPlacementTimestamp(placementTimestamp);
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);

        String referenceNumber = generateOrderReference(placementTimestamp, savedOrder.getId());
        savedOrder.setReferenceNumber(referenceNumber);
        orderRepository.save(savedOrder);

        return new OrderResponseDTO(
                referenceNumber,
                order.getItemName(),
                order.getQuantity(),
                order.getShippingAddress(),
                order.getStatus(),
                order.getPlacementTimestamp()
        );
    }

    /**
     * Cancels an existing order for the specified user.
     *
     * @param referenceNumber the reference number of the order to cancel.
     * @param user the user requesting the cancellation.
     * @return a message indicating the order has been canceled successfully.
     * @throws OrderNotFoundException if the order is not found for the user.
     * @throws OrderDispatchedException if the order has already been dispatched.
     * @throws OrderAlreadyCancelledException if the order has already been canceled.
     */
    public String cancelOrder(String referenceNumber, User user) {
        Order order = orderRepository.findByReferenceNumberAndUser(referenceNumber, user)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + referenceNumber + " not found for the user"));

        if (order.getStatus() == OrderStatus.DISPATCHED) {
            throw new OrderDispatchedException("Order with ID " + referenceNumber + " has already been dispatched");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new OrderAlreadyCancelledException("Order with ID " + referenceNumber + " is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return "Order "+order.getReferenceNumber()+" is cancelled successfully";
    }

    /**
     * Retrieves the order history for a specific user with pagination support.
     *
     * @param page the page number to retrieve (1-indexed).
     * @param size the number of orders to retrieve per page.
     * @param email the email of the user whose order history is being retrieved.
     * @return a list of OrderResponseDTOs containing the user's orders.
     * @throws UsernameNotFoundException if the user is not found by email.
     * @throws OrderNotFoundException if no orders are found for the user.
     */
    public List<OrderResponseDTO> getOrderHistory(int page, int size, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Pageable pageable = PageRequest.of(page-1, size);

        List<Order> userOrders =  orderRepository.findByUserId(user.getId(), pageable);

        if (userOrders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return userOrders.stream()
                .map(order -> new OrderResponseDTO(
                        order.getReferenceNumber(),
                        order.getItemName(),
                        order.getQuantity(),
                        order.getShippingAddress(),
                        order.getStatus(),
                        order.getPlacementTimestamp()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Dispatches all new orders, updating their status to DISPATCHED.
     * This method retrieves all orders with the NEW status and updates
     * their status to DISPATCHED in the repository.
     */
    public void dispatchOrders() {

        List<Order> newOrders = orderRepository.findAllByStatus(OrderStatus.NEW);

        newOrders.forEach(order -> {
            order.setStatus(OrderStatus.DISPATCHED);
            orderRepository.save(order);
        });
    }

    /**
     * Generates a unique reference number for an order based on its placement timestamp and ID.
     *
     * @param placementTimestamp the timestamp when the order was placed.
     * @param orderId the ID of the order.
     * @return a string representing the generated reference number.
     */
    private String generateOrderReference(LocalDateTime placementTimestamp, Long orderId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = placementTimestamp.format(formatter);
        return timestamp + "-" + orderId;
    }

}
