package springboot.onlinebookstore.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import springboot.onlinebookstore.validation.Email;
import springboot.onlinebookstore.validation.FieldMatch;

@Data
@FieldMatch(field = "password", fieldMatch = "repeatPassword")
public class UserRegistrationRequestDto {
    @Email
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
