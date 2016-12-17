/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lmantyla
 */
public class Question {
    
    String question;
    List<String> answers;

    public Question(String question) {
        this.question = question;
        this.answers = new ArrayList<String>();
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    
    
}
