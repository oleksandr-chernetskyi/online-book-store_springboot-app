package springboot.onlinebookstore.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.cartitem.CartItemRequestDto;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.dto.cartitem.CountQuantityDto;
import springboot.onlinebookstore.exception.EntityNotFoundException;
import springboot.onlinebookstore.mapper.cartitem.CartItemMapper;
import springboot.onlinebookstore.model.CartItem;
import springboot.onlinebookstore.model.ShoppingCart;
import springboot.onlinebookstore.model.User;
import springboot.onlinebookstore.repository.book.BookRepository;
import springboot.onlinebookstore.repository.cartitem.CartItemRepository;
import springboot.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import springboot.onlinebookstore.service.CartItemService;
import springboot.onlinebookstore.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemResponseDto save(CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(bookRepository.getById(cartItemRequestDto.getBookId()));
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        User user = userService.getAuthenticatedUser();
        setCartItemsAndShoppingCart(user, cartItem);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public Set<CartItemResponseDto> findItemsByShoppingCartId(Long id) {
        return cartItemRepository.findCartItemByShoppingCartId(id)
                .stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void setCartItemsAndShoppingCart(User user, CartItem cartItem) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .getUserById(user.getId())
                .orElseThrow(() -> {
                    log.error("setCartItemsAndShoppingCart method failed. "
                            + "Can't find cart item for user ID : {}", user.getId());
                    return new NoSuchElementException("Cart item not found by user ID");
                });
        cartItem.setShoppingCart(shoppingCart);
        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(cartItem);
        if (shoppingCart.getCartItems().isEmpty()) {
            shoppingCart.setCartItems(cartItems);
        } else {
            shoppingCart.getCartItems().add(cartItem);
        }
    }

    @Override
    public CartItemResponseDto update(CountQuantityDto countQuantityDto, Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Update method failed. Can't find cart item by ID: {}", id);
                    return new EntityNotFoundException("Can't find cart item by ID");
                });
        int newCountQuantity = countQuantityDto.getQuantity();
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long cartItemId) {
        cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> {
                    log.error("Delete method failed. Can't find cart by ID: {}", cartItemId);
                    return new EntityNotFoundException("Can't find cart by ID");
                });
    }
}
