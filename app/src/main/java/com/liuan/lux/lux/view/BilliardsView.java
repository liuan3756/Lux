package com.liuan.lux.lux.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

public class BilliardsView extends View {
	
	private Context mContext;
	private Paint paintCueBall;
	
	private float currentXCueBall;
	private float currentYCueBall;
	private float lastXCueBall;
	private float lastYCueBall;
	private long invalidateDelayed = 0;
	private boolean isRunning;
	
	public BilliardsView(Context context) {
		super(context);
		init(context);
	}
	
	public BilliardsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public BilliardsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		this.mContext = context;
		this.paintCueBall = new Paint();
		paintCueBall.setStyle(Paint.Style.FILL_AND_STROKE);
		paintCueBall.setColor(Color.RED);
		paintCueBall.setAntiAlias(true);
		paintCueBall.setStrokeWidth(0);
	}
	
	private void rebound() {
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(currentXCueBall, currentYCueBall, 30, paintCueBall);
		// if (isRunning) {
		// 	postInvalidateDelayed(invalidateDelayed);
		// }
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				currentXCueBall = event.getX();
				currentYCueBall = event.getY();
				Log.d(TAG, "dispatchTouchEvent: getX  " + event.getX());
				Log.d(TAG, "dispatchTouchEvent: getHistorySize  " + event.getXPrecision());
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		invalidate();
		return true;
	}
}
