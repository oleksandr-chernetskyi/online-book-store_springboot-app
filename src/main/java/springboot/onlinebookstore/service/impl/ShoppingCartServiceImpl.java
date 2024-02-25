package springboot.onlinebookstore.service.impl;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.cartitem.CartItemRequestDto;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import springboot.onlinebookstore.exception.BookNotFoundException;
import springboot.onlinebookstore.exception.NoShoppingCartException;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.model.CartItem;
import springboot.onlinebookstore.model.ShoppingCart;
import springboot.onlinebookstore.model.User;
import springboot.onlinebookstore.repository.book.BookRepository;
import springboot.onlinebookstore.repository.cartitem.CartItemRepository;
import springboot.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import springboot.onlinebookstore.service.ShoppingCartService;
import springboot.onlinebookstore.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto) {
        User authenticatedUser = userService.getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartRepository
                .getUserById(authenticatedUser.getId())
                .orElseThrow(() -> {
                    log.error("addCartItem method failed. Can't find shopping cart: {}",
                            authenticatedUser.getId());
                    throw new NoShoppingCartException("Shopping cart not found for user");
                });
        Book book = bookRepository.findById(cartItemRequestDto.getBookId())
                .orElseThrow(() -> {
                    log.error("Can't found book by id: {}", cartItemRequestDto.getBookId());
                    throw new BookNotFoundException("Book not found with id");
                });

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return mapToCartItemResponseDto(savedCartItem);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        User authenticatedUser = userService.getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartRepository
                .getUserById(authenticatedUser.getId())
                .orElseThrow(() -> {
                    log.error("getShoppingCart method failed. Can't find shopping cart: {}",
                            authenticatedUser.getId());
                    throw new NoShoppingCartException("Shopping cart not found for user");
                });
        Long id = shoppingCart.getId();
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setId(id);
        shoppingCartResponseDto.setUserId(authenticatedUser.getId());
        shoppingCartResponseDto.setCartItems(cartItemRepository
                .findCartItemByShoppingCartId(id)
                .stream()
                .map(this::mapToCartItemResponseDto)
                .collect(Collectors.toSet()));
        return shoppingCartResponseDto;
    }

    private CartItemResponseDto mapToCartItemResponseDto(CartItem cartItem) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setId(cartItem.getId());
        cartItemResponseDto.setBookId(cartItem.getBook().getId());
        cartItemResponseDto.setBookTitle(cartItem.getBook().getTitle());
        cartItemResponseDto.setQuantity(cartItem.getQuantity());
        return cartItemResponseDto;
    }
}
