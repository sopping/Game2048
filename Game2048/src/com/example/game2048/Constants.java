package com.example.game2048;

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
    private static final Map<Integer, Integer> cardTextColorMap = new HashMap<Integer, Integer>();
    static {
        cardTextColorMap.put(2, R.color.cardTextColorLv2);
        cardTextColorMap.put(4, R.color.cardTextColorLv4);
        cardTextColorMap.put(-1, R.color.cardTextColorLvGt8);
    }
    /**
     * 卡片背景色Map
     */
    private static final Map<Integer, Integer> cardBgcolorMap = new HashMap<Integer, Integer>();
    static {
        cardBgcolorMap.put(0, R.color.cardBgcolorLv0);
        cardBgcolorMap.put(2 << 0, R.color.cardBgcolorLv2);
        cardBgcolorMap.put(2 << 1, R.color.cardBgcolorLv4);
        cardBgcolorMap.put(2 << 2, R.color.cardBgcolorLv8);
        cardBgcolorMap.put(2 << 3, R.color.cardBgcolorLv16);
        cardBgcolorMap.put(2 << 4, R.color.cardBgcolorLv32);
        cardBgcolorMap.put(2 << 5, R.color.cardBgcolorLv64);
        cardBgcolorMap.put(2 << 6, R.color.cardBgcolorLv128);
        cardBgcolorMap.put(2 << 7, R.color.cardBgcolorLv256);
        cardBgcolorMap.put(2 << 8, R.color.cardBgcolorLv512);
        cardBgcolorMap.put(2 << 9, R.color.cardBgcolorLv1024);
        cardBgcolorMap.put(2 << 10, R.color.cardBgcolorLv2048);
        cardBgcolorMap.put(-1, R.color.cardBgcolorLvSuper);
    }

    /**
     * 根据数字值获取文字大小
     * @param number
     * @return
     */
    public static int getTextSizeResource(int number){

        if(number >= 2 << 9){
            return R.integer.cardTextSizeLv1024;
        }
        return R.integer.cardTextSizeLv2;
    }

    /**
     * 根据数值获取文字颜色
     * @param number
     * @return
     */
    public static int getTextColorResource(int number){

        return Constants.cardTextColorMap.get(Constants.cardTextColorMap.containsKey(number) ? number : -1);
    }

    /**
     * 根据数值获取卡片背景颜色
     * @param number
     * @return
     */
    public static int getTextBgcolorResource(int number){

        return Constants.cardBgcolorMap.get(Constants.cardBgcolorMap.containsKey(number) ? number : -1);
    }
}
