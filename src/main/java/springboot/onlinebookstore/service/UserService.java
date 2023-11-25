package springboot.onlinebookstore.service;

import springboot.onlinebookstore.dto.user.UserRegistrationRequestDto;
import springboot.onlinebookstore.dto.user.UserResponseDto;
import springboot.onlinebookstore.exception.RegistrationException;
import springboot.onlinebookstore.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException;

    User getAuthenticatedUser();
}
