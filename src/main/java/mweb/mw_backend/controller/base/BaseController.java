package mweb.mw_backend.controller.base;

import lombok.RequiredArgsConstructor;
import mweb.mw_backend.entity.Category;
import mweb.mw_backend.repository.CategoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * Controlador base que proporciona datos comunes para todas las páginas
 */
@RequiredArgsConstructor
public abstract class BaseController {

    protected final CategoryRepository categoryRepository;

    /**
     * Agrega atributos globales que necesita el navbar en todas las páginas
     */
    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        // Categorías para el dropdown del navbar
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        // Información de autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && 
                                !auth.getName().equals("anonymousUser");
        String username = (isAuthenticated && auth != null) ? auth.getName() : null;
        
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("username", username);

        // Contador del carrito (TODO: implementar lógica real)
        int cartCount = 0; // Por ahora estático, implementar con servicio de carrito
        model.addAttribute("cartCount", cartCount);
    }
}