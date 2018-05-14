package model;

import java.util.*;

public class TopicFactory {
    private static List<Topic> topics = null;

    public static List<Topic> getTopics(){
        if(topics == null){
            topics = new ArrayList<>();
//            HashMap<String, Integer> animalCorrectWords = new HashMap<>();
//            animalCorrectWords.put("cat", 1);
//            animalCorrectWords.put("dog", 1);
//            animalCorrectWords.put("rat", 1);
//            animalCorrectWords.put("mosquito", 3);
//            animalCorrectWords.put("horse", 2);
//            animalCorrectWords.put("bird", 1);
//            animalCorrectWords.put("crocodile", 3);
//            animalCorrectWords.put("snake", 1);
//            animalCorrectWords.put("buffalo", 1);
//            animalCorrectWords.put("fish", 1);
//            animalCorrectWords.put("lion", 3);
//            animalCorrectWords.put("elephant", 2);
//            animalCorrectWords.put("tiger", 1);
//            animalCorrectWords.put("panda", 3);
//            topics.add(new Topic("Animal", animalCorrectWords));

            HashMap<String, Integer> databaseCorrectWords = new HashMap<>();
            databaseCorrectWords.put("整理する", 1);
            databaseCorrectWords.put("抽出する", 1);
            databaseCorrectWords.put("データ構造", 1);
            databaseCorrectWords.put("ネットワーク型データベース", 1);
            databaseCorrectWords.put("階層型データベース", 3);
            databaseCorrectWords.put("子レコード", 2);
            databaseCorrectWords.put("親レコード", 1);
            databaseCorrectWords.put("関係データベース", 3);
            databaseCorrectWords.put("表", 1);
            databaseCorrectWords.put("テーブル", 1);
            databaseCorrectWords.put("行", 1);
            databaseCorrectWords.put("主キー", 1);
            databaseCorrectWords.put("外部キー", 1);
            databaseCorrectWords.put("レコード", 1);
            databaseCorrectWords.put("射影", 1);
            databaseCorrectWords.put("結合", 1);
            topics.add(new Topic("データベース", databaseCorrectWords));
            
            HashMap<String, Integer> hardwareCorrectWords = new HashMap<>();
            hardwareCorrectWords.put("入力装置", 1);
            hardwareCorrectWords.put("記憶装置", 1);
            hardwareCorrectWords.put("制御装置", 3);
            hardwareCorrectWords.put("演算装置", 2);
            hardwareCorrectWords.put("出力装置", 1);
            hardwareCorrectWords.put("メモリ", 3);
            hardwareCorrectWords.put("液晶ディスプレイ", 1);
            hardwareCorrectWords.put("キーボード", 1);
            hardwareCorrectWords.put("マウス", 1);
            hardwareCorrectWords.put("クロック周波数", 1);
            hardwareCorrectWords.put("磁気ディスク", 1);
            hardwareCorrectWords.put("光ディスク", 1);
            hardwareCorrectWords.put("フラッシュメモリ", 1);
            hardwareCorrectWords.put("プロセッサ", 3);
            hardwareCorrectWords.put("主記憶", 2);
            hardwareCorrectWords.put("補助記憶", 1);
            hardwareCorrectWords.put("半導体メモリ", 3);
            hardwareCorrectWords.put("記憶媒体", 1);
            hardwareCorrectWords.put("ディスプレイ", 1);
            hardwareCorrectWords.put("タッチパネル", 1);
            hardwareCorrectWords.put("周辺機器", 1);
            topics.add(new Topic("ハードウェア", hardwareCorrectWords));
            
            HashMap<String, Integer> softwareCorrectWords = new HashMap<>();
            softwareCorrectWords.put("基本ソフトウェア", 1);
            softwareCorrectWords.put("応用ソフトウェア", 1);
            softwareCorrectWords.put("ミドルウェア", 3);
            softwareCorrectWords.put("オペレーティングシステム", 2);
            softwareCorrectWords.put("制御プログラム", 1);
            softwareCorrectWords.put("言語プロセッサ", 3);
            softwareCorrectWords.put("サービスプログラム", 1);
            softwareCorrectWords.put("核", 1);
            softwareCorrectWords.put("ファイル", 1);
            softwareCorrectWords.put("テキストエディタ", 1);
            softwareCorrectWords.put("アプリケーション", 1);
            softwareCorrectWords.put("デバイスドライバ", 1);
            softwareCorrectWords.put("インストールする", 1);
            softwareCorrectWords.put("音声", 3);
            softwareCorrectWords.put("動画", 2);
            softwareCorrectWords.put("画像", 1);
            softwareCorrectWords.put("フォルダ", 3);
            softwareCorrectWords.put("ディレクトリ", 1);
            softwareCorrectWords.put("階層構造", 1);
            softwareCorrectWords.put("サブディレクトリ", 1);
            softwareCorrectWords.put("ルートディレクトリ", 1);
            topics.add(new Topic("ソフトウェア", softwareCorrectWords));
            
            HashMap<String, Integer> networkCorrectWords = new HashMap<>();
            networkCorrectWords.put("アイピーアドレス", 1);
            networkCorrectWords.put("トランスポート層", 1);
            networkCorrectWords.put("アプリケーション層", 1);
            networkCorrectWords.put("プレゼンテーション層", 3);
            networkCorrectWords.put("セッション層", 2);
            networkCorrectWords.put("ネットワーク層", 1);
            networkCorrectWords.put("データリンク層", 3);
            networkCorrectWords.put("物理層", 1);
            networkCorrectWords.put("ネットワークインタフェース層", 1);
            networkCorrectWords.put("パケット", 1);
            networkCorrectWords.put("通信プロトコル", 1);
            networkCorrectWords.put("ディーエヌエス", 1);
            networkCorrectWords.put("ドメイン名", 1);
            networkCorrectWords.put("ドメインネームシステム", 1);
            networkCorrectWords.put("バス型", 3);
            networkCorrectWords.put("スター型", 2);
            networkCorrectWords.put("リング型", 1);
            networkCorrectWords.put("OSI基本参照モデル", 3);
            networkCorrectWords.put("ケーブル", 1);
            networkCorrectWords.put("ルータ", 1);
            networkCorrectWords.put("ゲートウェイ", 1);
            networkCorrectWords.put("広域ネットワーク", 1);
            networkCorrectWords.put("専用線方式", 3);
            networkCorrectWords.put("回線交換方式", 2);
            networkCorrectWords.put("パケット交換方式", 1);
            networkCorrectWords.put("ATM交換方式", 3);
            topics.add(new Topic("ネットワーク", networkCorrectWords));
            
            HashMap<String, Integer> infoSecurityCorrectWords = new HashMap<>();
            infoSecurityCorrectWords.put("機密性", 1);
            infoSecurityCorrectWords.put("完全性", 1);
            infoSecurityCorrectWords.put("可用性", 1);
            infoSecurityCorrectWords.put("ランサムウェア", 3);
            infoSecurityCorrectWords.put("感染", 2);
            infoSecurityCorrectWords.put("コンピュータウイルス", 1);
            infoSecurityCorrectWords.put("自己伝染機能", 3);
            infoSecurityCorrectWords.put("情報漏えい", 1);
            infoSecurityCorrectWords.put("ディジタル署名", 1);
            infoSecurityCorrectWords.put("定期バックアップ", 1);
            infoSecurityCorrectWords.put("不正アクセス", 1);
            infoSecurityCorrectWords.put("技術的脅威", 1);
            infoSecurityCorrectWords.put("人的脅威", 1);
            infoSecurityCorrectWords.put("物理的脅威", 1);
            infoSecurityCorrectWords.put("フィッシング", 3);
            infoSecurityCorrectWords.put("DoS攻撃", 2);
            infoSecurityCorrectWords.put("DDoS攻撃", 1);
            infoSecurityCorrectWords.put("だまし取る", 3);
            infoSecurityCorrectWords.put("ソーシャルエンジニアリング", 1);
            infoSecurityCorrectWords.put("隙をつく", 1);
            infoSecurityCorrectWords.put("脆弱性", 1);
            infoSecurityCorrectWords.put("トロイの木馬", 1);
            infoSecurityCorrectWords.put("マルウェア", 3);
            infoSecurityCorrectWords.put("外部流出", 2);
            infoSecurityCorrectWords.put("生体認証", 1);
            infoSecurityCorrectWords.put("虹彩認証", 3);
            infoSecurityCorrectWords.put("暗証番号", 2);
            infoSecurityCorrectWords.put("ファイアウォール", 1);
            infoSecurityCorrectWords.put("セキュリティパッチ", 1);
            infoSecurityCorrectWords.put("デジタルタトゥー", 1);
            infoSecurityCorrectWords.put("ウイルス対策ソフト", 3);
            infoSecurityCorrectWords.put("セキュリティソフト", 2);           
            topics.add(new Topic("情報セキュリティー", infoSecurityCorrectWords));
        }

        return topics;
    }

    public static Topic getRandomTopic(){
        return getTopics().get(new Random().nextInt(getTopics().size()));
    }
}
