package com.APIPlatform.API_platform.Component;

import com.APIPlatform.API_platform.Service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderDispatchJob {

    final OrderService orderService;

    public OrderDispatchJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void execute(){
        orderService.dispatchOrders();
    }
}

