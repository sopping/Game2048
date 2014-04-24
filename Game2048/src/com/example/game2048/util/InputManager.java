package com.example.game2048.util;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.game2048.R;

/**
 * 输入控制器
 */
public class InputManager {

    private static final int SWIPE_MIN_DISTANCE = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private Activity activity;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;

    public InputManager(Activity activity) {
        this.activity = activity;
    }

    /**
     * 左划
     */
    public void swipeLeft(){

    }

    /**
     * 右划
     */
    public void swipeRight(){

    }

    /**
     * 上划
     */
    public void swipeUp(){

    }

    /**
     * 下划
     */
    public void swipeDown(){

    }


    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float offsetX = e2.getX() - e2.getX();
                float offsetY = e1.getY() - e2.getX();
                //X轴位移
                if(Math.abs(offsetX) > Math.abs(offsetY) && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                    if(offsetX > SWIPE_MIN_DISTANCE){
                        swipeRight();
                    }else if(offsetX < -SWIPE_MIN_DISTANCE){
                        swipeLeft();
                    }
                }else if(Math.abs(offsetY) > Math.abs(offsetX) && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY){
                    if(offsetY > SWIPE_MIN_DISTANCE){
                        swipeDown();
                    }else if(offsetY < -SWIPE_MIN_DISTANCE){
                        swipeUp();
                    }
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

}
