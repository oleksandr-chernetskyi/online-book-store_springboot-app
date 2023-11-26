package springboot.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springboot.onlinebookstore.dto.book.BookWithoutCategoryIdsDto;
import springboot.onlinebookstore.dto.category.CategoryRequestDto;
import springboot.onlinebookstore.dto.category.CategoryResponseDto;
import springboot.onlinebookstore.service.BookService;
import springboot.onlinebookstore.service.CategoryService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
@Tag(name = "Category management", description = "Endpoints to managing categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    private String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null) ? authentication.getName() : "User unknown";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category", description = "Create a new book category")
    public CategoryResponseDto createCategory(
            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        log.info("User {} is created a new book: {}", getLoggedInUserName(), categoryRequestDto);
        return categoryService.save(categoryRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Get all existing categories")
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id", description = "get category by existing id")
    public CategoryResponseDto findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update category",
            description = "Search and Update existing category by id")
    public CategoryResponseDto update(@PathVariable Long id,
            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        log.info("User {} is updating category with Id {} :{}",
                getLoggedInUserName(), id, categoryRequestDto);
        return categoryService.update(id, categoryRequestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete category",
            description = "Search and Delete existing category by id")
    public void delete(@PathVariable Long id) {
        log.info("User is deleting category with Id {}: {}", getLoggedInUserName(), id);
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    public List<BookWithoutCategoryIdsDto> getBooksByCategoryId(
            @PathVariable Long id, Pageable pageable) {
        log.info("User {} get books for category by Id: {}", getLoggedInUserName(), id);
        return bookService.findAllByCategoryId(id, pageable);
    }
}
