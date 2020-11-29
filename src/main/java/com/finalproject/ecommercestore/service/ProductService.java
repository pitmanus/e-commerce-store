package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.model.entity.Product;
import com.finalproject.ecommercestore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public Product addProduct(ProductDto productDto){
       return productRepository.save(modelMapper.map(productDto, Product.class));
    }

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }





}
