package me.cos.snake;

import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class TileView extends View {
    private int mTileSize = 10;
    private int mXCount;
    private int mYCount;
    private Bitmap[] mTiles;
    private int[][] mGrid;
    private Paint mPaint = new Paint();

    public TileView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh){
	super.onSizeChanged(w, h, oldw, oldh);

	mXCount = (int)Math.floor(w / mTileSize);
	mYCount = (int)Math.floor(h / mTileSize);

	mGrid = new int[mXCount][mYCount];
	clearTiles();
    }

    public void clearTiles() {
	for (int x=0; x<mXCount; ++x) {
	    for (int y=0; y<mYCount; ++y) {
		setTile(x, y, -1);
	    }
	}
    }

    public void setTile(int x, int y, int icon) {
	mGrid[x][y] = icon;
    }

    @Override protected void onDraw(Canvas canvas){
	super.onDraw(canvas);

	for (int x=0; x<mXCount; ++x) {
	    for (int y=0; y<mYCount; ++y) {
		if (mGrid[x][y] >= 0) {
		    canvas.drawBitmap(mTiles[mGrid[x][y]],
				      x * mTileSize,
				      y * mTileSize,
				      mPaint);
		    Log.d(MyTag.TAG, "drawBitmap " + mGrid[x][y] + " at " + x*mTileSize + "," + y*mTileSize);
		}
	    }
	}
    }

    public void resetTiles(int count) {
	mTiles = new Bitmap[count];
    }

    public void loadTile(int index, Drawable drawable) {
	Bitmap bitmap = Bitmap.createBitmap(mTileSize, mTileSize, Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap);
	drawable.setBounds(0, 0, mTileSize, mTileSize);
	drawable.draw(canvas);

	mTiles[index] = bitmap;
    }
}