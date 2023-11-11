package springboot.onlinebookstore.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginResponseDto {
    private final String token;
}
