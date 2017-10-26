package com.liuan.lux.lux.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LatinView extends View {
	
	float width = 0;
	float height = 0;
	
	float nextPointX;
	float nextPointY;
	
	Random random = null;
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
		path = new Path();
		paint = new Paint();
		paint.setColor(Color.parseColor("#FF6347"));
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		random = new Random();
		
		Timer timer = new Timer();
		
		TimerTask colorTask = new TimerTask() {
			@Override
			public void run() {
				int red = random.nextInt(255);
				int blue = random.nextInt(255);
				int green = random.nextInt(255);
				paint.setColor(Color.rgb(red, blue, green));
			}
		};
		//timer.schedule(colorTask, 0, 3000);
	}
	
	private float getRandomLength() {
		return width < height ? width / 24 : height / 24;
	}
	
	private void resetPath() {
		path = new Path();
		nextPointX = width / 2;
		nextPointY = height / 2;
		path.moveTo(nextPointX, nextPointY);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
		
		int XorY = random.nextInt(2);
		
		int nextXDirection = random.nextInt(2);
		int nextYDirection = random.nextInt(2);
		
		float XLength = getRandomLength();
		float YLength = getRandomLength();
		
		if (XorY == 0) {
			nextPointX = nextPointX + (nextXDirection == 0 ? XLength : -XLength);
		} else {
			nextPointY = nextPointY + (nextYDirection == 0 ? YLength : -YLength);
		}
		if (nextPointX > width || nextPointX < 0 || nextPointY > height || nextPointY < 0) {
			resetPath();
		} else {
			path.lineTo(nextPointX, nextPointY);
		}
		postInvalidateDelayed(50);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getWidth();
		height = getHeight();
		
		resetPath();
	}
}
