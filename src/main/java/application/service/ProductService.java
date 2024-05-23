package application.service;

import java.util.List;

import application.model.Product;

public interface ProductService {

    Product getProductById(Long id);
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long id);
    List<String> getProductNames();
    double getProductPrice(String productName);
    List<Product> getAllProducts();
    boolean isProductNameTaken(String productName);
}
