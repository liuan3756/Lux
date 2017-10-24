package com.liuan.lux.lux.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ClockView extends View {
	
	private Paint paintHour;
	private Paint paintMinute;
	private Paint paintSecond;
	private Paint paintBackGround;
	private float radius;//时钟半径
	private float centerX, centerY;//时钟圆心坐标
	private float paintBaseWidth;//画笔基本粗细
	private float angleHour, angleMinute, angleSecond = 0;//长短指针
	private float speed;//指针旋转速度
	private Canvas parentCanvas;
	
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
		change();
		postInvalidateDelayed(20);
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
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private void change() {
		angleSecond += speed;
		if (angleSecond % 360 == 0) {
			angleSecond = 0;
		}
		angleMinute += speed / 60f;
		if (angleMinute % 360 == 0) {
			angleMinute = 0;
		}
		angleHour += speed / 3600f;
		if (angleHour % 360 == 0) {
			angleHour = 0;
		}
	}
	
	private void init() {
		speed = 5f;
		initBackGround();
		initHourHand();
		initMinuteHand();
		initSecondHand();
	}
	
	private void initBackGround() {
		paintBackGround = new Paint();
		paintBackGround.setStyle(Paint.Style.FILL);
		paintBackGround.setAntiAlias(true);
		paintBackGround.setColor(Color.BLACK);
	}
	
	private void initHourHand() {
		paintHour = new Paint();
		paintHour.setStyle(Paint.Style.FILL);
		paintHour.setAntiAlias(true);
		paintHour.setColor(Color.GRAY);
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
		paintBackGround.setStrokeWidth(paintBaseWidth * 2);
		parentCanvas.drawCircle(centerX, centerY, radius, paintBackGround);
	}
	
	private void drawHourHand() {
		if (paintHour.getStrokeWidth() == 0f) {
			paintHour.setStrokeWidth(paintBaseWidth * 2f);
		}
		drawHand(angleHour, centerY - radius * 0.4f, paintHour);
	}
	
	private void drawMinuteHand() {
		if (paintMinute.getStrokeWidth() == 0f) {
			paintMinute.setStrokeWidth(paintBaseWidth * 1.5f);
		}
		drawHand(angleMinute, centerY - radius * 0.7f, paintMinute);
	}
	
	private void drawSecondHand() {
		if (paintSecond.getStrokeWidth() == 0f) {
			paintSecond.setStrokeWidth(paintBaseWidth * 0.5f);
		}
		drawHand(angleSecond, centerY - radius * 0.9f, paintSecond);
	}
	
	private void drawHand(float angleRotate, float endY, Paint handPaint) {
		parentCanvas.drawCircle(centerX, centerY, handPaint.getStrokeWidth() / 2, handPaint);
		parentCanvas.rotate(angleRotate, centerX, centerY);
		parentCanvas.drawLine(centerX, centerY, centerX, endY, handPaint);
		parentCanvas.drawCircle(centerX, endY, handPaint.getStrokeWidth() / 2, handPaint);
	}
	
	public void setSpeed(int speed) {//对外开放设置时针速度接口
		this.speed = speed;
	}
	
}
