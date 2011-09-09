package me.cos.snake;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class TileView2 extends SurfaceView implements SurfaceHolder.Callback{
    protected int mTileSize = 20;
    private Bitmap[] mTiles;
    private Paint mPaint = new Paint();
    private SurfaceHolder mHolder = getHolder();
    private boolean mSurfaceReady = false;
    private Canvas mCanvas;

    public TileView2(Context context, AttributeSet attrs) {
	super(context, attrs);
	mHolder.addCallback(this);
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override public void surfaceCreated(SurfaceHolder holder) {
	mSurfaceReady = true;
    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
	mSurfaceReady = false;
    }

    public void lockCanvas() {
	mCanvas = mHolder.lockCanvas();
    }

    public void unlockCanvasAndPost(){
	mHolder.unlockCanvasAndPost(mCanvas);
	mCanvas = null;
    }

    public void drawTile(int x, int y, int index) {
	mCanvas.drawBitmap(mTiles[index], x, y, mPaint);
	Log.d(MyTag.TAG, "drawBitmap " + index + " at " + x + "," + y);
    }

    public void eraseTile(int x, int y) {
	mCanvas.drawRect(new Rect(x, y, x+mTileSize, y+mTileSize), mPaint);
	Log.d(MyTag.TAG, "clear " + x + "," + y);
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