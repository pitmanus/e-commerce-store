package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public void addCategory(CategoryDto categoryDto){
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
    }

    public List<CategoryDto> showAllCategories(){
        return categoryRepository.findAll()
                .stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }


}
