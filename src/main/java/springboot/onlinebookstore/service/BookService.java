package springboot.onlinebookstore.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import springboot.onlinebookstore.dto.book.BookDto;
import springboot.onlinebookstore.dto.book.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(BookDto bookDto, Long id);

    void deleteById(Long id);

    List<BookDto> findAllByParams(Map<String, String> parameters, Pageable pageable);
}
