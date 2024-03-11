package springboot.onlinebookstore.dto.order;

import lombok.Data;
import springboot.onlinebookstore.model.Order;
import springboot.onlinebookstore.validation.status.Status;

@Data
public class OrderStatusUpdateDto {
    @Status(enumClass = Order.Status.class)
    private Order.Status status;
}
