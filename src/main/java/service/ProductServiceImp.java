package service;

import model.Category;
import model.Product;
import util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductServiceImp implements IProductService {
    private Connection connection;
    private PreparedStatement statement;
    private static final String SELECT_ALL_PRODUCT = "SELECT * FROM product JOIN category on product.category_id = category.category_id;";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product JOIN category on product.category_id = category_id WHERE product_id = ?";
    private static final String SELECT_ALL_CATEGORY =  "SELECT * FROM category";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product(category_id, product_name, product_price, quantity, color, description) VALUES (?,?,?,?,?,?);";
    private static final String UPDATE_PRODUCT_SQL  = "UPDATE product SET category_id = ?, product_name = ?, product_price = ?, quantity = ?, color = ?, description = ? WHERE product_id = ?;";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product where product_id = ?";
    private static final String SELECT_PRODUCT_LIKE_NAME = "SELECT * FROM product JOIN category on product.category_id = category_id WHERE product_name LIKE ?;";


    public ProductServiceImp() {
        connection = Connector.getConnection();
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = parseResultSet(resultSet);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }


    @Override
    public Product getProductById(int id) {
        Product product = null;
        try {
            statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            product = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(SELECT_ALL_CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                category.setDescription(resultSet.getString("category_description"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public boolean addProduct(Product product) {
        int rowsAffect = 0;
             try {
            statement = connection.prepareStatement(INSERT_PRODUCT_SQL);
            setProduct(product);
            rowsAffect = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffect > -1;
    }

    @Override
    public boolean editProduct(int id, Product product) {
        int rowUpdated = 0;
        try {
            statement = connection.prepareStatement(UPDATE_PRODUCT_SQL );
            setProduct(product);
            statement.setInt(7, id);
            rowUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated > -1;
    }

    @Override
    public boolean deleteProduct(int id) {
        int rowDeleted = 0;
        try {
            statement = connection.prepareStatement(DELETE_PRODUCT_SQL);
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted > -1;
    }

    @Override
    public List<Product> searchProductByName(String name) {
        List<Product> productList = new LinkedList<>();
        try {
            statement = connection.prepareStatement(SELECT_PRODUCT_LIKE_NAME);
            statement.setString(1, name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                productList.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    private Product parseResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        Category category = new Category();
        product.setProductId(resultSet.getInt("product_id"));
        product.setName(resultSet.getString("product_name"));
        product.setPrice(resultSet.getInt("product_price"));
        product.setQuantity(resultSet.getInt("quantity"));
        product.setColor(resultSet.getString("color"));
        product.setDescription(resultSet.getString("description"));
        category.setId(resultSet.getInt("category_id"));
        category.setName(resultSet.getString("category_name"));
        category.setDescription(resultSet.getString("category_description"));
        product.setCategory(category);
        return product;
    }


    private void setProduct(Product product) throws SQLException {
        statement.setInt(1, product.getCategory().getId());
        statement.setString(2, product.getName());
        statement.setInt(3, product.getPrice());
        statement.setInt(4, product.getQuantity());
        statement.setString(5, product.getColor());
        statement.setString(6, product.getDescription());
    }
}
