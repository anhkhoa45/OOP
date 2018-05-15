package model;

import java.util.*;

public class TopicFactory {
    private static List<Topic> topics = null;

    public static List<Topic> getTopics(){
        if(topics == null){
            topics = new ArrayList<>();
            HashMap<String, Integer> stationeryCorrectWords  = new HashMap<>();
            stationeryCorrectWords .put("鉛筆", 1);
            stationeryCorrectWords.put("消しゴム", 1);
            stationeryCorrectWords.put("ペン", 1);
            stationeryCorrectWords.put("ボールペン", 1);
            stationeryCorrectWords.put("シャープペンシル", 3);
            stationeryCorrectWords.put("チョーク", 2);
            stationeryCorrectWords.put("黒板", 1);
            stationeryCorrectWords.put("ホワイトボード", 3);
            stationeryCorrectWords.put("筆", 3);
            stationeryCorrectWords.put("紙", 1);
            stationeryCorrectWords.put("原稿用紙", 3);
            stationeryCorrectWords.put("色紙", 2);
            stationeryCorrectWords.put("折り紙", 2);
            stationeryCorrectWords.put("はさみ", 1);
            stationeryCorrectWords.put("のり", 3);
            stationeryCorrectWords.put("ホッチキス", 2);
            stationeryCorrectWords.put("ステープル", 2);
            stationeryCorrectWords.put("クリップ", 2);
            stationeryCorrectWords.put("本", 3);
            stationeryCorrectWords.put("教科書", 2);
            stationeryCorrectWords.put("クレヨン", 2);
            stationeryCorrectWords.put("ノート", 1);
            stationeryCorrectWords.put("辞書", 1);
            stationeryCorrectWords.put("電卓", 2);
            stationeryCorrectWords.put("輪ゴム", 3);
            stationeryCorrectWords.put("定規", 2);
            stationeryCorrectWords.put("コンパス", 1);
            stationeryCorrectWords.put("鉛筆削り", 3);
            topics.add(new Topic("学具", stationeryCorrectWords));
            
            HashMap<String, Integer> animalCorrectWords = new HashMap<>();
            animalCorrectWords.put("ネズミ", 1);
            animalCorrectWords.put("猫", 1);
            animalCorrectWords.put("犬", 1);
            animalCorrectWords.put("鶏", 1);
            animalCorrectWords.put("家鴨", 2);
            animalCorrectWords.put("鳥", 1);
            animalCorrectWords.put("スズメ", 3);
            animalCorrectWords.put("ハト", 2);
            animalCorrectWords.put("カラス", 2);
            animalCorrectWords.put("白鳥", 2);
            animalCorrectWords.put("梟", 2);
            animalCorrectWords.put("鷲", 3);
            animalCorrectWords.put("ウサギ", 1);
            animalCorrectWords.put("リス", 2);
            animalCorrectWords.put("猿", 2);
            animalCorrectWords.put("馬", 1);
            animalCorrectWords.put("牛", 1);
            animalCorrectWords.put("縞馬", 2);
            animalCorrectWords.put("羊", 1);
            animalCorrectWords.put("山羊", 2);
            animalCorrectWords.put("鹿", 2);
            animalCorrectWords.put("河馬", 2);
            animalCorrectWords.put("豚", 1);
            animalCorrectWords.put("パンダ", 1);
            animalCorrectWords.put("カンガルー", 2);
            animalCorrectWords.put("ラクダ", 3);
            animalCorrectWords.put("狐", 3);
            animalCorrectWords.put("狸", 3);
            animalCorrectWords.put("狼", 3);
            animalCorrectWords.put("虎", 3);
            animalCorrectWords.put("ライオン", 2);
            animalCorrectWords.put("犀", 3);
            animalCorrectWords.put("熊", 2);
            animalCorrectWords.put("象", 1);
            animalCorrectWords.put("蛇", 1);
            animalCorrectWords.put("鰐", 3);
            animalCorrectWords.put("亀", 2);
            animalCorrectWords.put("魚", 1);
            animalCorrectWords.put("エビ", 2);
            animalCorrectWords.put("カニ", 2);
            animalCorrectWords.put("タコ", 2);
            animalCorrectWords.put("ペンギン", 2);
            animalCorrectWords.put("海豚", 3);
            animalCorrectWords.put("サメ", 2);
            animalCorrectWords.put("虫", 1);
            animalCorrectWords.put("蟻", 2);
            animalCorrectWords.put("蚊", 1);
            animalCorrectWords.put("ゴキブリ", 2);
            animalCorrectWords.put("クモ", 3);
            animalCorrectWords.put("蛍", 2);
            animalCorrectWords.put("蝶蝶", 2);
            animalCorrectWords.put("蜂", 3);
            topics.add(new Topic("動物", animalCorrectWords));
            
            HashMap<String, Integer> jobCorrectWords = new HashMap<>();
            jobCorrectWords.put("医者", 1);
            jobCorrectWords.put("看護師", 1);
            jobCorrectWords.put("歯科医", 1);
            jobCorrectWords.put("科学者", 1);
            jobCorrectWords.put("美容師", 1);
            jobCorrectWords.put("先生", 1);
            jobCorrectWords.put("教師", 1);
            jobCorrectWords.put("歌手", 1);
            jobCorrectWords.put("運転手", 1);
            jobCorrectWords.put("野球選手", 2);
            jobCorrectWords.put("選手", 1);
            jobCorrectWords.put("サッカー選手", 2);
            jobCorrectWords.put("画家", 1);
            jobCorrectWords.put("芸術家", 3);
            jobCorrectWords.put("写真家", 3);
            jobCorrectWords.put("作家", 1);
            jobCorrectWords.put("演説家", 2);
            jobCorrectWords.put("演奏家", 2);
            jobCorrectWords.put("演出家", 3);
            jobCorrectWords.put("建築家", 1);
            jobCorrectWords.put("政治家", 1);
            jobCorrectWords.put("警官", 2);
            jobCorrectWords.put("警察官", 1);
            jobCorrectWords.put("コック", 2);
            jobCorrectWords.put("シェフ", 2);
            jobCorrectWords.put("料理長", 3);
            jobCorrectWords.put("裁判官", 3);
            jobCorrectWords.put("弁護士", 2);
            jobCorrectWords.put("調理師", 3);
            jobCorrectWords.put("会計士", 2);
            jobCorrectWords.put("消防士", 3);
            jobCorrectWords.put("兵士", 3);
            jobCorrectWords.put("銀行員", 1);
            jobCorrectWords.put("公務員", 1);
            jobCorrectWords.put("駅員", 1);
            jobCorrectWords.put("店員", 1);
            jobCorrectWords.put("会社員", 1);
            jobCorrectWords.put("研究者", 2);
            jobCorrectWords.put("警備員", 2);
            jobCorrectWords.put("秘書", 2);
            jobCorrectWords.put("サラリーマン", 1);
            jobCorrectWords.put("俳優", 1);
            jobCorrectWords.put("フリーター", 1);
            jobCorrectWords.put("女優", 1);
            jobCorrectWords.put("監督", 1);
            jobCorrectWords.put("講師", 2);
            jobCorrectWords.put("技師", 2);
            jobCorrectWords.put("教授", 2);
            jobCorrectWords.put("エンジニア", 1);
            jobCorrectWords.put("大工", 2);
            jobCorrectWords.put("探偵", 2);
            jobCorrectWords.put("スチュワーデス", 3);
            jobCorrectWords.put("パイロット", 3);
            jobCorrectWords.put("機長", 3);
            topics.add(new Topic("職業", jobCorrectWords));

            HashMap<String, Integer> fruitCorrectWords = new HashMap<>();
            fruitCorrectWords.put("バナナ", 1);
            fruitCorrectWords.put("西瓜", 1);
            fruitCorrectWords.put("苺", 3);
            fruitCorrectWords.put("林檎", 2);
            fruitCorrectWords.put("オレンジ", 1);
            fruitCorrectWords.put("梨", 3);
            fruitCorrectWords.put("桃", 1);
            fruitCorrectWords.put("レモン", 1);
            fruitCorrectWords.put("蜜柑", 3);
            fruitCorrectWords.put("梅", 1);
            fruitCorrectWords.put("マンゴー", 1);
            fruitCorrectWords.put("ラズベリー", 1);
            fruitCorrectWords.put("メロン", 1);
            fruitCorrectWords.put("グレープフルーツ", 1);
            fruitCorrectWords.put("パイナップル", 3);
            fruitCorrectWords.put("パッションフルーツ", 2);
            fruitCorrectWords.put("パパイヤ", 1);
            fruitCorrectWords.put("ブルーベリー", 3);
            fruitCorrectWords.put("ライチ", 1);
            fruitCorrectWords.put("ブドウ", 1);
            fruitCorrectWords.put("ドラゴンフルーツ", 3);
            fruitCorrectWords.put("クランベリー", 1);
            fruitCorrectWords.put("柿", 1);
            fruitCorrectWords.put("金柑", 1);
            topics.add(new Topic("果物", fruitCorrectWords));

            HashMap<String, Integer> fastfoodCorrectWords = new HashMap<>();
            fastfoodCorrectWords.put("スパゲッティ", 1);
            fastfoodCorrectWords.put("パスタ", 1);
            fastfoodCorrectWords.put("フライドチキン", 3);
            fastfoodCorrectWords.put("ピザ", 2);
            fastfoodCorrectWords.put("ハンバーガー", 1);
            fastfoodCorrectWords.put("サラダ", 3);
            fastfoodCorrectWords.put("ホットドッグ", 1);
            fastfoodCorrectWords.put("コカコーラ", 1);
            fastfoodCorrectWords.put("フレンチフライド", 3);
            fastfoodCorrectWords.put("フレンチドーナツ", 1);
            fastfoodCorrectWords.put("アイスクリーム", 1);
            fastfoodCorrectWords.put("うどん", 1);
            fastfoodCorrectWords.put("ラーメン", 3);
            fastfoodCorrectWords.put("カレーライス", 1);
            fastfoodCorrectWords.put("寿司", 1);
            fastfoodCorrectWords.put("牛丼", 1);
            fastfoodCorrectWords.put("たこ焼き", 3);
            fastfoodCorrectWords.put("お好み焼き", 1);
            fastfoodCorrectWords.put("お握り", 1);
            fastfoodCorrectWords.put("天丼", 1);
            fastfoodCorrectWords.put("そば", 3);
            fastfoodCorrectWords.put("かつ丼", 1);
            fastfoodCorrectWords.put("焼きそば", 1);
            fastfoodCorrectWords.put("スープ", 1);
            fastfoodCorrectWords.put("天ぷら", 3);
            topics.add(new Topic("牛丼", fastfoodCorrectWords));

            HashMap<String, Integer> provinceCorrectWords = new HashMap<>();
            provinceCorrectWords.put("あいち", 2);
            provinceCorrectWords.put("あきた", 2);
            provinceCorrectWords.put("あおもり", 2);
            provinceCorrectWords.put("ちば", 1);
            provinceCorrectWords.put("えひめ", 3);
            provinceCorrectWords.put("ふくい", 3);
            provinceCorrectWords.put("ふくおか", 2);
            provinceCorrectWords.put("ふくしま", 2);
            provinceCorrectWords.put("ぎふ", 3);
            provinceCorrectWords.put("ぐんま", 3);
            provinceCorrectWords.put("ひろしま", 1);
            provinceCorrectWords.put("ほっかいどう", 1);
            provinceCorrectWords.put("ひょうご", 3);
            provinceCorrectWords.put("いばらき", 3);
            provinceCorrectWords.put("いしかわ", 3);
            provinceCorrectWords.put("いわて", 3);
            provinceCorrectWords.put("かがわ", 3);
            provinceCorrectWords.put("かごしま", 3);
            provinceCorrectWords.put("かながわ", 2);
            provinceCorrectWords.put("こうち", 3);
            provinceCorrectWords.put("くまもと", 3);
            provinceCorrectWords.put("きょうと", 1);
            provinceCorrectWords.put("みえ", 2);
            provinceCorrectWords.put("みやぎ", 3);
            provinceCorrectWords.put("みやざき", 3);
            provinceCorrectWords.put("ながの", 2);
            provinceCorrectWords.put("ながさき", 2);
            provinceCorrectWords.put("なら", 1);
            provinceCorrectWords.put("にいがた", 3);
            provinceCorrectWords.put("おおいた", 3);
            provinceCorrectWords.put("おかやま", 3);
            provinceCorrectWords.put("おきなわ", 1);
            provinceCorrectWords.put("おおさか", 1);
            provinceCorrectWords.put("さが", 2);
            provinceCorrectWords.put("さいたま", 1);
            provinceCorrectWords.put("しが", 3);
            provinceCorrectWords.put("しまね", 3);
            provinceCorrectWords.put("しずおか", 2);
            provinceCorrectWords.put("とちぎ", 3);
            provinceCorrectWords.put("とくしま", 3);
            provinceCorrectWords.put("とうきょう", 1);
            provinceCorrectWords.put("とっとり", 2);
            provinceCorrectWords.put("とやま", 3);
            provinceCorrectWords.put("わかやま", 3);
            provinceCorrectWords.put("やまがた", 3);
            provinceCorrectWords.put("やまぐち", 2);
            provinceCorrectWords.put("やまなし", 2);
            topics.add(new Topic("日本の都市", provinceCorrectWords));

            HashMap<String, Integer> colorCorrectWords = new HashMap<>();
            colorCorrectWords.put("色", 1);
            colorCorrectWords.put("白", 1);
            colorCorrectWords.put("黒", 1);
            colorCorrectWords.put("赤", 1);
            colorCorrectWords.put("青", 1);
            colorCorrectWords.put("緑", 1);
            colorCorrectWords.put("紫", 1);
            colorCorrectWords.put("黄色", 1);
            colorCorrectWords.put("茶色", 1);
            colorCorrectWords.put("金色", 1);
            colorCorrectWords.put("銀色", 1);
            colorCorrectWords.put("桃色", 3);
            colorCorrectWords.put("ピンク", 1);
            colorCorrectWords.put("灰色", 1);
            colorCorrectWords.put("グレー", 1);
            colorCorrectWords.put("橙色", 1);
            colorCorrectWords.put("水色", 2);
            colorCorrectWords.put("紺色", 2);
            colorCorrectWords.put("藍色", 2);
            colorCorrectWords.put("藤色", 3);
            colorCorrectWords.put("焦げ茶色", 3);
            colorCorrectWords.put("黄土色", 1);
            colorCorrectWords.put("焼きそば", 1);
            colorCorrectWords.put("黄緑", 1);
            colorCorrectWords.put("深緑", 1);
            colorCorrectWords.put("ダークグリーン", 1);
            colorCorrectWords.put("ベージュ", 1);
            colorCorrectWords.put("朱色", 3);
            colorCorrectWords.put("銅色", 2);
            colorCorrectWords.put("ライラック", 2);
            colorCorrectWords.put("ターコイズ", 2);
            topics.add(new Topic("色", colorCorrectWords));

            HashMap<String, Integer> natureCorrectWords = new HashMap<>();
            natureCorrectWords.put("自然", 1);
            natureCorrectWords.put("景色", 1);
            natureCorrectWords.put("宇宙", 3);
            natureCorrectWords.put("地震", 3);
            natureCorrectWords.put("津波", 2);
            natureCorrectWords.put("林", 1);
            natureCorrectWords.put("森", 1);
            natureCorrectWords.put("木", 1);
            natureCorrectWords.put("枝", 1);
            natureCorrectWords.put("花", 1);
            natureCorrectWords.put("葉", 1);
            natureCorrectWords.put("草", 1);
            natureCorrectWords.put("畑", 2);
            natureCorrectWords.put("空", 1);
            natureCorrectWords.put("空気", 1);
            natureCorrectWords.put("太陽", 1);
            natureCorrectWords.put("月", 1);
            natureCorrectWords.put("星", 1);
            natureCorrectWords.put("雨", 1);
            natureCorrectWords.put("風", 1);
            natureCorrectWords.put("雷", 1);
            natureCorrectWords.put("雲", 1);
            natureCorrectWords.put("雪", 1);
            natureCorrectWords.put("石", 1);
            natureCorrectWords.put("岩", 3);
            natureCorrectWords.put("砂", 1);
            natureCorrectWords.put("土", 1);
            natureCorrectWords.put("山", 1);
            natureCorrectWords.put("火山", 3);
            natureCorrectWords.put("丘", 2);
            natureCorrectWords.put("谷", 2);
            natureCorrectWords.put("崖", 2);
            natureCorrectWords.put("洞窟", 2);
            natureCorrectWords.put("島", 1);
            natureCorrectWords.put("水", 1);
            natureCorrectWords.put("海", 1);
            natureCorrectWords.put("山", 1);
            natureCorrectWords.put("川", 3);
            natureCorrectWords.put("河", 2);
            natureCorrectWords.put("波", 2);
            natureCorrectWords.put("湖", 2);
            natureCorrectWords.put("池", 1);
            natureCorrectWords.put("井", 2);
            natureCorrectWords.put("井戸", 3);
            natureCorrectWords.put("温泉", 3);
            natureCorrectWords.put("滝", 2);
            natureCorrectWords.put("泉", 2);
            topics.add(new Topic("自然", natureCorrectWords));
        }
        return topics;
    }

    public static Topic getRandomTopic(){
        return getTopics().get(new Random().nextInt(getTopics().size()));
    }
}
