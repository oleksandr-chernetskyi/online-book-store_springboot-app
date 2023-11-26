package springboot.onlinebookstore.mapper.book;

import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import springboot.onlinebookstore.config.MapperConfig;
import springboot.onlinebookstore.dto.book.BookResponseDto;
import springboot.onlinebookstore.dto.book.BookWithoutCategoryIdsDto;
import springboot.onlinebookstore.dto.book.CreateBookRequestDto;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.model.Category;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    BookWithoutCategoryIdsDto toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookResponseDto responseDto, Book book) {
        responseDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }
}
