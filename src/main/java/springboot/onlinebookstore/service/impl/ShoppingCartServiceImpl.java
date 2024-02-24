package springboot.onlinebookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.cartitem.CartItemRequestDto;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import springboot.onlinebookstore.exception.NoShoppingCartException;
import springboot.onlinebookstore.model.ShoppingCart;
import springboot.onlinebookstore.model.User;
import springboot.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import springboot.onlinebookstore.service.CartItemService;
import springboot.onlinebookstore.service.ShoppingCartService;
import springboot.onlinebookstore.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final CartItemService cartItemService;

    @Override
    public CartItemResponseDto save(CartItemRequestDto cartItemRequestDto) {
        return cartItemService.save(cartItemRequestDto);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        User authenticatedUser = userService.getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartRepository
                .getUserById(authenticatedUser.getId())
                .orElseThrow(() -> {
                    log.error("getShoppingCart method failed. Can't find shopping cart: {}",
                            authenticatedUser.getId());
                    return new NoShoppingCartException("Shopping cart not found for user");
                });
        Long id = shoppingCart.getId();
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setId(id);
        shoppingCartResponseDto.setUserId(authenticatedUser.getId());
        shoppingCartResponseDto.setCartItems(cartItemService
                .findItemsByShoppingCartId(id));
        return shoppingCartResponseDto;
    }
}
