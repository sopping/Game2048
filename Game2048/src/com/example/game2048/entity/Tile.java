package com.example.game2048.entity;

import java.io.Serializable;

/**
 * 卡片类
 */
public class Tile implements Serializable{

    private long value = 0;
    private Position position;
    private transient Tile[] mergedFrom;
    private Position previousPosition;

    public Tile(Position position, long value) {
        this.position = position;
        this.value = value;
    }

    /**
     * 保存之前的位置
     */
    public void savePosition(){
        this.previousPosition.setX(position.getX());
        this.previousPosition.setY(position.getY());
    }

    /**
     * 更新位置
     * @param position
     *
     */
    public void updatePosition(Position position){
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public long getValue() {
        return value;
    }

    public Tile[] getMergedFrom() {
        return mergedFrom;
    }

    public void setMergedFrom(Tile[] mergedFrom) {
        this.mergedFrom = mergedFrom;
    }
}
