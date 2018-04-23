/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ngoc
 */
public class Answer {
    private String answer;
    private int score;

    public Answer(String answer){
        this.answer = answer;
    }

    public Answer(String answer, int score){
        this.answer = answer;
        this.score = score;
    }

    public String getAnswer(){
        return this.answer;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
