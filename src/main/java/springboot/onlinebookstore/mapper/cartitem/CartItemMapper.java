package springboot.onlinebookstore.mapper.cartitem;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import springboot.onlinebookstore.config.MapperConfig;
import springboot.onlinebookstore.dto.cartitem.CartItemResponseDto;
import springboot.onlinebookstore.model.CartItem;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    CartItemResponseDto toDto(CartItem cartItem);

    @AfterMapping
    default void setBookId(@MappingTarget CartItemResponseDto responseDto,
            CartItem cartItem) {
        responseDto.setBookId(cartItem.getBook().getId());
    }

    @AfterMapping
    default void setBookTitle(@MappingTarget CartItemResponseDto responseDto,
            CartItem cartItem) {
        responseDto.setBookTitle(String.valueOf(cartItem.getBook().getId()));
    }
}
