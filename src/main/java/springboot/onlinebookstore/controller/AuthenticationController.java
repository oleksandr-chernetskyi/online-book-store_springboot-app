package springboot.onlinebookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.onlinebookstore.dto.user.UserLoginRequestDto;
import springboot.onlinebookstore.dto.user.UserLoginResponseDto;
import springboot.onlinebookstore.dto.user.UserRegistrationRequestDto;
import springboot.onlinebookstore.dto.user.UserResponseDto;
import springboot.onlinebookstore.exception.RegistrationException;
import springboot.onlinebookstore.security.AuthenticationService;
import springboot.onlinebookstore.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid
            UserRegistrationRequestDto registrationRequestDto) throws RegistrationException {
        logger.info("Received information about registration user with email: {}",
                registrationRequestDto.getEmail());
        return userService.register(registrationRequestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return authenticationService.authenticate(userLoginRequestDto);
    }
}
