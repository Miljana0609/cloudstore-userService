package se.jensen.miljana.cloudstoreuserservice.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductClient {

    private final RestClient restClient;

    @Value("${fakestore.url}")
    private String fakestoreServiceUrl;

    public ProductClient(RestClient.Builder builder) {
        this.restClient = builder.build();
    }


    public List<Product> getProducts() {
        Product[] products = restClient
                .get()
                .uri(fakestoreServiceUrl)
                .retrieve()
                .body(Product[].class);

        if (products == null || products.length == 0) {
            return List.of();
        }
        return Arrays.asList(products);

    }


}
