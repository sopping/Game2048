package com.example.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 游戏主程序
 * @author Administrator
 *
 */
public class GameView extends GridLayout {

    private Card[][] cardsMap;
    private List<Card> emptyCardsList = new ArrayList<Card>();


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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minSpec = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(minSpec, minSpec);
    }
	/**
	 * 游戏初始化方法
	 */
	private void initGameView(){

        setColumnCount(getResources().getInteger(R.integer.gridColumns));
        setRowCount(getResources().getInteger(R.integer.gridRows));
        setBackgroundColor(getResources().getColor(R.color.gridBgcolor));

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
						if(offsetX > getResources().getDimensionPixelSize(R.dimen.offset_min_x)){
							swipeRight();
						}else if(offsetX < -getResources().getDimensionPixelSize(R.dimen.offset_min_x)){
							swipeLeft();
						}
					}else{
						if(offsetY > getResources().getDimensionPixelSize(R.dimen.offset_min_y)){
							swipeDown();
						}else if(offsetY < -getResources().getDimensionPixelSize(R.dimen.offset_min_y)){
							swipeUp();
						}
					}
					break;
				}

				return true;
			}
		});
	}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (int) ((Math.min(w, h) - getResources().getDimension(R.dimen.grid_marigin) ) / getColumnCount());
        int cardHeight = (int) ((Math.min(w, h) - getResources().getDimension(R.dimen.grid_marigin)) / getRowCount());
        addCards(cardWidth, cardHeight);
        //startGame();
    }

    /**
     * 重新加载游戏
     */
    private void reloadGame(){

        emptyCardsList.clear();
        for(int y=0;y<getRowCount();y++){
            for(int x=0;x<getColumnCount();x++){
                Card c = cardsMap[y][x];
                c.setNumber(0);
                emptyCardsList.add(c);
            }
        }
       startGame();
    }

    /**
     * 游戏开始
     */
    public void startGame(){
        addRandomNum();
        addRandomNum();
        MainActivity.getMainActivity().clearScore();
//        for(int i = 1;i <= 14 ;i++){
//            addNumer((int) Math.pow(2, i));
//        }
    }
    /**
     * 添加卡片
     * @param cardWidth
     * @param cardHeight
     */
    private void addCards(int cardWidth, int cardHeight){
        Card c;
        cardsMap = new Card[getRowCount()][getColumnCount()];

        for(int y=0;y<getRowCount();y++){
            for(int x=0;x<getColumnCount();x++){
                c = new Card(getContext());
                c.setNumber(0);
                addView(c, cardWidth, cardHeight);
                cardsMap[y][x] = c;
                emptyCardsList.add(c);
            }
        }
    }

    /**
     * 添加随机数字
     */
    private void addRandomNum(){
        if(emptyCardsList.size() > 0){
            Random random = new Random();
            int index = random.nextInt((emptyCardsList.size()));
            Card c = emptyCardsList.remove(index);
            c.setNumber(random.nextInt(10) == 9 ? 4 : 2);
        }
    }

    /**
     * 添加指定数字
     * @param number
     */
    private void addNumer(int number){
        if(emptyCardsList.size() > 0){
            Random random = new Random();
            int index = random.nextInt((emptyCardsList.size()));
            Card c = emptyCardsList.remove(index);
            c.setNumber(number);
        }
    }

    /**
     * 检查游戏是否结束
     */
    private void checkGameOver(){
        if(emptyCardsList.size() != 0){
            return;
        }

        for(int y=0;y<getRowCount();y++){
            for(int x=0;x<getColumnCount();x++){
                if( (y > 0 && cardsMap[y][x].getNumber() == cardsMap[y-1][x].getNumber()) ||
                    (x < getColumnCount() - 1 && cardsMap[y][x].getNumber() == cardsMap[y][x+1].getNumber())){
                    return;
                }
            }
        }

        new AlertDialog.Builder(getContext()).setTitle("WORNING").setMessage("GAME OVER!")
                .setPositiveButton("Restart",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reloadGame();
                    }
                }).show();
    }

    /**
     * 顺序合并
     *
     * @param numbers
     * @param mainActivity
     * @return
     */
    private List<Integer> combine(List<Integer> numbers, MainActivity mainActivity){
        List<Integer> result = new ArrayList<Integer>();
        for(int i=0,len=numbers.size();i<len;i++){
            if(i < len - 1 && numbers.get(i).equals(numbers.get(i + 1))){
                result.add(numbers.get(i) * 2);
                if(mainActivity != null){
                    mainActivity.addScore(numbers.get(i) * 2);
                }
                i++;
                continue;
            }else{
                result.add(numbers.get(i));
            }
        }
        return result;
    }

    /**
	 * 向右划
	 */
	private void swipeRight() {
        boolean exchanged = false;
        for(int y=0;y<getRowCount();y++){
            List<Integer> numbers = new ArrayList<Integer>();
            for(int x=getColumnCount()-1;x>=0;x--){
                if(cardsMap[y][x].getNumber() != 0){
                    numbers.add(cardsMap[y][x].getNumber());
                }
            }
            List<Integer> combinedNumbers = combine(numbers, MainActivity.getMainActivity());

            for(int x=getColumnCount()-1;x>=0;x--){
                if(getColumnCount() - x <= combinedNumbers.size()){
                    if(cardsMap[y][x].getNumber() != combinedNumbers.get(getColumnCount() - x - 1)){
                        emptyCardsList.remove(cardsMap[y][x]);
                        cardsMap[y][x].setNumber(combinedNumbers.get(getColumnCount() - x - 1));
                        exchanged = true;
                    }
                }else{
                    if(cardsMap[y][x].getNumber() != 0){
                        emptyCardsList.add(cardsMap[y][x]);
                        cardsMap[y][x].setNumber(0);
                        exchanged = true;
                    }
                }
            }
        }
        if(exchanged){
            addRandomNum();
            checkGameOver();
        }
	}
	/**
	 * 向左划
	 */
	private void swipeLeft() {
        boolean exchanged = false;
        for(int y=0;y<getRowCount();y++){
            List<Integer> numbers = new ArrayList<Integer>();
            for(int x=0;x<getColumnCount();x++){
                if(cardsMap[y][x].getNumber() != 0){
                    numbers.add(cardsMap[y][x].getNumber());
                }
            }
            List<Integer> combinedNumbers = combine(numbers, MainActivity.getMainActivity());

            for(int x=0;x<getColumnCount();x++){
                if(x <= combinedNumbers.size() - 1){
                    if(cardsMap[y][x].getNumber() != combinedNumbers.get(x)){
                        emptyCardsList.remove(cardsMap[y][x]);
                        cardsMap[y][x].setNumber(combinedNumbers.get(x));
                        exchanged = true;
                    }
                }else{
                    if(cardsMap[y][x].getNumber() != 0){
                        emptyCardsList.add(cardsMap[y][x]);
                        cardsMap[y][x].setNumber(0);
                        exchanged = true;
                    }
                }
            }
        }
        if(exchanged){
            addRandomNum();
            checkGameOver();
        }
	}

	/**
	 * 向下划
	 */
	private void swipeDown() {
        boolean exchanged = false;
        for(int x=0;x<getColumnCount();x++){
            List<Integer> numbers = new ArrayList<Integer>();
            for(int y=getRowCount()-1;y>=0;y--){
                if(cardsMap[y][x].getNumber() != 0){
                    numbers.add(cardsMap[y][x].getNumber());
                }
            }
            List<Integer> combinedNumbers = combine(numbers, MainActivity.getMainActivity());

            for(int y=getColumnCount()-1;y>=0;y--){
                if(getColumnCount() - y <= combinedNumbers.size()){
                    if(cardsMap[y][x].getNumber() != combinedNumbers.get(getColumnCount() - y -1)){
                        emptyCardsList.remove(cardsMap[y][x]);
                        cardsMap[y][x].setNumber(combinedNumbers.get(getColumnCount() - y - 1));
                        exchanged = true;
                    }
                }else{
                    if(cardsMap[y][x].getNumber() != 0){
                        emptyCardsList.add(cardsMap[y][x]);
                        cardsMap[y][x].setNumber(0);
                        exchanged = true;
                    }
                }
            }
        }
        if(exchanged){
            addRandomNum();
            checkGameOver();
        }
	}
	/**
	 * 向上划
	 */
	private void swipeUp() {
        boolean exchanged = false;
        for(int x=0;x<getColumnCount();x++){
            List<Integer> numbers = new ArrayList<Integer>();
            for(int y=0;y<getRowCount();y++){
                if(cardsMap[y][x].getNumber() != 0){
                    numbers.add(cardsMap[y][x].getNumber());
                }
            }
            List<Integer> combinedNumbers = combine(numbers, MainActivity.getMainActivity());

            for(int y=0;y<getRowCount();y++){
                if(y <= combinedNumbers.size() - 1){
                    if(cardsMap[y][x].getNumber() != combinedNumbers.get(y)){
                        emptyCardsList.remove(cardsMap[y][x]);
                        cardsMap[y][x].setNumber(combinedNumbers.get(y));
                        exchanged = true;
                    }
                }else{
                    if(cardsMap[y][x].getNumber() != 0){
                        cardsMap[y][x].setNumber(0);
                        emptyCardsList.add(cardsMap[y][x]);
                        exchanged = true;
                    }
                }
            }
        }
        if(exchanged){
            addRandomNum();
            checkGameOver();
        }
	}


    private void swipeUp2() {
        boolean exchanged = false;
        long score = 0;
        for(int x=0;x<getColumnCount();x++){
            NumberArray array = new NumberArray(getRowCount());
            for(int y=0;y<getRowCount();y++){
                array.addCard(cardsMap[y][x]);
            }

            array.combine();
            int[] result = array.getResult();
            score += array.getScore();

            for(int y=0;y<getRowCount();y++){
                if(cardsMap[y][x].getNumber() != result[y]){
                    exchanged = true;
                    if(cardsMap[y][x].getNumber() == 0){
                        emptyCardsList.remove(cardsMap[y][x]);
                    }else{
                        emptyCardsList.add(cardsMap[y][x]);
                    }
                }
                cardsMap[y][x].setNumber(result[y]);
            }
        }
        MainActivity.getMainActivity().addScore(score);
        if(exchanged){
            addRandomNum();
            checkGameOver();
        }
    }

    private class NumberArray {
        private int[] nonZeroArray;//非零数组
        private int[] result;//最终结果
        private long score = 0;
        private int index = 0;

        NumberArray(int length) {
            this.nonZeroArray = new int[length];
            this.result = new int[length];
        }

        void addCard(Card c){
            if(c.getNumber() >= 0){
                nonZeroArray[index++] = c.getNumber();
            }
        }

        void combine(){
            for(int i=0;i<=index-1;i++){
                if(nonZeroArray[i] == nonZeroArray[i+1]){
                    result[i] = nonZeroArray[i] * 2;
                    score += result[i];
                    i++;
                }else{
                    result[i] = nonZeroArray[i];
                }
            }
            //System.arraycopy(nonZeroArray, index, result, index, result.length);
        }

        long getScore() {
            return score;
        }

        int[] getResult() {
            return result;
        }
    }
}

