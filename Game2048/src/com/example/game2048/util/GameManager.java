package com.example.game2048.util;

import com.example.game2048.entity.Direction;
import com.example.game2048.entity.Grid;
import com.example.game2048.entity.Position;
import com.example.game2048.entity.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 游戏管理
 */
public class GameManager implements Serializable{

    public static final long WON_SCORE = 2048;
    private transient LocalStorageManager storageManager;
    private transient InputManager inputManager;
    private transient Actuator actuator;
    private transient int startTiles = 2;

    private int size;
    private Grid grid;
    private long score;
    private long bestscore;
    private boolean over;
    private boolean won;
    private boolean keepPlaying;

    public GameManager(int size, InputManager inputManager, LocalStorageManager storageManager, Actuator actuator) {
        this.size = size;
        this.inputManager = inputManager;
        this.storageManager = storageManager;
        this.actuator = actuator;
    }


    /**
     * 重新开始
     */
    public void restart(){
        storageManager.clearGameManager();
        setup();
    }

    /**
     * 赢了以后继续游戏
     */
    public void keepPlaying(){
        setKeepPlaying(true);
    }

    /**
     * 朝一个方向移动
     * @param direction
     */
    public void move(Direction direction){

        if(isGameTerminated()){
            return;
        }

        final Position dirPosition = direction.getDirPosition();
        prepareTiles();

        boolean moved = traversals(dirPosition, new Function() {
            @Override
            public Boolean apply(Object[] args) {
                Position cell = (Position) args[0];
                Tile tile = grid.cellContent(cell);
                Position[] positions = findFarthestPosition(cell, dirPosition);
                Tile next = grid.cellContent(positions[1]);
                if(tile != null){
                    if(next != null && next.getValue() == tile.getValue() && next.getMergedFrom() == null){
                        Tile merged = new Tile(positions[1], tile.getValue()*2);
                        merged.setMergedFrom(new Tile[]{tile, next});

                        grid.insertTile(merged);
                        grid.removeTile(tile);

                        tile.updatePosition(positions[1]);
                        addScore(merged.getValue());

                        if(merged.getValue() == WON_SCORE){
                            setWon(true);
                        }
                    }else{
                        moveTile(tile, positions[0]);
                    }

                    if(!cell.equals(tile)){
                        return true;
                    }
                }
                return false;
            }
        });

        if(moved){
            addRandomTile();

            if(!movesAvailable()){
                setOver(true);
            }
            actuate();
        }
    }

    /**
     * 初始化
     */
    public void setup(){
        GameManager previousState = storageManager.getGameManager();
        if(previousState != null){
            setGrid(new Grid(previousState.getSize(), previousState.getGrid().getCells()));
            setScore(previousState.getScore());
            setOver(previousState.isOver());
            setWon(previousState.isWon());
            setKeepPlaying(previousState.isKeepPlaying());
        }else{
            setGrid(new Grid(size, null));
            setScore(0);
            setOver(false);
            setWon(false);
            setKeepPlaying(false);

            addStartTiles();
        }

    }

    /**
     * 添加随机卡片
     */
    public void addRandomTile(){
        if(grid.cellsAvailable()){
            int value = Math.random() < 0.9 ? 2 : 4;
            Tile tile = new Tile(grid.randomAvailableCell(), value);
            grid.insertTile(tile);
        }
    }

    /**
     * 添加初始化的方格
     */
    public void addStartTiles(){
        for(int i=0;i<startTiles;i++){
            addRandomTile();
        }
    }

    /**
     * 游戏是否中止：游戏失败，或者赢了但未继续
     * @return
     */
    public boolean isGameTerminated(){
        return isOver() || (isWon() && !isKeepPlaying());
    }

    /**
     * 执行
     */
    public void actuate(){
        if(storageManager.getBestScore() < score){
            storageManager.setBestScore(score);
            this.bestscore = score;
        }

        if(isOver()){
            storageManager.clearGameManager();
        }else{
            storageManager.setGameManager(this);
        }
        actuator.actuate(this);
    }

    /**
     * 保存移动前的位置，准备合并信息
     */
    public void prepareTiles(){
        grid.eachCell(new Function() {
            @Override
            public Object apply(Object[] args) {

                Tile tile = (Tile) args[2];
                if(tile != null){
                    tile.setMergedFrom(null);
                    tile.savePosition();
                }
                return null;
            }
        });
    }

    /**
     * 移动卡片
     * @param tile
     * @param position
     */
    public void moveTile(Tile tile, Position position){
        grid.getCells()[tile.getX()][tile.getY()] = null;
        grid.getCells()[position.getX()][position.getY()] = tile;
        tile.updatePosition(position);
    }

    /**
     * 遍历方法,返回是否移动
     * @param vector
     * @param callback
     */
    public boolean traversals(Position vector, Function callback){

        List<Integer> xPos = new ArrayList<Integer>();
        List<Integer> yPos = new ArrayList<Integer>();
        for(int i=0;i<size;i++){
            xPos.add(i);
            yPos.add(i);
        }

        //总是从最远端开始遍历
        if(vector.getX() == 1){
            Collections.reverse(xPos);
        }
        if(vector.getY() == 1){
            Collections.reverse(yPos);
        }

        Boolean moved = false;
        for(Integer x : xPos){
            for(Integer y : yPos){
                moved = (Boolean) callback.call(new Position(x, y)) || moved;
            }
        }

        return moved;
    }

    /**
     * 找到某个方向上的最远的位置
     * @param cell
     * @param vector
     * @return
     */
    public Position[] findFarthestPosition(Position cell, Position vector){
        Position previous;
        do{
            previous = cell;
            cell = new Position(previous.getX()+vector.getX(), previous.getY()+vector.getY());
        }while (grid.withinBounds(cell) &&
                grid.cellAvailable(cell));

        return new Position[]{previous, cell};
    }

    /**
     * 是否可以移动
     * @return
     */
    public boolean movesAvailable(){

        return grid.cellsAvailable() || tileMatchesAvailable();
    }

    /**
     * 查找是否存在可以合并的两个方块(耗时长)
     * @return
     */
    public boolean tileMatchesAvailable(){

        Tile tile = null;
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                tile = grid.cellContent(new Position(x, y));
                if(tile != null){
                    for(Direction direction : Direction.values()){
                        Position dirPosition = direction.getDirPosition();
                        Tile other = grid.cellContent(new Position(x + dirPosition.getX(), y + dirPosition.getY()));
                        if(other != null && other.getValue() == tile.getValue()){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    /**
     * 增加分数
     * @param score
     */
    public void addScore(long score){
        this.score += score;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isKeepPlaying() {
        return keepPlaying;
    }

    public void setKeepPlaying(boolean keepPlaying) {
        this.keepPlaying = keepPlaying;
    }

    public long getBestscore() {
        return bestscore;
    }
}
