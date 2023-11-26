package springboot.onlinebookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.onlinebookstore.dto.category.CategoryRequestDto;
import springboot.onlinebookstore.dto.category.CategoryResponseDto;
import springboot.onlinebookstore.exception.EntityNotFoundException;
import springboot.onlinebookstore.mapper.category.CategoryMapper;
import springboot.onlinebookstore.model.Category;
import springboot.onlinebookstore.repository.category.CategoryRepository;
import springboot.onlinebookstore.service.CategoryService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        return categoryMapper.toDto(categoryRepository
                .save(categoryMapper.toModel(categoryRequestDto)));
    }

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        Category categoryById = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id));
        categoryById.setName(categoryRequestDto.getName());
        categoryById.setDescription(categoryRequestDto.getDescription());
        return categoryMapper.toDto(categoryRepository.save(categoryById));
    }

    @Override
    public void deleteById(Long id) {
        Category categoryDeleted = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id));
        categoryDeleted.setDeleted(true);
        categoryRepository.save(categoryDeleted);
    }
}
