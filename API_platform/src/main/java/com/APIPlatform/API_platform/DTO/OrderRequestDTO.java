package com.APIPlatform.API_platform.DTO;

import com.APIPlatform.API_platform.Entity.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderRequestDTO {

    private Long id;

    @NotEmpty
    private String itemName;

    @NotNull
    private int quantity;

    @NotEmpty
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime placementTimestamp;

    private Long user_id;

    public OrderRequestDTO(Long id, String itemName, int quantity, String shippingAddress, OrderStatus status, LocalDateTime placementTimestamp, Long user_id) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.placementTimestamp = placementTimestamp;
        this.user_id = user_id;
    }

    public OrderRequestDTO() {
    }

    @Override
    public String toString() {
        return "Order {" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status=" + status +
                ", placementTimestamp=" + placementTimestamp +
                ", user_id=" + user_id +
                '}';
    }

}
