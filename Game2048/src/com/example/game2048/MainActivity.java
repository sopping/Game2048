package com.example.game2048;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static MainActivity mainActivity = null;
    private TextView tvScore;
    private long score = 0;

    public MainActivity() {
        mainActivity = this;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.score);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void addScore(long score) {
        this.score += score;
        showScore();
    }

    public void clearScore(){
        this.score = 0;
        showScore();
    }

    private void showScore(){
        tvScore.setText(score + "");
    }
}

