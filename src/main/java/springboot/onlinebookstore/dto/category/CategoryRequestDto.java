package springboot.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryRequestDto {
    @NotNull
    @Length(min = 4, max = 40)
    private String name;
    @Length(min = 10, max = 255)
    private String description;
}
