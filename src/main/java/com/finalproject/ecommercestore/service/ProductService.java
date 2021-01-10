package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.model.entity.Product;
import com.finalproject.ecommercestore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public Product saveProduct(ProductDto productDto){
       return productRepository.save(modelMapper.map(productDto, Product.class));
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public void addNewProduct(ProductDto productDto){
        Product product = saveProduct(productDto);

        MultipartFile productImage = productDto.getProductImage();

        try {
            byte[] bytes = productImage.getBytes();
            String name = product.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/product" + name)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editProduct(ProductDto productDto){
        Product product = saveProduct(productDto);

        MultipartFile productImage = productDto.getProductImage();

        if (!productImage.isEmpty())
            try {
                byte[] bytes = productImage.getBytes();
                String name = product.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/product" + name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File
                                ("src/main/resources/static/image/product" + name)));
                stream.write(bytes);
                stream.close();
                System.out.println("File updated");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByCategories(Long id){
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getProductCategories().stream().anyMatch(category -> category.getId()==id))
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByProductName(String productName){
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getName().toUpperCase().contains(productName.toUpperCase()))
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto getById(Long id){
        return productRepository.findById(id)
                .stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .findAny()
                .orElse(null);
    }

    public Product getProductById(Long id){
       return productRepository.findById(id).get();
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }





}
