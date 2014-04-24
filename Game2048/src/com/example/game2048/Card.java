package com.example.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.game2048.util.Constants;

/**
 * 卡片
 */
public class Card extends FrameLayout {

    private int number = 0;
    private TextView label;

	public Card(Context context) {
		super(context);

        label = new TextView(getContext());
        label.setGravity(Gravity.CENTER);
        label.setBackgroundResource(R.drawable.tile);
        LayoutParams lp = new LayoutParams(-1, -1);//填充父级容器
        int margin =  getResources().getDimensionPixelSize(R.dimen.tile_marigin);
        lp.setMargins(margin, margin, margin, margin);
        addView(label, lp);
        setNumber(0);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if(number > 0){
            label.setText(number + "");
        }else{
            label.setText("");
        }
        label.setTextSize(getResources().getInteger(Constants.getTextSizeResource(number)));
        label.setTextColor(getResources().getColor(Constants.getTextColorResource(number)));
        label.setBackgroundColor(getResources().getColor(Constants.getTextBgcolorResource(number)));
    }
}
