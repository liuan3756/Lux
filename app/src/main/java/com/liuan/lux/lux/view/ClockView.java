package com.liuan.lux.lux.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class ClockView extends View {
	
	public static final float DEFAULT_HOUR_SPEED = 10f;
	public static final float DEFAULT_MINUTE_SPEED = 14f;
	public static final float DEFAULT_SECOND_SPEED = 50f;
	public static final long DEFAULT_INVALIDATE_DELAYED = 30;
	private boolean needHourHand;
	private boolean needMinuteHand;
	private boolean needSecondHand;
	
	private Paint paintHour;
	private Paint paintMinute;
	private Paint paintSecond;
	private Paint paintBackGround;
	private float radius;//时钟半径
	private float centerX, centerY;//时钟圆心坐标
	private float paintBaseWidth;//画笔基本粗细
	private float angleHour, angleMinute, angleSecond = 0;//长短指针
	private long invalidateDelayed = 0;
	private float speedHour, speedMinute, speedSecond;//指针旋转速度
	private Canvas parentCanvas;
	private boolean asAClock;
	
	public ClockView(Context context) {
		super(context);
		init();
	}
	
	public ClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.parentCanvas = canvas;
		drawBackGround();
		drawHourHand();
		drawMinuteHand();
		drawSecondHand();
		postInvalidateDelayed(invalidateDelayed);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//根据View大小适配参数
		float width, height;
		width = getWidth();
		height = getHeight();
		radius = width / 2;
		centerX = width / 2;
		centerY = height / 2;
		paintBaseWidth = width / 30;
		angleMinute = 0;
		angleHour = 0;
		angleSecond = 0;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private void init() {
		speedHour = DEFAULT_HOUR_SPEED;
		speedMinute = DEFAULT_MINUTE_SPEED;
		speedSecond = DEFAULT_SECOND_SPEED;
		invalidateDelayed = DEFAULT_INVALIDATE_DELAYED;
		needHourHand = true;
		needMinuteHand = true;
		needSecondHand = false;
		initBackGround();
		initHourHand();
		initMinuteHand();
		initSecondHand();
	}
	
	private void initBackGround() {
		paintBackGround = new Paint();
		paintBackGround.setStyle(Paint.Style.FILL);
		paintBackGround.setAntiAlias(true);
		paintBackGround.setColor(Color.parseColor("#3385FF"));
	}
	
	private void initHourHand() {
		paintHour = new Paint();
		paintHour.setStyle(Paint.Style.FILL);
		paintHour.setAntiAlias(true);
		paintHour.setColor(Color.WHITE);
	}
	
	private void initMinuteHand() {
		paintMinute = new Paint();
		paintMinute.setStyle(Paint.Style.FILL);
		paintMinute.setAntiAlias(true);
		paintMinute.setColor(Color.WHITE);
	}
	
	private void initSecondHand() {
		paintSecond = new Paint();
		paintSecond.setStyle(Paint.Style.FILL);
		paintSecond.setAntiAlias(true);
		paintSecond.setColor(Color.RED);
	}
	
	private void drawBackGround() {
		parentCanvas.drawCircle(centerX, centerY, radius, paintBackGround);
	}
	
	private void drawHourHand() {
		if (!needHourHand) return;
		angleHour += speedHour;
		if (angleHour % 360 == 0) {
			angleHour = 0;
		}
		if (paintHour.getStrokeWidth() == 0f) {
			paintHour.setStrokeWidth(paintBaseWidth * 4f);
		}
		drawHand(angleHour, radius * 0.4f, paintHour);
	}
	
	private void drawMinuteHand() {
		if (!needMinuteHand) return;
		angleMinute += speedMinute;
		if (angleMinute % 360 == 0) {
			angleMinute = 0;
		}
		if (paintMinute.getStrokeWidth() == 0f) {
			paintMinute.setStrokeWidth(paintBaseWidth * 4f);
		}
		drawHand(angleMinute, radius * 0.7f, paintMinute);
	}
	
	private void drawSecondHand() {
		if (!needSecondHand) return;
		angleSecond += speedSecond;
		if (angleSecond % 360 == 0) {
			angleSecond = 0;
		}
		if (paintSecond.getStrokeWidth() == 0f) {
			paintSecond.setStrokeWidth(paintBaseWidth * 1f);
		}
		drawHand(angleSecond, radius * 0.9f, paintSecond);
	}
	
	private void drawHand(float angleRotate, float handLength, Paint handPaint) {
		parentCanvas.drawCircle(centerX, centerY, handPaint.getStrokeWidth() / 2, handPaint);
		parentCanvas.rotate(angleRotate, centerX, centerY);
		parentCanvas.drawLine(centerX, centerY, centerX, centerY - handLength, handPaint);
		parentCanvas.drawCircle(centerX, centerY - handLength, handPaint.getStrokeWidth() / 2, handPaint);
	}
	
	public void setHourHandVisible(boolean hourHandVisible) {
		this.needHourHand = hourHandVisible;
	}
	
	public void setMinuteHandVisible(boolean minuteHandVisible) {
		this.needMinuteHand = minuteHandVisible;
	}
	
	public void setSecondHandVisible(boolean secondHandVisible) {
		this.needSecondHand = secondHandVisible;
	}
	
	public void asAClock() {
		asAClock = true;
		needSecondHand = true;
		invalidateDelayed = 1000;
		speedSecond = 6f;
		speedMinute = speedSecond / 60;
		speedHour = speedMinute / 60;
		Calendar calendar = Calendar.getInstance();
		float hour = calendar.get(Calendar.HOUR);
		float minute = calendar.get(Calendar.MINUTE);
		float second = calendar.get(Calendar.SECOND);
		Log.d("@@@@", "hour " + hour);
		Log.d("@@@@", "minute " + minute);
		Log.d("@@@@", "second " + second);
		angleHour = 360f * (hour / 12f);
		angleMinute = angleHour - 360f * (minute / 60f);
		angleSecond = angleHour - angleMinute - 360f * (second / 60f);
		Log.d("@@@@", "angleHour " + angleHour);
		Log.d("@@@@", "angleMinute " + angleMinute);
		Log.d("@@@@", "angleSecond " + angleSecond);
	}
	
}
