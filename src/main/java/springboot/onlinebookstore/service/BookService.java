package springboot.onlinebookstore.service;

import java.util.List;
import springboot.onlinebookstore.dto.BookDto;
import springboot.onlinebookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
