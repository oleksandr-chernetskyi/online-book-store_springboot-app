package springboot.onlinebookstore.dto.order;

import lombok.Data;
import springboot.onlinebookstore.model.Order;

@Data
public class OrderStatusUpdateDto {
    private Order.Status status;
}
