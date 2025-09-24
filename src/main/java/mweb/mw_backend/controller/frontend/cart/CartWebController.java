package mweb.mw_backend.controller.frontend.cart;

import lombok.RequiredArgsConstructor;
import mweb.mw_backend.entity.PurchaseCart;
import mweb.mw_backend.entity.User;
import mweb.mw_backend.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/cart")
@RequiredArgsConstructor
public class CartWebController {
    
    private final CartService cartService;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/web/auth/login";
        }
        
        PurchaseCart cart = cartService.getCartByUserId(user.getId());
        Double total = cart.calculateTotal();
        Integer totalItems = cart.getTotalItems();
        
        model.addAttribute("cart", cart);
        model.addAttribute("cartTotal", total);
        model.addAttribute("totalItems", totalItems);
        
        return "cart/view"; // templates/cart/view.html
    }

    @PostMapping("/add")
    public String addToCart(@AuthenticationPrincipal User user,
                           @RequestParam Long productId,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           RedirectAttributes redirectAttributes) {
        
        if (user == null) {
            return "redirect:/web/auth/login";
        }
        
        try {
            cartService.addItemToCart(user.getId(), productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Producto agregado al carrito");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/web/products/" + productId;
    }

    @PostMapping("/update/{itemId}")
    public String updateQuantity(@AuthenticationPrincipal User user,
                                @PathVariable Long itemId,
                                @RequestParam Integer quantity,
                                RedirectAttributes redirectAttributes) {
        
        if (user == null) {
            return "redirect:/web/auth/login";
        }
        
        try {
            cartService.updateItemQuantity(user.getId(), itemId, quantity);
            redirectAttributes.addFlashAttribute("success", "Cantidad actualizada");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/web/cart";
    }

    @PostMapping("/remove/{itemId}")
    public String removeItem(@AuthenticationPrincipal User user,
                            @PathVariable Long itemId,
                            RedirectAttributes redirectAttributes) {
        
        if (user == null) {
            return "redirect:/web/auth/login";
        }
        
        try {
            cartService.removeItemFromCart(user.getId(), itemId);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado del carrito");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/web/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal User user,
                           RedirectAttributes redirectAttributes) {
        
        if (user == null) {
            return "redirect:/web/auth/login";
        }
        
        cartService.clearCart(user.getId());
        redirectAttributes.addFlashAttribute("success", "Carrito limpiado");
        
        return "redirect:/web/cart";
    }
}