package com.APIPlatform.API_platform.DTO;

import com.APIPlatform.API_platform.Entity.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderResponseDTO {

    private String referenceNumber;
    private String itemName;
    private int quantity;
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime placementTimestamp;

    public OrderResponseDTO(String referenceNumber, String itemName, int quantity, String shippingAddress, OrderStatus status, LocalDateTime placementTimestamp) {
        this.referenceNumber = referenceNumber;
        this.itemName = itemName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.placementTimestamp = placementTimestamp;
    }

    public OrderResponseDTO() {
    }

    @Override
    public String toString() {
        return "Order {" +
                "referenceNumber=" + referenceNumber +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status=" + status +
                ", placementTimestamp=" + placementTimestamp +
                '}';
    }
}
