package springboot.onlinebookstore.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import springboot.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import springboot.onlinebookstore.model.Order;
import springboot.onlinebookstore.validation.status.Status;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems;
    private LocalDateTime orderTime;
    private BigDecimal totalPrice;
    @Status(enumClass = Order.Status.class)
    private String status;
}
