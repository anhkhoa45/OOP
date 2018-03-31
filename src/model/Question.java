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
    private String categoryType;
    public ArrayList<String> getAnswerList(){   //get correct answer in db
        ArrayList<String> list= new ArrayList<String>();
        list.add("Cat");
        list.add("Dog");
        return list;
    }
    public boolean checkAnswer(Answer answer){
        String s= new String("Cat");
        if(answer.getAnswer().equals("Cat")){
            return true;
        }
        return false;
    }
}
