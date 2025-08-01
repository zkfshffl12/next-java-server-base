package com.next.app.api.user.service;

import com.next.app.api.user.entity.Product;
import com.next.app.api.user.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 상품 전체 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // 상품 단건 조회
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    // 상품 생성
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    // 상품 수정
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setDescription(updatedProduct.getDescription());
        return productRepository.save(product);
    }
    // 상품 삭제
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
