package wepa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userpage")
public class UserpageController {
    
    @RequestMapping
    public String viewUserpage(Model model) {
        return "userpage";
    }
    
}
