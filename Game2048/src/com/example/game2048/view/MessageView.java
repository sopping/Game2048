package com.example.game2048.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.game2048.MainActivity;

/**
 * Created by Administrator on 14-4-23.
 */
public class MessageView extends AlertDialog{
    public MessageView(Context context) {
        super(context);
    }

    /**
     * 展示信息
     * @param won 是否赢了
     */
    public void showMessage(boolean won,OnClickListener listener) {
        hide();
        setButton(BUTTON_POSITIVE, won ? "You win!" : "Game over!", listener);
        show();
    }
}
