package springboot.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springboot.onlinebookstore.dto.book.BookResponseDto;
import springboot.onlinebookstore.dto.book.CreateBookRequestDto;
import springboot.onlinebookstore.service.BookService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books")
@Tag(name = "Book management", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    private String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null) ? authentication.getName() : "User unknown";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new book", description = "Create a new book")
    public BookResponseDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        log.info("User {} is created a new book: {}", getLoggedInUserName(), requestDto.getTitle());
        return bookService.save(requestDto);
    }

    @GetMapping
    @Operation(summary = "Get all books",
            description = "Get a list of all available books")
    public List<BookResponseDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id",
            description = "Get existing book by id")
    public BookResponseDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update book",
            description = "Search and Update existing book by id")
    public BookResponseDto update(@PathVariable Long id,
            @RequestBody @Valid CreateBookRequestDto createBookRequestDto) {
        log.info("User {} is updating book with Id {}: {}", getLoggedInUserName(),
                id, createBookRequestDto.getTitle());
        return bookService.update(id, createBookRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book by id",
            description = "Search and Delete existing book by id")
    public void delete(@PathVariable Long id) {
        log.info("User is deleting book with Id {}: {}", getLoggedInUserName(), id);
        bookService.deleteById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Book search",
            description = "Search for a book using specified parameters")
    public List<BookResponseDto> searchBooks(@RequestParam Map<String, String> parameters,
            Pageable pageable) {
        return bookService.findAllByParams(parameters, pageable);
    }
}
