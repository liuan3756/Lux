package com.liuan.lux.lux.view;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LatinView extends View {
	int red = 0;
	int blue = 0;
	int green = 0;
	
	int screenWidth = 0;
	int screenHeight = 0;
	
	int lastPointX;
	int lastPointY;
	
	private int lastXDirection;
	private int lastYDirection;
	Random random = null;
	private Handler handler = null;
	private Path path;
	private Paint paint;
	
	public LatinView(Context context) {
		super(context);
		init();
	}
	
	public LatinView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public LatinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		WindowManager wm = ((Activity) getContext()).getWindowManager();
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				invalidate();
			}
		};
		path = new Path();
		
		lastPointX = screenWidth / 2;
		lastPointY = screenHeight / 2;
		
		path.moveTo(lastPointX, lastPointY);
		
		paint = new Paint();
		paint.setColor(Color.parseColor("#FF6347"));
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		random = new Random();
		
		Timer timer = new Timer();
		
		TimerTask colorTask = new TimerTask() {
			@Override
			public void run() {
				red = random.nextInt(255);
				blue = random.nextInt(255);
				green = random.nextInt(255);
				paint.setColor(Color.rgb(red, blue, green));
			}
		};
		//timer.schedule(colorTask, 0, 3000);
		
		
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				
				int XorY = random.nextInt(2);
				getDirection();
				
				int XLength = getRandomLength();
				int YLength = getRandomLength();
				
				if (XorY == 0) {
					if (lastXDirection == 0) {
						lastPointX += XLength;
					} else {
						lastPointX -= XLength;
					}
				} else {
					if (lastYDirection == 0) {
						lastPointY += YLength;
					} else {
						lastPointY -= YLength;
					}
				}
				if (lastPointX > screenWidth || lastPointX < 0 || lastPointY > screenHeight || lastPointY < 0) {
					path.reset();
					lastPointX = screenWidth / 2;
					lastPointY = screenHeight / 2;
					path.moveTo(lastPointX, lastPointY);
				} else {
					path.lineTo(lastPointX, lastPointY);
				}
				handler.sendEmptyMessage(1);
			}
		};
		timer.schedule(timerTask, 0, 50);
		
	}
	
	private int getRandomLength() {
		int a = random.nextInt(15);
		return 30;
	}
	
	private void getDirection() {
		int XDirection = random.nextInt(2);
		int YDirection = random.nextInt(2);
		//if ((XDirection != lastXDirection && YDirection == lastYDirection) || (YDirection != lastYDirection && XDirection == lastXDirection)) {
		//	getDirection();
		//} else {
		lastXDirection = XDirection;
		lastYDirection = YDirection;
		//}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
