package com.example.game2048.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.game2048.util.Constants;
import com.example.game2048.R;
import com.example.game2048.entity.Tile;

/**
 * Created by Administrator on 14-4-23.
 */
public class TileView extends FrameLayout {

    private TextView label;
    private Tile tile;

    public TileView(Context context,Tile tile) {
        super(context);
        this.tile = tile;
        label = new TextView(context);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundResource(R.drawable.tile);
        label.setTextSize(getResources().getInteger(Constants.getTextSizeResource(tile.getValue())));
        label.setTextColor(getResources().getColor(Constants.getTextColorResource(tile.getValue())));
        label.setBackgroundColor(getResources().getColor(Constants.getTextBgcolorResource(tile.getValue())));
        if(tile.getValue() > 0){
            label.setText(tile.getValue() + "");
        }
        LayoutParams lp = new LayoutParams(-1, -1);//填充父级容器
        int margin =  getResources().getDimensionPixelSize(R.dimen.tile_marigin);
        lp.setMargins(margin, margin, margin, margin);
        addView(label, lp);
    }
}
