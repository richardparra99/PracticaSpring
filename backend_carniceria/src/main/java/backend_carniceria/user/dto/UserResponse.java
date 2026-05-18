package backend_carniceria.user.dto;

import java.util.Set;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        Set<String> roles
) {
}
