package springboot.onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import springboot.onlinebookstore.validation.password.FieldMatch;

@Data
@FieldMatch(field = "password", fieldMatch = "repeatPassword")
public class UserRegistrationRequestDto {
    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min = 6, max = 30)
    private String password;
    @NotNull
    @Size(min = 6, max = 30)
    private String repeatPassword;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String shippingAddress;
}
