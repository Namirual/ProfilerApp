package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.domain.AnswerOption;
import wepa.domain.Question;
import wepa.repository.AccountRepository;
import wepa.service.AdminService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.GET)

    public String showUsers(Model model) {
        model.addAttribute("accounts", adminService.getAllUsers());

        return "admin";
    }
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String showUserDetails(@PathVariable String id, Model model) {
        model.addAttribute("account", adminService.getUserById(id));
        model.addAttribute("profile", adminService.getProfileByUserId(id));
        model.addAttribute("questions", adminService.getQuestionsAndAnswersForProfileByUserId(id));
        return "userDetailsForAdmin";
    }
}