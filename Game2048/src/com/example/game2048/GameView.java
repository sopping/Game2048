package com.example.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
/**
 * ��Ϸ������
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
	 * ��Ϸ��ʼ������
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
					//X��λ��
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
	 * ���һ�
	 */
	private void swipeRight() {
		System.out.println("right");
	}
	/**
	 * ����
	 */
	private void swipeLeft() {
		System.out.println("left");
	}
	/**
	 * ���»�
	 */
	private void swipeDown() {
		System.out.println("Down");
	}
	/**
	 * ���ϻ�
	 */
	private void swipeUp() {
		System.out.println("up");
	}
}
