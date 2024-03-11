package springboot.onlinebookstore.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.order.OrderRequestDto;
import springboot.onlinebookstore.dto.order.OrderResponseDto;
import springboot.onlinebookstore.dto.order.OrderStatusUpdateDto;
import springboot.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import springboot.onlinebookstore.exception.EntityNotFoundException;
import springboot.onlinebookstore.exception.NoShoppingCartException;
import springboot.onlinebookstore.mapper.order.OrderMapper;
import springboot.onlinebookstore.mapper.orderitem.OrderItemMapper;
import springboot.onlinebookstore.model.Order;
import springboot.onlinebookstore.model.OrderItem;
import springboot.onlinebookstore.model.ShoppingCart;
import springboot.onlinebookstore.model.User;
import springboot.onlinebookstore.repository.order.OrderRepository;
import springboot.onlinebookstore.repository.orderitem.OrderItemRepository;
import springboot.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import springboot.onlinebookstore.service.OrderService;
import springboot.onlinebookstore.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto createOrder(Long id, OrderRequestDto orderRequestDto) {
        User authenticatedUser = userService.getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartRepository
                .getUserById(authenticatedUser.getId())
                .orElseThrow(() -> {
                    log.error("Create order method failed. "
                            + "Can't find shopping cart by user ID: {}",
                            authenticatedUser.getId());
                    throw new NoShoppingCartException("Shopping cart not found by user");
                });
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setTotal(BigDecimal.ZERO);
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setStatus(Order.Status.NEW);
        Set<OrderItem> orderItems = getOrderItemsFromShoppingCart(shoppingCart, order);
        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        BigDecimal totalPrice = calculateTotalPrice(orderItems);
        order.setTotal(totalPrice);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> findAllOrdersByUserId(Long userId, Pageable pageable) {
        List<Order> orders = orderRepository.findAllOrdersByUserId(userId);
        return orders.stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = orderMapper.toDto(order);
                    Set<OrderItemResponseDto> orderItemResponseDtos
                            = order.getOrderItems().stream()
                            .map(orderItemMapper::toDto)
                            .collect(Collectors.toSet());
                    orderResponseDto.setOrderItems(orderItemResponseDtos);
                    return orderResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Set<OrderItemResponseDto> findAllOrderItemsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Find order items by id failed: {}",
                            findAllOrderItemsByOrderId(orderId));
                    throw new EntityNotFoundException("Can't find Order items by ID");
                });
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto findOrderItemById(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Find order item method failed. "
                                    + "Can't find order by ID: {}",
                            orderId);
                    throw new EntityNotFoundException("Can't find order by existing ID");
                });
        return order.getOrderItems().stream()
                .filter(o -> o.getId().equals(itemId))
                .findFirst()
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Find order item method failed. "
                            + "Can't find item by ID: {}", itemId);
                    throw new EntityNotFoundException("Can't find item by ID");
                });
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId,
            OrderStatusUpdateDto orderStatusUpdateDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Update order status method failed."
                            + "Can't find order using provided ID: {}", orderId);
                    throw new EntityNotFoundException("Can't find order using provided ID");
                });
        order.setStatus(orderStatusUpdateDto.getStatus());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    private Set<OrderItem> getOrderItemsFromShoppingCart(ShoppingCart shoppingCart, Order order) {
        return shoppingCart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setOrder(order);
                    orderItem.setPrice(cartItem.getBook().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                    return orderItem;
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
