package service;

import model.Category;
import model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProduct();

    Product getProductById(int id);

    List<Category> getCategoryList();

    boolean addProduct(Product product);

    boolean editProduct(int id, Product product);

    boolean deleteProduct(int id);

    List<Product> searchProductByName(String name);
}