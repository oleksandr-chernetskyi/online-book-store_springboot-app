package springboot.onlinebookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountQuantityDto {
    @NotNull
    @Min(0)
    private int quantity;
}
