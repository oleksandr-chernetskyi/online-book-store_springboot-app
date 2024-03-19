package springboot.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.onlinebookstore.dto.order.OrderRequestDto;
import springboot.onlinebookstore.dto.order.OrderResponseDto;
import springboot.onlinebookstore.dto.order.OrderStatusUpdateDto;
import springboot.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import springboot.onlinebookstore.model.User;
import springboot.onlinebookstore.service.OrderService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Order Controller",
        description = "Endpoints for managing user orders and items")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order")
    public OrderResponseDto createOrder(Authentication authentication,
            @RequestBody @Valid OrderRequestDto orderRequestDto) {
        User authenticatedUser = (User) authentication.getPrincipal();
        log.info("Create a new order for user: {}", authenticatedUser.getId());
        return orderService.createOrder(authenticatedUser.getId(), orderRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all orders by user")
    public List<OrderResponseDto> findAllOrdersByUserId(
            Authentication authentication, Pageable pageable) {
        User authenticatedUser = (User) authentication.getPrincipal();
        log.info("Get all user orders: {}", authenticatedUser);
        return orderService.findAllOrdersByUserId(authenticatedUser.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all order items")
    public Set<OrderItemResponseDto> findAllOrderItemsByOrderId(
            @PathVariable Long orderId) {
        log.info("Get all order items using order ID: {}", orderId);
        return orderService.findAllOrderItemsByOrderId(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get order item by ID")
    public OrderItemResponseDto findOrderItemById(
            @PathVariable Long orderId, @PathVariable Long itemId) {
        log.info("Get order item using ID, and item ID: {} {}", orderId, itemId);
        return orderService.findOrderItemById(orderId, itemId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{orderId}")
    @Operation(summary = "Update order status using ID")
    public OrderResponseDto updateOrderStatus(@PathVariable Long orderId,
            @RequestBody OrderStatusUpdateDto orderStatusUpdateDto) {
        log.info("Update order status by ID: {}", orderId);
        return orderService.updateOrderStatus(orderId, orderStatusUpdateDto);
    }
}
