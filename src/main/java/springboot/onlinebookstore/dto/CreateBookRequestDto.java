package springboot.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import springboot.onlinebookstore.validation.Isbn;

@Data
public class CreateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    @Isbn
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;

    private String description;

    private String coverImage;
}
