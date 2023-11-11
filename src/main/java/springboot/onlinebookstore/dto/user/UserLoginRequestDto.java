package springboot.onlinebookstore.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import springboot.onlinebookstore.validation.Email;

@Data
public class UserLoginRequestDto {
    @NotNull
    @Size(min = 6, max = 30)
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;
}
