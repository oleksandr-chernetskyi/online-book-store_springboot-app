package springboot.onlinebookstore.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.book.BookDto;
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
    public BookDto save(CreateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        log.info("Starting process to find all books...");
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> {
                    log.error("FindById method failed. Can't find book by id: {}", id);
                    return new EntityNotFoundException("Can't find book by id: " + id);
                }));
    }

    @Override
    public BookDto update(BookDto bookDto, Long id) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(() -> {
            log.error("Update method failed. Can't update book by id: {}", id);
            return new EntityNotFoundException("Can't update book by id: " + id);
        });
        updatedBook.setTitle(bookDto.getTitle());
        updatedBook.setAuthor(bookDto.getAuthor());
        updatedBook.setIsbn(bookDto.getIsbn());
        updatedBook.setPrice(bookDto.getPrice());
        updatedBook.setDescription(bookDto.getDescription());
        updatedBook.setCoverImage(bookDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(updatedBook));
    }

    @Override
    public void deleteById(Long id) {
        log.info("Starting process to delete book by id: {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> findAllByParams(Map<String, String> parameters, Pageable pageable) {
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
