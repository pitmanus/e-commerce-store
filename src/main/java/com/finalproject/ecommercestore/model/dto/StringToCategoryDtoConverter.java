package com.finalproject.ecommercestore.model.dto;

import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.repository.CategoryRepository;
import com.finalproject.ecommercestore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringToCategoryDtoConverter implements Converter<String, CategoryDto> {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public StringToCategoryDtoConverter(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDto convert(String id) {
        List<CategoryDto> categoryDtoList = categoryRepository.findAll()
                .stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        if(id.equals(""))
            return null;

        long parsedId = Long.parseLong(id);
        return categoryDtoList
                .stream()
                .filter(categoryDto -> categoryDto.getId().equals(parsedId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + parsedId + " not found"));
    }
}
