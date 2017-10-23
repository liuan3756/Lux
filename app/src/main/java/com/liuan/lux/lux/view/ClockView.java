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
	private Paint paintBackGround;
	private float radius;//时钟半径
	private float centerX, centerY;//时钟圆心坐标
	private float paintBaseWidth;//画笔基本粗细
	private float angleLong, angleShort = 0;//长短指针
	private boolean isKill;//控制线程结束
	private float speed;//指针旋转速度
	private Thread thread;
	
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
		drawBackGround(canvas);
		drawHourHand(canvas);
		drawMinuteHand(canvas);
		super.onDraw(canvas);
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
		angleLong = 0;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private void init() {
		isKill = false;
		speed = 1.5f;
		initBackGround();
		initHourHand();
		initMinuteHand();
		
		thread = new Thread();
		new Thread(new Runnable() {//线程控制canvas旋转角度
			@Override
			public void run() {
				while (!isKill) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					angleLong += speed;
					if (angleLong % 360 == 0) {
						angleLong = 0;
					}
					angleShort += speed / 5f;
					if (angleShort % 360 == 0) {
						angleShort = 0;
					}
					//Log.i("px", "" + angleLong);
					postInvalidate();
				}
				
			}
		}).start();
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
	
	private void drawBackGround(Canvas canvas) {
		paintBackGround.setStrokeWidth(paintBaseWidth * 2);
		canvas.drawCircle(centerX, centerY, radius, paintBackGround);
	}
	
	private void drawHourHand(Canvas canvas) {
		if (paintHour.getStrokeWidth() == 0f) {
			paintHour.setStrokeWidth(paintBaseWidth * 3);
		}
		canvas.drawCircle(centerX, centerY, paintBaseWidth * 1.5f, paintHour);
		canvas.rotate(angleShort, centerX, centerY);
		
		canvas.drawLine(centerX, centerY, centerX, centerY - radius / 3, paintHour);
		canvas.drawCircle(centerX, centerY - radius / 3, paintBaseWidth * 1.5f, paintHour);
	}
	
	private void drawMinuteHand(Canvas canvas) {
		if (paintMinute.getStrokeWidth() == 0f) {
			paintMinute.setStrokeWidth(paintBaseWidth * 2);
		}
		canvas.drawCircle(centerX, centerY, paintBaseWidth, paintMinute);
		canvas.rotate(angleLong, centerX, centerY);
		canvas.drawLine(centerX, centerY, centerX, centerY - radius / 2, paintMinute);
		canvas.drawCircle(centerX, centerY - radius / 2, paintBaseWidth, paintMinute);
	}
	
	
	public void killThread() {//结束线程
		isKill = true;
	}
	
	public void setSpeed(int speed) {//对外开放设置时针速度接口
		this.speed = speed;
	}
	
	class InvalidateThread extends Thread {
		@Override
		public void run() {
			super.run();
		}
	}
	
	
}
