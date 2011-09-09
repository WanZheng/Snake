package me.cos.snake;

import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.PorterDuff;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class Background extends View {
    Random mRND = new Random();

    public Background(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    @Override protected void onDraw (Canvas canvas){
	Log.d(MyTag.TAG, "Background.onDraw");

	canvas.drawColor(Color.argb(0x80, mRND.nextInt() & 0xff, mRND.nextInt() & 0xff, mRND.nextInt() & 0xff), PorterDuff.Mode.SRC);
	// canvas.drawColor(Color.argb(0xff, 0xff, 0xff, 0x00), PorterDuff.Mode.SRC);
    }
}