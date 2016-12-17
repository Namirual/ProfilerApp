/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.controller;

/**
 *
 * @author lmantyla
 */
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.domain.Answer;
import wepa.domain.Question;

@Controller
public class ViewController {

    List<String> vastaukset = new ArrayList<>();

    @RequestMapping(value = "/view/", method = RequestMethod.GET)
    public String getView(Model model) {

        return "redirect:/view/1";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String getView(Model model, @PathVariable String id) {

        int idNum = Integer.parseInt(id);

        if (idNum < 1) {
            return "redirect:/view/1";
        }

        Question question1 = new Question("Ikä?");
        question1.getAnswers().add("10–15");
        question1.getAnswers().add("16–20");
        question1.getAnswers().add("21–25");

        Question question2 = new Question("Henkinen ikä?");
        question2.getAnswers().add("10–20");
        question2.getAnswers().add("20–30");
        question2.getAnswers().add("30–40");

        Question question3 = new Question("Alkuperä?");
        question3.getAnswers().add("Murrica");
        question3.getAnswers().add("Yurop");
        question3.getAnswers().add("Azn");

        List questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        if (idNum < 10) {
            model.addAttribute("next", idNum + 1);
        }

        if (idNum > 1) {
            model.addAttribute("previous", idNum - 1);
        }

        while (vastaukset.size() <= idNum) {
            vastaukset.add("ei vastattu");
        }

        model.addAttribute("questions", questions);
        model.addAttribute("vastaukset", vastaukset.get(idNum));
        return "view";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.POST, consumes = "application/json")
    public String postResult(@RequestBody Answer body, @PathVariable String id) {
        vastaukset.set(Integer.parseInt(id), body.getAnswer());
        return "";
    }
}
