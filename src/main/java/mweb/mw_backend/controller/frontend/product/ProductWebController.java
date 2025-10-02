package mweb.mw_backend.controller.frontend.product;

import mweb.mw_backend.controller.base.BaseController;
import mweb.mw_backend.entity.Category;
import mweb.mw_backend.entity.Product;
import mweb.mw_backend.entity.Review;
import mweb.mw_backend.repository.CategoryRepository;
import mweb.mw_backend.service.ProductService;
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
public class ProductWebController extends BaseController {
    
    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductWebController(CategoryRepository categoryRepository, 
                               ProductService productService, 
                               ReviewService reviewService) {
        super(categoryRepository);
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String productList(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "12") int size,
                             @RequestParam(required = false) Long categoryId,
                             @RequestParam(required = false) String search,
                             Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;
        
        // Filtrado combinado: categoría y búsqueda
        if (categoryId != null && search != null && !search.trim().isEmpty()) {
            products = productService.searchProductsByCategory(categoryId, search, pageable);
            model.addAttribute("selectedCategoryId", categoryId);
            model.addAttribute("searchTerm", search);
        } else if (categoryId != null) {
            products = productService.findProductsByCategory(categoryId, pageable);
            model.addAttribute("selectedCategoryId", categoryId);
        } else if (search != null && !search.trim().isEmpty()) {
            products = productService.searchProducts(search, pageable);
            model.addAttribute("searchTerm", search);
        } else {
            products = productService.findAllProducts(pageable);
        }
        
        // Información para la vista (las categorías ya se agregan automáticamente por BaseController)
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalElements", products.getTotalElements());
        model.addAttribute("pageSize", size);
        
        // Mantener parámetros para la paginación
        model.addAttribute("categoryParam", categoryId);
        model.addAttribute("searchParam", search);
        
        return "products/list"; // templates/products/list.html
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        
        List<Review> reviews = reviewService.getReviewsByProduct(id);
        Double averageRating = reviewService.getAverageRatingByProduct(id);
        Long reviewCount = reviewService.getReviewCountByProduct(id);
        
        // Productos relacionados de la misma categoría
        List<Product> relatedProducts = productService.findRelatedProducts(id, product.getCategory().getId());
        
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
        Page<Product> products = productService.findProductsByCategory(categoryId, pageable);
        
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        
        return "products/category"; // templates/products/category.html
    }
    
    /**
     * Endpoint para búsqueda AJAX (opcional para mejorar UX)
     */
    @GetMapping("/search")
    @ResponseBody
    public Page<Product> searchProductsAjax(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "12") int size,
                                           @RequestParam(required = false) Long categoryId,
                                           @RequestParam(required = false) String search) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        if (categoryId != null && search != null && !search.trim().isEmpty()) {
            return productService.searchProductsByCategory(categoryId, search, pageable);
        } else if (categoryId != null) {
            return productService.findProductsByCategory(categoryId, pageable);
        } else if (search != null && !search.trim().isEmpty()) {
            return productService.searchProducts(search, pageable);
        } else {
            return productService.findAllProducts(pageable);
        }
    }
}