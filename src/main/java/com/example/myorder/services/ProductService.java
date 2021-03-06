package com.example.myorder.services;

import com.example.myorder.api.dtos.CreateProductDto;
import com.example.myorder.api.dtos.ProductResponseDto;
import com.example.myorder.api.mappers.ProductMapper;
import com.example.myorder.api.mappers.RestaurantMapper;
import com.example.myorder.entities.Product;
import com.example.myorder.entities.Restaurant;
import com.example.myorder.exception.NotFoundException;
import com.example.myorder.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestaurantService restaurantService;

    public ProductResponseDto create(CreateProductDto createProductDto){
        Product p1 = createProduct(createProductDto);
        productRepository.save(p1);

        return ProductMapper.toResponseDto(p1);
    }

    public ProductResponseDto createProductResponseDto(Product product, Restaurant restaurant){
        return new ProductResponseDto()
                .setName(product.getName())
                .setRestaurant(RestaurantMapper.toResponseDto(restaurant));
    }

    public Product createProduct(CreateProductDto productDto){

        return new Product()
                .setName(productDto.getName())
                .setValue(productDto.getValue())
                .setRestaurant(restaurantService.findById(productDto.getRestaurantId()));
    }

    public Product findById(Integer id){
        return productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Produto não encontrado"));
                // TODO: estudar lambdas expressions no javinha
    }

    public List<ProductResponseDto> listAll(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(ProductMapper::toResponseDto).collect(Collectors.toList());
    }


}
