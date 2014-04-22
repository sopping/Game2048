package com.example.game2048.entity;

import com.example.game2048.util.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * 方格
 */
public class Grid {

    private int size;
    private Tile[][] cells;

    public Grid(int size, Tile[][] previousState) {
        this.size = size;
        this.cells = previousState != null ? fromState(previousState) : new Tile[this.size][this.size];
    }

    /**
     * 由之前的状态获取新的卡片数组
     */
    private Tile[][] fromState(Tile[][] previousState){
        Tile[][] cells = new Tile[size][size];
        for(int x=0;x<previousState.length;x++){
            for(int y=0;y<previousState.length;y++){
                Tile tile = previousState[x][y];
                cells[x][y] = tile != null ? new Tile(new Position(tile.getX(), tile.getY()), tile.getValue()) : null;
            }
        }
        return cells;
    }

    /**
     * 遍历方格，调用callback，参数是x、y、cells[x][y]
     * @param callback
     */
    public void eachCell(Function callback){
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                callback.call(x, y, cells[x][y]);
            }
        }
    }

    /**
     * 获取空的方格位置
     * @return
     */
    public List<Position> availableCells(){
        final List<Position> cells = new ArrayList<Position>();
        eachCell(new Function(){
            @Override
            public Object apply(Object[] args) {
                int x = (Integer) args[0];
                int y = (Integer) args[1];
                Tile tile = (Tile) args[2];

                if(tile == null){
                    cells.add(new Position(x, y));
                }
                return null;
            }
        });

        return cells;
    }

    /**
     * 取一个随机的空位置
     * @return
     */
    public Position randomAvailableCell(){
        List<Position> positions = availableCells();
        if(positions.size() > 0){
            return positions.get((int)(Math.random() * positions.size()));
        }
        return null;
    }

    /**
     * 整个方格是否还有空位置
     * @return
     */
    public boolean cellsAvailable(){
        return availableCells().size() > 0;
    }

    /**
     * 检查某一个位置是否有效
     * @param position
     * @return
     */
    public boolean cellAvailable(Position position){
        return !cellOccupied(position);
    }

    /**
     * 检查某一位置是否有卡片
     * @param position
     * @return
     */
    public boolean cellOccupied(Position position) {
        return cellContent(position) != null;
    }

    /**
     * 获取某一位置的卡片
     * @param position
     * @return
     */
    public Tile cellContent(Position position) {
        if(withinBounds(position)){
            return cells[position.getX()][position.getY()];
        }
        return null;
    }

    /**
     * 检查位置是否在边界内
     * @param position
     * @return
     */
    private boolean withinBounds(Position position) {
        return position.getX() >= 0 && position.getX() < size &&
                position.getY() > 0 && position.getY() < size;
    }


}
