package backend_carniceria.user;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import backend_carniceria.user.dto.UserResponse;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isEnabled(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toCollection(java.util.LinkedHashSet::new))
        );
    }
}
