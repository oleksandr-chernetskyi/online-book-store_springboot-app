package springboot.onlinebookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import springboot.onlinebookstore.dto.category.CategoryRequestDto;
import springboot.onlinebookstore.dto.category.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto findById(Long id);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto);

    void deleteById(Long id);
}
