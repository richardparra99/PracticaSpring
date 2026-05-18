package backend_carniceria.bootstrap;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import backend_carniceria.role.Role;
import backend_carniceria.role.RoleName;
import backend_carniceria.role.RoleRepository;
import backend_carniceria.user.User;
import backend_carniceria.user.UserRepository;

@Component
public class DataBootstrap implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BootstrapAdminProperties adminProperties;

    public DataBootstrap(RoleRepository roleRepository,
                         UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         BootstrapAdminProperties adminProperties) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminProperties = adminProperties;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedRoles();
        seedAdmin();
    }

    private void seedRoles() {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
        }
    }

    private void seedAdmin() {
        if (!adminProperties.isEnabled() || userRepository.existsByEmail(adminProperties.getEmail())) {
            return;
        }

        Set<Role> adminRoles = EnumSet.of(RoleName.ROLE_ADMIN).stream()
                .map(this::requireRole)
                .collect(Collectors.toSet());

        User admin = new User(
                adminProperties.getEmail(),
                adminProperties.getFirstName(),
                adminProperties.getLastName(),
                passwordEncoder.encode(adminProperties.getPassword()),
                adminRoles
        );

        userRepository.save(admin);
    }

    private Role requireRole(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalStateException("Role not found: " + roleName));
    }
}
