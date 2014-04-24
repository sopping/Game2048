package com.example.game2048.util;

import android.app.Activity;
import android.content.DialogInterface;

import com.example.game2048.R;
import com.example.game2048.entity.Grid;
import com.example.game2048.entity.Position;
import com.example.game2048.entity.Tile;
import com.example.game2048.view.BestScoreView;
import com.example.game2048.view.GridView;
import com.example.game2048.view.MessageView;
import com.example.game2048.view.ScoreView;
import com.example.game2048.view.TileView;

/**
 * 执行器
 */
public class Actuator {

    private long score;

    private GridView gridView;
    private ScoreView scoreView;
    private BestScoreView bestScoreView;
    private Activity activity;

    public Actuator(Activity activity) {
        this.activity = activity;
        if(activity != null){
            gridView = (GridView) activity.findViewById(R.id.gridView);
            scoreView = (ScoreView) activity.findViewById(R.id.score);
            bestScoreView = (BestScoreView) activity.findViewById(R.id.bestScore);
        }
    }

    /**
     * 执行
     */
    public void actuate(GameManager metadata){
        int width = getTileWidth(metadata.getSize());
        Grid grid = metadata.getGrid();
        gridView.removeAllViews();
        Tile[][] cells = grid.getCells();
        for(int x=0;x<metadata.getSize();x++){
            for(int y=0;y<metadata.getSize();y++){
                Tile tile = cells[x][y];
                if(tile != null){
                    addTile(tile, width);
                }else{
                    addTile(new Tile(new Position(x,y), 0), width);
                }
            }
        }

        updateScore(metadata.getScore());
        updateBestScore(metadata.getBestscore());

        if(metadata.isGameTerminated()){
            if(metadata.isOver()){
                showMessage(false, metadata);
            }else if(metadata.isWon()){
                showMessage(true, metadata);
            }
        }
    }

    /**
     * 获取卡片的宽高
     * @param size
     * @return
     */
    public int getTileWidth(int size){
        int gridWidth = gridView.getGridWidth();
        float margin = activity.getResources().getDimension(R.dimen.grid_marigin);

        return (int) ((gridWidth - margin) / size);
    }

    /**
     * 显示提示信息
     * @param won
     */
    public void showMessage(boolean won,final GameManager manager){
        new MessageView(activity.getApplicationContext()).showMessage(won, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                manager.restart();
            }
        });
    }

    /**
     * 添加卡片
     * @param tile
     */
    public void addTile(Tile tile, int width){
        TileView tileView = new TileView(activity.getApplicationContext(), tile);
        gridView.addView(tileView, width, width);
    }

    /**
     * 更新分数
     * @param score
     */
    public void updateScore(long score){
        long addition = score - this.score;
        this.score = score;
        scoreView.updateScore(score, addition);
    }

    /**
     * 更新最高分
     * @param bestCore
     */
    public void updateBestScore(long bestCore){
        bestScoreView.showBestScore(bestCore);
    }


}
