package model;

import java.util.*;

public class TopicFactory {
    private static List<Topic> topics = null;

    public static List<Topic> getTopics(){
        if(topics == null){
            topics = new ArrayList<>();
            HashMap<String, Integer> animalCorrectWords = new HashMap<>();
            animalCorrectWords.put("cat", 1);
            animalCorrectWords.put("dog", 1);
            animalCorrectWords.put("rat", 1);
            animalCorrectWords.put("mosquito", 3);
            topics.add(new Topic("Animal", animalCorrectWords));
        }

        return topics;
    }

    public static Topic getRandomTopic(){
        return getTopics().get(new Random().nextInt(topics.size()));
        
    }
}
