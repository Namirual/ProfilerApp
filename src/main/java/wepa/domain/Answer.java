/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.domain;

/**
 *
 * @author lmantyla
 */
public class Answer {
    String sender;
    String answer;

    public Answer() {
    }

    public String getSender() {
        return sender;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
 
