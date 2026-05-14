package se.jensen.miljana.cloudstoreuserservice.model.dto;

public record AuthResponse(
        String token,
        String email
) {
}
