package wepa.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.service.QuestionService;

@Controller
@RequestMapping("/createquestion")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public String viewCreateQuestion(Model model) {
        // Get the user authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //model.addAttribute("questions", questionService.findAllQuestions());
        return "create_question";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public String postNewQuestion(@RequestParam String question, @RequestParam List<String> answer) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        questionService.createQuestion(question, answer);

        return "redirect:/userpage";
    }
}
