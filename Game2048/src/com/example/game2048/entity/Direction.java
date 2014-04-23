package com.example.game2048.entity;

/**
 * 方向
 */
public enum Direction {

    UP(new Position(0, -1)),
    RIGHT(new Position(1, 0)),
    DOWN(new Position(0, 1)),
    LEFT(new Position(-1, 0));

    private Position dirPosition;

    Direction(Position dirPosition) {
        this.dirPosition = dirPosition;
    }

    /**
     * 获取方向
     * @return
     */
    public Position getDirPosition(){
        return dirPosition;
    }

}
