package springboot.onlinebookstore.service;

import springboot.onlinebookstore.dto.cartitem.CartItemRequestDto;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto);

    ShoppingCartResponseDto getShoppingCart();
}
