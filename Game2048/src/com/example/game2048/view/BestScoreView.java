package com.example.game2048.view;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Administrator on 14-4-23.
 */
public class BestScoreView extends TextView{
    public BestScoreView(Context context) {
        super(context);
    }

    /**
     * 展示最高分
     * @param score
     */
    public void showBestScore(long score){
        setText(score + "");
    }

    /**
     * 清除最高分，即展示0分
     */
    public void clearBestScore(){
        showBestScore(0);
    }

}
