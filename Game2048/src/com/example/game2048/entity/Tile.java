package com.example.game2048.entity;

import java.io.Serializable;

/**
 * 卡片类
 */
public class Tile implements Serializable{

    private int x;
    private int y;
    private long value = 0;
    private transient Tile[] mergedFrom;
    private Position previousPosition;

    public Tile(Position position, long value) {
        this.x = position.getX();
        this.y = position.getY();
        this.value = value;
    }

    /**
     * 保存之前的位置
     */
    @Deprecated
    public void savePosition(){
        this.previousPosition.setX(x);
        this.previousPosition.setY(y);
    }

    /**
     * 更新位置
     * @param x
     * @param y
     */
    public void updatePosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Tile[] getMergedFrom() {
        return mergedFrom;
    }

    public void setMergedFrom(Tile[] mergedFrom) {
        this.mergedFrom = mergedFrom;
    }
}
