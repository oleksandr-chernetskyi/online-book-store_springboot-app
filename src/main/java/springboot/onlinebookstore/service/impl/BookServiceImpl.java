package springboot.onlinebookstore.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.book.BookResponseDto;
import springboot.onlinebookstore.dto.book.CreateBookRequestDto;
import springboot.onlinebookstore.exception.EntityNotFoundException;
import springboot.onlinebookstore.exception.IllegalSpecificationArgumentException;
import springboot.onlinebookstore.mapper.BookMapper;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.repository.SpecificationManager;
import springboot.onlinebookstore.repository.book.BookRepository;
import springboot.onlinebookstore.service.BookService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationManager<Book> specificationManager;

    @Override
    public BookResponseDto save(CreateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public List<BookResponseDto> findAll(Pageable pageable) {
        log.info("Starting process to find all books...");
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookResponseDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> {
                    log.error("FindById method failed. Can't find book by id: {}", id);
                    return new EntityNotFoundException("Can't find book by id: " + id);
                }));
    }

    @Override
    public BookResponseDto update(Long id, CreateBookRequestDto createBookRequestDto) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(() -> {
            log.error("Update method failed. Can't update book by id: {}", id);
            return new EntityNotFoundException("Can't update book by id: " + id);
        });
        updatedBook.setTitle(createBookRequestDto.getTitle());
        updatedBook.setAuthor(createBookRequestDto.getAuthor());
        updatedBook.setIsbn(createBookRequestDto.getIsbn());
        updatedBook.setPrice(createBookRequestDto.getPrice());
        updatedBook.setDescription(createBookRequestDto.getDescription());
        updatedBook.setCoverImage(createBookRequestDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(updatedBook));
    }

    @Override
    public void deleteById(Long id) {
        log.info("Starting process to delete book by id: {}", id);
        Book deletedBook = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id));
        deletedBook.setDeleted(true);
        bookRepository.save(deletedBook);
    }

    @Override
    public List<BookResponseDto> findAllByParams(Map<String, String> parameters,
            Pageable pageable) {
        Specification<Book> specification = null;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            Specification<Book> spec = specificationManager.get(entry.getKey(),
                    entry.getValue().split(","));
            specification = specification == null
                    ? Specification.where(spec) : specification.and(spec);
        }
        if (specification == null) {
            log.error("FindAllByParams method failed. "
                    + "invalid combination of filterKey and value: {}", parameters);
            throw new IllegalSpecificationArgumentException(
                    "Invalid combination of filterKey and value");
        }
        return bookRepository.findAll(specification, pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
