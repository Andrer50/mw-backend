package mweb.mw_backend.controller.frontend.home;

import lombok.RequiredArgsConstructor;
import mweb.mw_backend.entity.Category;
import mweb.mw_backend.entity.Product;
import mweb.mw_backend.repository.CategoryRepository;
import mweb.mw_backend.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Obtener productos destacados (los más recientes)
        List<Product> featuredProducts = productRepository.findTop8ByOrderByIdDesc();
        
        // Obtener todas las categorías
        List<Category> categories = categoryRepository.findAll();
        
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("categories", categories);
        
        return "index"; // templates/index.html
    }
}