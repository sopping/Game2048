package com.example.game2048.util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 本地存储
 */
public class LocalStorageManager{

    private static final String FILE_NAME = "localStorage";
    private static final String BEST_SCORE_KEY ="bestScore";

    private GameManager gameManager;
    private SharedPreferences sharedPreferences;
    private Activity activity;

    public LocalStorageManager(Activity activity) {
        this.activity = activity;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        sharedPreferences = activity.getSharedPreferences(BEST_SCORE_KEY, activity.MODE_PRIVATE);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = activity.openFileInput(FILE_NAME);
            ois = new ObjectInputStream(fis);
            gameManager = (GameManager) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取之前存储的最高分
     * @return
     */
    public long getBestScore() {
        return sharedPreferences.getLong(BEST_SCORE_KEY, 0);
    }

    /**
     * 设置本次最高分
     * @param bestScore
     */
    public void setBestScore(long bestScore) {
        if(bestScore >= 0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(BEST_SCORE_KEY, bestScore);
            editor.commit();
        }
    }

    /**
     * 获取之前存储的游戏状态
     * @return
     */
    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * 保存本次游戏状态
     * @param gameManager
     */
    public void setGameManager(GameManager gameManager) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream oos = null;
        try {
            fileOutputStream = activity.openFileOutput(FILE_NAME, activity.MODE_PRIVATE);
            oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(gameManager);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 清除游戏状态
     */
    public void clearGameManager(){
        setGameManager(null);
    }
}
