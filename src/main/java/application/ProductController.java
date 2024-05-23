package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.model.Product;
import application.service.ProductService;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/showProduct")
    public String showProduct(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "showProduct";
    }

    @GetMapping("/addProduct")
    public String addProductForm(Model model) {
        model.addAttribute("productData", new Product());
        return "addProduct";
    }
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("productData") @Valid Product product,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }

        // Check if the product name is already taken
        if (productService.isProductNameTaken(product.getProductName())) {
            // Set the model attribute for the error message
            model.addAttribute("productNameTaken", "Product name is already taken.");
            return "addProduct";
        }

        productService.addProduct(product);
        return "redirect:/showProduct";
    }

    @GetMapping("/updateProduct/{id}")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead', 'Producthead')")
    public String updateProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("productData", product);
        return "updateProduct";
    }

    @PostMapping("/updateProduct")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead', 'Producthead')")
    public String updateProduct(@ModelAttribute Product product) {
        if (product.getId() == null) {
            return "redirect:/error";
        }
        Product existingProduct = productService.getProductById(product.getId());
        if (existingProduct == null) {
            return "redirect:/error";
        }
        existingProduct.setProductName(product.getProductName());
        existingProduct.setPrice(product.getPrice());
        productService.updateProduct(existingProduct);
        return "redirect:/showProduct";
    }

    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead', 'Producthead')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the product.");
        }
    }

    @GetMapping("/api/productNames")
    @ResponseBody
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Figureshead', 'Producthead')")
    public ResponseEntity<List<String>> getProductNames() {
        List<String> productNames = productService.getProductNames();
        return ResponseEntity.ok().body(productNames);
    }
}
