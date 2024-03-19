package springboot.onlinebookstore.service;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import springboot.onlinebookstore.dto.order.OrderRequestDto;
import springboot.onlinebookstore.dto.order.OrderResponseDto;
import springboot.onlinebookstore.dto.order.OrderStatusUpdateDto;
import springboot.onlinebookstore.dto.orderitem.OrderItemResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(Long id, OrderRequestDto orderRequestDto);

    List<OrderResponseDto> findAllOrdersByUserId(Long userId, Pageable pageable);

    Set<OrderItemResponseDto> findAllOrderItemsByOrderId(Long orderId);

    OrderItemResponseDto findOrderItemById(Long orderId, Long itemId);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatusUpdateDto orderStatusUpdateDto);
}
