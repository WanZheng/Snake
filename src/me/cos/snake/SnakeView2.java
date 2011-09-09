package me.cos.snake;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.SurfaceHolder;
import android.view.KeyEvent;
import android.widget.TextView;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;

public class SnakeView2 extends TileView2 {
    private TextView mStatusBar;
    private ArrayList<Coordinate> mSnake = new ArrayList();
    private int mDirection;
    private int mMoveDelay = 5;
    private Object mLock = new Object();
    private Thread mThread = new Thread(new Runnable() {
	    @Override public void run() {
		while (mStatus > 0) {
		    update();
		    // try {
		    // 	Thread.sleep(mMoveDelay);
		    // } catch (InterruptedException e) {
		    // }
		}
	    }
	});

    private static final int RED_STAR = 0;
    private static final int YELLOW_STAR = 1;
    private static final int GREEN_STAR = 2;

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    private int mStatus = 0;
    private float mSpeed = 10.0f;
    private float[][] mMoveTable = {
	{0, -0.1f}, {0.1f, 0}, {0, 0.1f}, {-0.1f, 0}
    };

    public SnakeView2(Context context, AttributeSet attrs) {
	super(context, attrs);

	init();
	Log.d(MyTag.TAG, "SnakeView constructor.");
    }

    private void init() {
	resetTiles(3);

	Resources r = getContext().getResources();
	loadTile(RED_STAR, r.getDrawable(R.drawable.redstar));
	loadTile(YELLOW_STAR, r.getDrawable(R.drawable.yellowstar));
	loadTile(GREEN_STAR, r.getDrawable(R.drawable.greenstar));

	setFocusable(true);
	initNewGame();
    }

    private void initNewGame() {
	Log.d(MyTag.TAG, "initNewGame()");

	mDirection = SOUTH;
	mSnake.clear();

	mSnake.add(new Coordinate(10, 10));
    }

    private void update() {
	if (mStatus > 0) {
	    lockCanvas();

	    clearSnake();
	    updatePhysics();
	    drawSnake();

	    unlockCanvasAndPost();
	}
    }

    private void clearSnake() {
	for (Coordinate c : mSnake) {
	    eraseTile((int)c.mX, (int)c.mY);
	}
    }

    private void drawSnake() {
	int index = 0;
	for (Coordinate c : mSnake) {
	    drawTile((int)c.mX, (int)c.mY, index == 0 ? YELLOW_STAR : RED_STAR);
	    index++;
	}
    }

    private void updatePhysics() {
	Coordinate head = mSnake.get(0);
	float[] movement;
	synchronized (mLock) {
	    movement = mMoveTable[mDirection];
	}
	head.mX += movement[0] * mSpeed;
	head.mY += movement[1] * mSpeed;
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	Log.d(MyTag.TAG, "onKeyDown()");

	switch (keyCode) {
	case KeyEvent.KEYCODE_DPAD_UP:
	    if (mStatus == 0) {
		// mStatusBar.setVisibility(View.INVISIBLE);
		startGame();
	    }else{
		setDirection(NORTH);
	    }
	    return true;
	case KeyEvent.KEYCODE_DPAD_DOWN:
	    setDirection(SOUTH);
	    return true;
	case KeyEvent.KEYCODE_DPAD_LEFT:
	    setDirection(WEST);
	    return true;
	case KeyEvent.KEYCODE_DPAD_RIGHT:
	    setDirection(EAST);
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }

    private void setDirection(int direction) {
	synchronized (mLock) {
	    mDirection = direction;
	}
    }

    private class Coordinate {
	public float mX;
	public float mY;

	Coordinate(float x, float y) {
	    mX = x;
	    mY = y;
	}
    }

    public void setStatusBar(TextView statusBar) {
	mStatusBar = statusBar;
    }

    public void pause() {
	stopGame();
    }

    private void startGame() {
	mStatus = 1;
	mThread.start();
    }

    private void stopGame() {
	mStatus = 0;
	while (true) {
	    try {
		mThread.join();
		break;
	    } catch (InterruptedException e) {
	    }
	}
    }
}