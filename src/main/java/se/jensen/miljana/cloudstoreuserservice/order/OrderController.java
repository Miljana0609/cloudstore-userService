package se.jensen.miljana.cloudstoreuserservice.order;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.miljana.cloudstoreuserservice.order.model.CreateOrderRequest;
import se.jensen.miljana.cloudstoreuserservice.order.model.CustomerOrder;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public CustomerOrder createOrder(Authentication authentication, @RequestBody @Valid CreateOrderRequest request) {

        if (authentication == null) {
            throw new RuntimeException("User is not authenticated");
        }

        String userEmail = authentication.getName();
        return orderService.createOrder(userEmail, request);
    }


    @GetMapping
    public List<CustomerOrder> getOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        return orderService.getOrderForUser(userEmail);
    }

}
