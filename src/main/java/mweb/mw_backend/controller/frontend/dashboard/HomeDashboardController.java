package mweb.mw_backend.controller.frontend.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeDashboardController {

    @GetMapping("/dashboard")
    public String home(Model model) {
        return "dashboard";
    }
}
