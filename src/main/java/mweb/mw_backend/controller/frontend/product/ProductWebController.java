package mweb.mw_backend.controller.frontend.product;

import lombok.RequiredArgsConstructor;
import mweb.mw_backend.entity.Category;
import mweb.mw_backend.entity.Product;
import mweb.mw_backend.entity.Review;
import mweb.mw_backend.repository.CategoryRepository;
import mweb.mw_backend.repository.ProductRepository;
import mweb.mw_backend.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/products")
@RequiredArgsConstructor
public class ProductWebController {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewService reviewService;

    @GetMapping
    public String productList(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "12") int size,
                             @RequestParam(required = false) Long categoryId,
                             Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;
        
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId, pageable);
            model.addAttribute("selectedCategoryId", categoryId);
        } else {
            products = productRepository.findAll(pageable);
        }
        
        List<Category> categories = categoryRepository.findAll();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        
        return "products/list"; // templates/products/list.html
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        List<Review> reviews = reviewService.getReviewsByProduct(id);
        Double averageRating = reviewService.getAverageRatingByProduct(id);
        Long reviewCount = reviewService.getReviewCountByProduct(id);
        
        // Productos relacionados de la misma categoría
        List<Product> relatedProducts = productRepository
                .findByCategoryIdAndIdNot(product.getCategory().getId(), id, PageRequest.of(0, 4))
                .getContent();
        
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("relatedProducts", relatedProducts);
        
        return "products/detail"; // templates/products/detail.html
    }

    @GetMapping("/category/{categoryId}")
    public String productsByCategory(@PathVariable Long categoryId,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "12") int size,
                                   Model model) {
        
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
        
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        
        return "products/category"; // templates/products/category.html
    }
}