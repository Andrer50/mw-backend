package mweb.mw_backend.controller.frontend.auth;

import lombok.RequiredArgsConstructor;
import mweb.mw_backend.auth.LoginRequest;
import mweb.mw_backend.auth.RegisterRequest;
import mweb.mw_backend.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/auth")
@RequiredArgsConstructor
public class AuthWebController {
    
    private final AuthService authService;

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error,
                           @RequestParam(required = false) String logout,
                           Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Email o contraseña incorrectos");
        }
        
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión exitosamente");
        }
        
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login"; // templates/auth/login.html
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register"; // templates/auth/register.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest,
                          RedirectAttributes redirectAttributes) {
        
        try {
            authService.register(registerRequest);
            redirectAttributes.addFlashAttribute("success", 
                "Registro exitoso. Por favor inicia sesión.");
            return "redirect:/web/auth/login";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/web/auth/login?logout=true";
    }
}