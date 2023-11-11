package springboot.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import springboot.onlinebookstore.config.MapperConfig;
import springboot.onlinebookstore.dto.book.BookDto;
import springboot.onlinebookstore.dto.book.CreateBookRequestDto;
import springboot.onlinebookstore.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
