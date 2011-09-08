package me.cos.snake;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.KeyEvent;
import android.widget.TextView;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;

public class SnakeView extends TileView {
    private TextView mStatusBar;
    private ArrayList<Coordinate> mSnake = new ArrayList();
    private int mDirection;

    private static final int RED_STAR = 0;
    private static final int YELLOW_STAR = 1;
    private static final int GREEN_STAR = 2;

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    private int mStatus = 0;

    private int[][] mMoveTable = {
	{0, -1}, {1, 0}, {0, 1}, {-1, 0}
    };

    private UpdateHandler mHandler = new UpdateHandler();
    private int mMoveDelay = 500;

    private class UpdateHandler extends Handler {
	@Override public void handleMessage(Message msg) {
	    update();
	    invalidate();
	}

	public void sleep(int millis) {
	    if (! hasMessages(0)) {
		sendMessageDelayed(obtainMessage(0), millis);
	    }
	}
    }

    public SnakeView(Context context, AttributeSet attrs) {
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
    }

    private void initNewGame() {
	Log.d(MyTag.TAG, "initNewGame()");

	mDirection = NORTH;
	mSnake.clear();

	mSnake.add(new Coordinate(10, 10));
	mSnake.add(new Coordinate(10, 11));
	mSnake.add(new Coordinate(10, 12));
	mSnake.add(new Coordinate(10, 13));
	mSnake.add(new Coordinate(10, 14));
	mSnake.add(new Coordinate(10, 15));
	mSnake.add(new Coordinate(10, 16));
	mSnake.add(new Coordinate(10, 17));
    }

    private void update() {
	if (mStatus > 0) {
	    clearTiles();
	    updateSnake();

	    mHandler.sleep(mMoveDelay);
	}
    }

    private void updateSnake() {
	Coordinate head = mSnake.get(0);
	int[] movement = mMoveTable[mDirection];
	Coordinate newHead = new Coordinate(head.getX() + movement[0], head.getY() + movement[1]);
	mSnake.add(0, newHead);
	mSnake.remove(mSnake.size() - 1);

	int index = 0;
	for (Coordinate c : mSnake) {
	    setTile(c.getX(), c.getY(), index == 0 ? YELLOW_STAR : RED_STAR);
	    index++;
	}
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	Log.d(MyTag.TAG, "onKeyDown()");

	switch (keyCode) {
	case KeyEvent.KEYCODE_DPAD_UP:
	    if (mStatus == 0) {
		mStatus = 1;

		initNewGame();
		update();

		mStatusBar.setVisibility(View.INVISIBLE);
	    }else{
		mDirection = NORTH;
	    }
	    return true;
	case KeyEvent.KEYCODE_DPAD_DOWN:
	    mDirection = SOUTH;
	    return true;
	case KeyEvent.KEYCODE_DPAD_LEFT:
	    mDirection = WEST;
	    return true;
	case KeyEvent.KEYCODE_DPAD_RIGHT:
	    mDirection = EAST;
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }

    private class Coordinate {
	private int mX;
	private int mY;

	Coordinate(int x, int y) {
	    mX = x;
	    mY = y;
	}

	int getX() {
	    return mX;
	}

	int getY() {
	    return mY;
	}
    }

    public void setStatusBar(TextView statusBar) {
	mStatusBar = statusBar;
    }

    public void pause() {
	mStatus = 0;
    }
}