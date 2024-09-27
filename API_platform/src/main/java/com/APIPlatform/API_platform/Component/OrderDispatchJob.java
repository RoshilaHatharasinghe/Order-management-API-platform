package com.APIPlatform.API_platform.Component;

import com.APIPlatform.API_platform.Service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderDispatchJob {

    final OrderService orderService;

    // Constructor-based dependency injection to initialize the OrderService
    public OrderDispatchJob(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * This method is scheduled to run periodically based on the cron expression.
     * The cron expression "0 0 * * * *" means that this method will be executed at the start of every hour.
     * It calls the dispatchOrders() method from the OrderService to process and dispatch pending orders.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void execute(){
        orderService.dispatchOrders();
    }
}

