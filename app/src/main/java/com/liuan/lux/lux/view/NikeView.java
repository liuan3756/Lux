package com.liuan.lux.lux.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class NikeView extends View {
	
	private float[][] points = {{100, 100}, {200, 150}, {300, 300}};
	
	private Paint paintBackGround;
	private Paint paintPath;
	private Paint paintCircle;
	private float radius;//时钟半径
	private float centerX, centerY;
	private Canvas parentCanvas;
	private Path pathShort;
	private Path pathLong;
	private float paintBaseWidth;//画笔基本粗细
	private float paintCurrentX;
	private float paintCurrentY;
	private boolean isFirst;
	
	public NikeView(Context context) {
		super(context);
		init();
	}
	
	public NikeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public NikeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		float width, height;
		width = getWidth();
		height = getHeight();
		radius = width / 2;
		centerX = width / 2;
		centerY = height / 2;
		paintBaseWidth = width / 30;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		this.parentCanvas = canvas;
		drawBackGround();
		drawPath();
		drawPoint();
		postInvalidateDelayed(30);
	}
	
	private void init() {
		initBackGround();
		initPath();
		
		isFirst = true;
	}
	
	private void initBackGround() {
		paintBackGround = new Paint();
		paintBackGround.setStyle(Paint.Style.FILL);
		paintBackGround.setAntiAlias(true);
		paintBackGround.setColor(Color.parseColor("#3385FF"));
	}
	
	private void initPath() {
		paintPath = new Paint();
		paintPath.setStyle(Paint.Style.STROKE);
		paintPath.setAntiAlias(true);
		paintPath.setColor(Color.WHITE);
		paintPath.setStrokeWidth(100);
		
		paintCircle = new Paint();
		paintCircle.setStyle(Paint.Style.FILL);
		paintCircle.setAntiAlias(true);
		paintCircle.setColor(Color.WHITE);
		paintCircle.setStrokeWidth(100);
		
		pathShort = new Path();
		pathLong = new Path();
	}
	
	
	private void drawBackGround() {
		parentCanvas.drawCircle(centerX, centerY, radius, paintBackGround);
	}
	
	private void drawPoint() {
		
		for (float[] point : points) {
			if (paintCurrentX == point[0] && paintCurrentY == point[1]) {
				parentCanvas.drawCircle(paintCurrentX, paintCurrentY, paintPath.getStrokeWidth() / 2, paintCircle);
				
			}
		}
	}
	
	private void drawPath() {
		if (pathShort.isEmpty()) {
			pathShort.moveTo(points[0][0], points[0][1]);
			paintCurrentX = points[0][0];
			paintCurrentY = points[0][1];
		} else {
			pathShort.lineTo(paintCurrentX, paintCurrentY);
			parentCanvas.drawPath(pathShort, paintPath);
			parentCanvas.drawCircle(paintCurrentX, paintCurrentY, paintPath.getStrokeWidth() / 2, paintCircle);
			paintCurrentX += 5;
			paintCurrentY += 5;
		}
	}
}
