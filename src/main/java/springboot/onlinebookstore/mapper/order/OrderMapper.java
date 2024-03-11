package springboot.onlinebookstore.mapper.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import springboot.onlinebookstore.config.MapperConfig;
import springboot.onlinebookstore.dto.order.OrderResponseDto;
import springboot.onlinebookstore.model.Order;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    @Mappings({@Mapping(target = "userId", source = "user.id"),
        @Mapping(target = "orderTime", source = "orderDate"),
        @Mapping(target = "totalPrice", source = "total")})
    OrderResponseDto toDto(Order order);
}
