package springboot.onlinebookstore.mapper.category;

import org.mapstruct.Mapper;
import springboot.onlinebookstore.config.MapperConfig;
import springboot.onlinebookstore.dto.category.CategoryRequestDto;
import springboot.onlinebookstore.dto.category.CategoryResponseDto;
import springboot.onlinebookstore.model.Category;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toModel(CategoryRequestDto requestDto);
}
