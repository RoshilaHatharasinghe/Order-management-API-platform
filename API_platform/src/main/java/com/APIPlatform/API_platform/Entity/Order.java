package com.APIPlatform.API_platform.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime placementTimestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(Long id, String itemName, int quantity, String shippingAddress, OrderStatus status, LocalDateTime placementTimestamp, User user_id) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.placementTimestamp = placementTimestamp;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "OrderD{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status=" + status +
                ", placementTimestamp=" + placementTimestamp +
                ", user_id=" + user +
                '}';
    }
}

