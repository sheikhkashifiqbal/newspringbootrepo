package com.car.carservices.service.impl;

import com.car.carservices.dto.CategoryDTO;
import com.car.carservices.entity.Category;
import com.car.carservices.mapper.CategoryMapper;
import com.car.carservices.repository.CategoryRepository;
import com.car.carservices.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper mapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category category = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(category));
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = repository.findById(id).orElseThrow();
        return mapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category category = repository.findById(id).orElseThrow();
        category.setCategoryName(dto.getCategoryName());
        return mapper.toDTO(repository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}