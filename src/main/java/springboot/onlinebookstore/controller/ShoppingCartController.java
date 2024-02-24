package springboot.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.onlinebookstore.dto.cartitem.CartItemRequestDto;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.dto.cartitem.CountQuantityDto;
import springboot.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import springboot.onlinebookstore.service.CartItemService;
import springboot.onlinebookstore.service.ShoppingCartService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Shopping Cart Controller")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PostMapping
    @Operation(summary = "Add new item to shopping cart")
    public CartItemResponseDto cartItemResponseDto(@RequestBody @Valid
            CartItemRequestDto cartItemRequestDto) {
        return shoppingCartService.save(cartItemRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get shopping cart")
    public ShoppingCartResponseDto shoppingCartResponseDto() {
        return shoppingCartService.getShoppingCart();
    }

    @PutMapping("/cart-items/{id}")
    @Operation(summary = "Update cart item by ID")
    public CartItemResponseDto update(@RequestBody @Valid
            CountQuantityDto countQuantityDto, @PathVariable Long id) {
        return cartItemService.update(countQuantityDto, id);
    }

    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete cart item by ID")
    public void deleteCartItemById(@PathVariable Long id) {
        cartItemService.delete(id);
    }
}
