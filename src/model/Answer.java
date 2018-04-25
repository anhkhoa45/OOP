/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Answer {
    private String word;
    private int score;

    public Answer(String word){
        this.word = word;
    }

    public Answer(String word, int score){
        this.word = word;
        this.score = score;
    }

    public String getWord(){
        return this.word;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
