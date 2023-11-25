package springboot.onlinebookstore.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import springboot.onlinebookstore.dto.book.BookResponseDto;
import springboot.onlinebookstore.dto.book.CreateBookRequestDto;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto requestDto);

    List<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto findById(Long id);

    BookResponseDto update(Long id, CreateBookRequestDto createBookRequestDto);

    void deleteById(Long id);

    List<BookResponseDto> findAllByParams(Map<String, String> parameters,
            Pageable pageable);
}
