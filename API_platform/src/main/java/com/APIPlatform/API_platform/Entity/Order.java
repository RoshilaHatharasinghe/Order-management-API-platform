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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String referenceNumber;

    @Column(name="item_name",nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.NEW;

    @Column(name = "placement_timestamp", nullable = false)
    private LocalDateTime placementTimestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(Long id, String referenceNumber, String itemName, int quantity, String shippingAddress, OrderStatus status, LocalDateTime placementTimestamp, User user) {
        this.id = id;
        this.referenceNumber = referenceNumber;
        this.itemName = itemName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.placementTimestamp = placementTimestamp;
        this.user = user;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "OrderD{" +
                "id=" + id +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status=" + status +
                ", placementTimestamp=" + placementTimestamp +
                ", user=" + user +
                '}';
    }

}

