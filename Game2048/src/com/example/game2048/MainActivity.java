package com.example.game2048;

import android.os.Bundle;
import android.app.Activity;
import android.os.storage.StorageManager;
import android.view.Menu;
import android.widget.TextView;

import com.example.game2048.util.Actuator;
import com.example.game2048.util.GameManager;
import com.example.game2048.util.InputManager;
import com.example.game2048.util.LocalStorageManager;

public class MainActivity extends Activity {

    private static MainActivity mainActivity = null;

    public MainActivity() {
        mainActivity = this;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        LocalStorageManager storageManager = new LocalStorageManager(getMainActivity());
        InputManager inputManager = new InputManager(getMainActivity());
        Actuator actuator = new Actuator(getMainActivity());

        new GameManager(R.integer.size, inputManager, storageManager, actuator);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}

