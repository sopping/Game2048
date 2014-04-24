package com.example.game2048.util;

import com.example.game2048.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 一些常数
 * @author Administrator
 *
 */
public class Constants {
    /**
     * 卡片文字颜色Map
     */
    private static final Map<Long, Integer> tileTextColorMap = new HashMap<Long, Integer>();
    static {
        tileTextColorMap.put(2l, R.color.tileTextColorLv2);
        tileTextColorMap.put(4l, R.color.tileTextColorLv4);
        tileTextColorMap.put(-1l, R.color.tileTextColorLvGt8);
    }
    /**
     * 卡片背景色Map
     */
    private static final Map<Long, Integer> tileBgcolorMap = new HashMap<Long, Integer>();
    static {
        tileBgcolorMap.put(0l, R.color.tileBgcolorLv0);
        tileBgcolorMap.put(2l << 0, R.color.tileBgcolorLv2);
        tileBgcolorMap.put(2l << 1, R.color.tileBgcolorLv4);
        tileBgcolorMap.put(2l << 2, R.color.tileBgcolorLv8);
        tileBgcolorMap.put(2l << 3, R.color.tileBgcolorLv16);
        tileBgcolorMap.put(2l << 4, R.color.tileBgcolorLv32);
        tileBgcolorMap.put(2l << 5, R.color.tileBgcolorLv64);
        tileBgcolorMap.put(2l << 6, R.color.tileBgcolorLv128);
        tileBgcolorMap.put(2l << 7, R.color.tileBgcolorLv256);
        tileBgcolorMap.put(2l << 8, R.color.tileBgcolorLv512);
        tileBgcolorMap.put(2l << 9, R.color.tileBgcolorLv1024);
        tileBgcolorMap.put(2l << 10, R.color.tileBgcolorLv2048);
        tileBgcolorMap.put(-1l, R.color.tileBgcolorLvSuper);
    }

    /**
     * 根据数字值获取文字大小
     *
     * @param number
     * @return
     */
    public static int getTextSizeResource(long number){

        if(number >= 2 << 9){
            return R.integer.tileTextSizeLv1024;
        }
        return R.integer.tileTextSizeLv2;
    }

    /**
     * 根据数值获取文字颜色
     *
     * @param number
     * @return
     */
    public static int getTextColorResource(long number){

        return Constants.tileTextColorMap.get(Constants.tileTextColorMap.containsKey(number) ? number : -1);
    }

    /**
     * 根据数值获取卡片背景颜色
     *
     * @param number
     * @return
     */
    public static int getTextBgcolorResource(long number){

        return Constants.tileBgcolorMap.get(Constants.tileBgcolorMap.containsKey(number) ? number : -1);
    }
}
