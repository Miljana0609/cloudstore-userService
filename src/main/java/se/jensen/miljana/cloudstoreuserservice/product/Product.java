package se.jensen.miljana.cloudstoreuserservice.product;

public record Product(
        int id,
        String title,
        double price,
        String description,
        String category,
        String image,
        Rating rating
) {
}
