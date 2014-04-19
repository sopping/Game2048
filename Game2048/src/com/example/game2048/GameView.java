package com.example.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
/**
 * 游戏主程序
 * @author Administrator
 *
 */
public class GameView extends GridLayout {

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}
	
	/**
	 * 游戏初始化方法
	 */
	private void initGameView(){
		setOnTouchListener(new OnTouchListener() {
			
			private float startX, startY, offsetX, offsetY;
			
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = motionEvent.getX();
					startY = motionEvent.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = motionEvent.getX() - startX;
					offsetY = motionEvent.getY() - startY;
					//X轴位移
					if(Math.abs(offsetX) > Math.abs(offsetY)){
						if(offsetX > Constants.OFFSET_X_MIN){
							swipeRight();
						}else if(offsetX < -Constants.OFFSET_X_MIN){
							swipeLeft();
						}
					}else{
						if(offsetY > Constants.OFFSET_Y_MIN){
							swipeDown();
						}else if(offsetY < -Constants.OFFSET_Y_MIN){
							swipeUp();
						}
					}
					break;
				}
				
				return true;
			}
		});
	}
	
	/**
	 * 向右划
	 */
	private void swipeRight() {
		System.out.println("right");
	}
	/**
	 * 向左划
	 */
	private void swipeLeft() {
		System.out.println("left");
	}
	/**
	 * 向下划
	 */
	private void swipeDown() {
		System.out.println("Down");
	}
	/**
	 * 向上划
	 */
	private void swipeUp() {
		System.out.println("up");
	}
}
