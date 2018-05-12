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
            animalCorrectWords.put("horse", 2);
            animalCorrectWords.put("bird", 1);
            animalCorrectWords.put("crocodile", 3);
            topics.add(new Topic("Animal", animalCorrectWords));

            HashMap<String, Integer> databaseCorrectWords = new HashMap<>();
            databaseCorrectWords.put("整理する", 1);
            databaseCorrectWords.put("抽出する", 1);
            databaseCorrectWords.put("データ構造", 1);
            databaseCorrectWords.put("階層型データベース", 3);
            databaseCorrectWords.put("子レコード", 2);
            databaseCorrectWords.put("親レコード", 1);
            databaseCorrectWords.put("関係データベース", 3);
            databaseCorrectWords.put("表", 1);
            databaseCorrectWords.put("テーブル", 1);
            databaseCorrectWords.put("行", 1);
            databaseCorrectWords.put("レコード", 1);
            topics.add(new Topic("データベース", databaseCorrectWords));

            topics.add(new Topic("データベース 1", databaseCorrectWords));

            topics.add(new Topic("データベース 2", databaseCorrectWords));
        }

        return topics;
    }

    public static Topic getRandomTopic(){
        return getTopics().get(new Random().nextInt(getTopics().size()));
    }
}
