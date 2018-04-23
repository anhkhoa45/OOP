/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Ngoc
 */
public class Question {
    private int id;
    private int categoryType;
    private String value;

    public Question(int id, int categoryType, String value) {
        this.id = id;
        this.categoryType = categoryType;
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public ArrayList<String> getAnswerList(){   //get correct answer in db
        ArrayList<String> list= new ArrayList<String>();
        list.add("cat");
        list.add("dog");
        list.add("rat");
        list.add("house");
        list.add("bird");
        return list;
    }

    public int checkAnswer(Answer answer){
        if(this.getAnswerList().contains(answer.getAnswer().toLowerCase())){
            return 1;
        } else {
            return 0;
        }
    }
}
