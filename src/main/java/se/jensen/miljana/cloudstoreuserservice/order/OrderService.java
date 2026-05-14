package se.jensen.miljana.cloudstoreuserservice.order;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.jensen.miljana.cloudstoreuserservice.order.model.CreateOrderItemRequest;
import se.jensen.miljana.cloudstoreuserservice.order.model.CreateOrderRequest;
import se.jensen.miljana.cloudstoreuserservice.order.model.CustomerOrder;
import se.jensen.miljana.cloudstoreuserservice.order.model.OrderItem;
import se.jensen.miljana.cloudstoreuserservice.product.Product;
import se.jensen.miljana.cloudstoreuserservice.product.ProductClient;

import java.util.List;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }


    public CustomerOrder createOrder(String userEmail, CreateOrderRequest request) {
        List<Product> products = productClient.getProducts();

        CustomerOrder order = new CustomerOrder(userEmail);

        for (CreateOrderItemRequest itemRequest : request.items()) {
            Product product = findProduct(products, itemRequest.productId());

            OrderItem item = new OrderItem();
            item.setId((long) product.id());
            item.setProductTitle(product.title());
            item.setPrice(item.getPrice());
            item.setQuantity(itemRequest.quantity());

            order.addItem(item);
        }
        return orderRepository.save(order);

    }


    public List<CustomerOrder> getOrderForUser(String userEmail) {
        return orderRepository.findByUserEmail(userEmail);
    }


    private Product findProduct(List<Product> products, long productId) {
        return products.stream()
                .filter(product -> product.id() == productId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"
                ));
    }


}
