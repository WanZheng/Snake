package me.cos.snake;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Snake extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	SnakeView snakeView = (SnakeView) findViewById(R.id.snake);
	TextView statusBar = (TextView) findViewById(R.id.status);

	snakeView.setStatusBar(statusBar);
    }
}