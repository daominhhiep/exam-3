package controller;

import model.Category;
import model.Product;
import service.IProductService;
import service.ProductServiceImp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ProductServlet", urlPatterns = "/home")
public class ProductServlet extends HttpServlet {
    private IProductService service = new ProductServiceImp();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = null;
        String action = request.getParameter("action");
        List<Product> productList = service.getAllProduct();


        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                product = parseProduct(request);
                boolean result = service.addProduct(product);
                System.out.println(result);
                showProductList(request, response, productList);
                break;
            case "edit":
                product = parseProduct(request);
                result = service.editProduct(product.getProductId(), product);
                System.out.println(result);
                showProductList(request, response, productList);
                break;
            case "search":
                String input = request.getParameter("search-input");
                productList = service.searchProductByName(input);
                showProductList(request, response, productList);
                break;
            default:
                productList = service.getAllProduct();
                showProductList(request, response, productList);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = null;
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                product = new Product();
                showForm(request, response, product, action);
                break;
            case "edit":
                int productId = Integer.parseInt(request.getParameter("id"));
                product = service.getProductById(productId);
                showForm(request, response, product, action);
                break;
            case "delete":
                productId = Integer.parseInt(request.getParameter("id"));
                System.out.println(service.deleteProduct(productId));
                List<Product> productList = service.getAllProduct();
                showProductList(request, response, productList);
                break;
            default:
                productList = service.getAllProduct();
                showProductList(request, response, productList);
                break;
        }
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, Product product, String action) {
        List<Category> categoryList = service.getCategoryList();
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("product", product);
        request.setAttribute("action", action);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response, List<Product> productList) {
        request.setAttribute("productList", productList);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private Product parseProduct(HttpServletRequest request) {
        Product product;
        product = new Product();
        product.setProductId(Integer.parseInt(request.getParameter("product-id")));
        product.setName(request.getParameter("product-name"));
        product.setPrice(Integer.parseInt(request.getParameter("product-price")));
        product.setQuantity(Integer.parseInt(request.getParameter("product-quantity")));
        product.setColor(request.getParameter("product-color"));
        product.setDescription(request.getParameter("product-description"));
        Category category = new Category();
        category.setId(Integer.parseInt(request.getParameter("category-id")));
        product.setCategory(category);
        return product;
    }


}
