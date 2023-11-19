package springboot.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import springboot.onlinebookstore.config.MapperConfig;
import springboot.onlinebookstore.dto.user.UserRegistrationRequestDto;
import springboot.onlinebookstore.dto.user.UserResponseDto;
import springboot.onlinebookstore.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}
