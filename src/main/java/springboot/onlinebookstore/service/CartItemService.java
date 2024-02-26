package springboot.onlinebookstore.service;

import java.util.Set;
import springboot.onlinebookstore.dto.cartitem.CartItemRequestDto;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.dto.cartitem.CountQuantityDto;

public interface CartItemService {
    CartItemResponseDto addItemToShoppingCart(CartItemRequestDto cartItemRequestDto);

    Set<CartItemResponseDto> findItemsByShoppingCartId(Long id);

    CartItemResponseDto updateCartItem(CountQuantityDto countQuantityDto, Long id);

    void delete(Long cartItemId);
}
