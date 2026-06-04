package se.jensen.miljana.cloudstoreuserservice.product;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductClient {

    private final RestClient restClient;
    private String fakestoreServiceUrl = "http://fakestoreservice-env.eba-pmbni5kh.eu-north-1.elasticbeanstalk.com/products";

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
