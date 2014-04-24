package com.example.game2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

/**
 * Created by Administrator on 14-4-23.
 */
public class GridView extends GridLayout{

    private int gridWidth;

    public GridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minSpec = Math.min(widthMeasureSpec, heightMeasureSpec);
        gridWidth = minSpec;
        super.onMeasure(minSpec, minSpec);
    }

    public int getGridWidth() {
        return gridWidth;
    }
}
