/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Topic {
    private String content;
    public Map<String, Integer> correctWords = new HashMap<>();

    public Topic(String content, HashMap<String, Integer> correctWords) {
        this.content = content;
        this.correctWords = correctWords;
    }

    public String getValue(){
        return this.content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Integer> getCorrectWords() {
        return correctWords;
    }

    public void setCorrectWords(Map<String, Integer> correctWords) {
        this.correctWords = correctWords;
    }

    public int getWordScore(String word){
        if (this.getCorrectWords().containsKey(word.toLowerCase())){
            return this.getCorrectWords().get(word.toLowerCase());
        } else {
            return 0;
        }
    }
}
