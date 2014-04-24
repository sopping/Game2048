package com.example.game2048.view;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Created by Administrator on 14-4-23.
 */
public class ScoreView extends TextView {
    public ScoreView(Context context) {
        super(context);
    }

    /**
     * 展示当前分数
     * @param score
     */
    public void showScore(long score){
        setText(score + "");
    }

    /**
     * 更新分数，使用动画
     * @param score 最终显示分数
     * @param addition 增加的分数
     */
    public void updateScore(long score, long addition){
        //animation
        showScore(score);
    }

    /**
     * 清除分数
     */
    public void clearScore(){
        showScore(0);
    }
}
