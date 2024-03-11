package springboot.onlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import springboot.onlinebookstore.validation.isbn.Isbn;

@Data
public class CreateBookRequestDto {
    @NotNull
    @Length(min = 5, max = 255)
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
    @NotNull
    private Set<Long> categoryIds;
}
