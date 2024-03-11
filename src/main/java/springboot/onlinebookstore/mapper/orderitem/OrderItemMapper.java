package springboot.onlinebookstore.mapper.orderitem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import springboot.onlinebookstore.config.MapperConfig;
import springboot.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import springboot.onlinebookstore.model.OrderItem;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mappings({@Mapping(target = "bookId", source = "book.id")})
    OrderItemResponseDto toDto(OrderItem orderItem);
}
