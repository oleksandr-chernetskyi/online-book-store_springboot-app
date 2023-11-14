package springboot.onlinebookstore.service.impl;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.user.UserRegistrationRequestDto;
import springboot.onlinebookstore.dto.user.UserResponseDto;
import springboot.onlinebookstore.exception.EntityNotFoundException;
import springboot.onlinebookstore.exception.RegistrationException;
import springboot.onlinebookstore.mapper.UserMapper;
import springboot.onlinebookstore.model.Role;
import springboot.onlinebookstore.model.User;
import springboot.onlinebookstore.repository.role.RoleRepository;
import springboot.onlinebookstore.repository.user.UserRepository;
import springboot.onlinebookstore.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequest)
            throws RegistrationException {
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            log.error("Register method failed. "
                            + "Unable to registration with current parameters: {}",
                    registrationRequest.getEmail());
            throw new RegistrationException("Unable to complete registration");
        }
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setShippingAddress(registrationRequest.getShippingAddress());

        Role userRole = roleRepository.findRoleByRoleName(Role.RoleName.USER)
                .orElseThrow(() -> {
                    log.error("Register method failed. "
                                    + "Can't find role by user for registration request: {}",
                            registrationRequest);
                    return new RegistrationException("Can't find role by user");
                });
        Set<Role> userSetRole = new HashSet<>();
        userSetRole.add(userRole);
        user.setRoles(userSetRole);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> {
                    log.error("GetAuthenticatedUser method failed. "
                            + "Can't find user by email: {}", authentication.getName());
                    return new EntityNotFoundException("Can't find user by email: "
                            + authentication.getName());
                });
    }
}
