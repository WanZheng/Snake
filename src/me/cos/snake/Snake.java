package me.cos.snake;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Snake extends Activity
{
    SnakeView2 mSnake;
    TextView mStatusBar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	mSnake = (SnakeView2) findViewById(R.id.snake2);
	mSnake.setZOrderOnTop(true);    // necessary
	mSnake.getHolder().setFormat(android.graphics.PixelFormat.TRANSPARENT);

	mStatusBar = (TextView) findViewById(R.id.status);
	mSnake.setStatusBar(mStatusBar);
    }

    @Override protected void onPause() {
	super.onPause();
	mSnake.pause();
    }

    @Override protected void onResume() {
	super.onResume();
	mStatusBar.setVisibility(View.VISIBLE);
    }
}