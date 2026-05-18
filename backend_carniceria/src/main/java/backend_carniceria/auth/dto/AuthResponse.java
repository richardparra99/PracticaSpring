package backend_carniceria.auth.dto;

import backend_carniceria.user.dto.UserResponse;

public record AuthResponse(
        String token,
        UserResponse user
) {
}
