package springboot.onlinebookstore.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springboot.onlinebookstore.repository.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("Can't find user by email: {}", email);
            return new RuntimeException("Can't find user by email: " + email);
        });
    }
}
