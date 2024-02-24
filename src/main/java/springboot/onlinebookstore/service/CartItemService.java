package springboot.onlinebookstore.service;

import java.util.Set;
import springboot.onlinebookstore.dto.cartitem.CartItemRequestDto;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.dto.cartitem.CountQuantityDto;
import springboot.onlinebookstore.model.CartItem;
import springboot.onlinebookstore.model.User;

public interface CartItemService {
    CartItemResponseDto save(CartItemRequestDto cartItemRequestDto);

    Set<CartItemResponseDto> findItemsByShoppingCartId(Long id);

    void setCartItemsAndShoppingCart(User user, CartItem cartItem);

    CartItemResponseDto update(CountQuantityDto countQuantityDto, Long id);

    void delete(Long cartItemId);
}
