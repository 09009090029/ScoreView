package com.vocapp.custom.ui;


import com.vocapp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class ScoreView extends View {
	Path path = new Path();
	int main_radius;
	int outer_radius;
	int text_size = 20;
	int border_width = 20;
	int arc_color1 = Color.YELLOW;
	int arc_color2 = Color.CYAN;
	int arc_color3 = Color.BLUE;
	int arc_color4 = Color.DKGRAY;
	int inner_radius;
	int outer_color = Color.LTGRAY;
	int inner_color = Color.RED;
	String text = "34";
	int text_color = Color.BLACK;
	float startX = 0;
	float startY = 0;
	float pointX = 0;
	float pointY = 0;
	float startLeftX;
	float startLeftY;
	float startRightX;
	float startRightY;

	float arce_width = 5;
	static int rotation = 0;
	int neddle_color = Color.BLUE;
	int neddle_width = 10;

	public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);

		parseAttributes(context.obtainStyledAttributes(attrs,
				R.styleable.CustomView));

	}

	public ScoreView(Context context) {
		super(context);

	}

	private void parseAttributes(TypedArray a) {

		// textColor = (int) a.getColor(R.styleable., textColor);
		text_size = a.getDimensionPixelSize(R.styleable.CustomView_text_size,
				text_size);
		outer_color = a.getColor(R.styleable.CustomView_outer_color,
				outer_color);
		neddle_width = a.getDimensionPixelSize(
				R.styleable.CustomView_neddle_width, neddle_width);

		neddle_color = a.getColor(R.styleable.CustomView_neddle_color,
				neddle_color);
		inner_color = a.getColor(R.styleable.CustomView_inner_color,
				inner_color);
		arc_color4 = a.getColor(R.styleable.CustomView_arc_color4, arc_color4);

		arc_color3 = a.getColor(R.styleable.CustomView_arc_color3, arc_color3);
		arc_color2 = a.getColor(R.styleable.CustomView_arc_color2, arc_color2);
		arc_color1 = a.getColor(R.styleable.CustomView_arc_color1, arc_color1);
		border_width = a.getDimensionPixelSize(
				R.styleable.CustomView_border_width, border_width);
		arce_width = a.getDimension(R.styleable.CustomView_arc_width,
				arce_width);

	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	//
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// int width=getMeasuredWidth()-(getPaddingLeft()+getPaddingRight());
	// int hight=getMeasuredHeight()-(getPaddingTop()+getPaddingBottom());
	// main_radius = width;
	// // outer_radius=main_radius;
	// inner_radius=main_radius-(4*border_width);
	//
	//
	//
	//
	//
	// }

	@Override
	protected void onDraw(Canvas canvas) {

		int width = getMeasuredWidth() - (getPaddingLeft() + getPaddingRight());
		// int hight=getMeasuredHeight()-(getPaddingTop()+getPaddingBottom());
		main_radius = width;
		outer_radius = main_radius;
		inner_radius = main_radius - (4 * border_width);

		Paint main_circle_paint = new Paint();
		main_circle_paint.setColor(outer_color);
		main_circle_paint.setStyle(Style.FILL);

		Paint inner_paint = new Paint();
		inner_paint.setColor(inner_color);
		inner_paint.setStyle(Style.FILL);

		canvas.drawCircle(main_radius / 2, main_radius / 2, main_radius / 2,
				main_circle_paint);
		canvas.drawCircle(main_radius / 2, main_radius / 2, inner_radius / 2,
				inner_paint);

		RectF rectF = new RectF(getPaddingLeft() + border_width,
				getPaddingTop() + border_width, outer_radius - border_width,
				outer_radius - border_width);

		Paint arce_paint = new Paint();
		arce_paint.setColor(arc_color1);
		arce_paint.setStrokeWidth(arce_width);
		arce_paint.setStyle(Style.STROKE);
		canvas.drawArc(rectF, 0, -45, false, arce_paint);
		arce_paint.setColor(arc_color2);
		canvas.drawArc(rectF, -45, -90, false, arce_paint);
		arce_paint.setColor(arc_color3);
		canvas.drawArc(rectF, -90, -135, false, arce_paint);
		arce_paint.setColor(arc_color4);
		canvas.drawArc(rectF, -135, -180, false, arce_paint);

		Paint text_paint = new Paint();
		text_paint.setColor(text_color);
		text_paint.setStyle(Style.STROKE);
		text_paint.setTextSize(text_size);
		text_paint.setTextAlign(Align.CENTER);

		Paint neddle_paint = new Paint();
		neddle_paint.setColor(neddle_color);
		neddle_paint.setStyle(Style.FILL);
		neddle_paint.setAntiAlias(true);
		// neddle_paint.setShader(new RadialGradient(startX, startY,
		// 10,neddle_color, Color.RED, TileMode.MIRROR));
		neddle_paint.setStrokeWidth(1);
		float text_hight = text_paint.descent() - text_paint.ascent();
		float vaticalOffSet = (text_hight / 2) - text_paint.descent();
		float horizentelOffSet = text_paint.measureText(text) / 2;

		float textx = main_radius / 2/* +(horizentelOffSet) */;
		float texty = main_radius / 2 - (vaticalOffSet);
		canvas.drawText(text, textx, texty, text_paint);
		try {
			int value = Integer.parseInt(text.trim());
			if (value > 100) {

				value = 100;

			} else if (value < 0) {
				value = 0;
			}
			double angle = value * 1.8;

			calculetePoints(angle);
		} catch (NumberFormatException e) {

		}

		/*
		 * path.setFillType(FillType.EVEN_ODD); path.moveTo(startLeftX,
		 * startLeftY); path.lineTo(startLeftX, startLeftY);
		 * path.lineTo(startRightX,startRightY); path.lineTo(pointX, pointY);
		 * path.close(); canvas.drawPath(path, neddle_paint);
		 */

		// path.setFillType(FillType.);
		path.setFillType(FillType.EVEN_ODD);

		path.moveTo(startLeftX, startLeftY);

		path.lineTo(pointX, pointY);
		// path.moveTo(pointX, pointY);

		path.lineTo(startRightX, startRightY);
		// path.moveTo(startRightX,startRightY);

		// path.lineTo(startLeftX, startLeftY);

		path.close();
		canvas.drawPath(path, neddle_paint);

		// canvas.drawLine(startX, startY, pointX, pointY,neddle_paint);

		super.onDraw(canvas);
	}

	public void calculetePoints(double mainAngle) {
		/*
		 * int width=getMeasuredWidth()-(getPaddingLeft()+getPaddingRight());
		 * main_radius = width; outer_radius=main_radius;
		 * inner_radius=main_radius-(4*border_width);
		 */
		mainAngle = mainAngle + 180;
		double rightAnglr = mainAngle - 5;

		double leftAnglr = mainAngle + 5;

		double radianPoint = mainAngle * (Math.PI / 180);
		double radianLeft = leftAnglr * (Math.PI / 180);
		double radianRight = rightAnglr * (Math.PI / 180);

		startX = (float) (main_radius / 2 + inner_radius / 2
				* (Math.cos(radianPoint)));
		startY = (float) (((main_radius / 2)) + (inner_radius / 2)
				* (Math.sin(radianPoint)));
		startLeftX = (float) (main_radius / 2 + inner_radius / 2
				* (Math.cos(radianLeft)));
		startLeftY = (float) (((main_radius / 2)) + (inner_radius / 2)
				* (Math.sin(radianLeft)));
		startRightX = (float) (main_radius / 2 + inner_radius / 2
				* (Math.cos(radianRight)));
		startRightY = (float) (((main_radius / 2)) + (inner_radius / 2)
				* (Math.sin(radianRight)));

		pointX = (float) ((main_radius / 2) + ((inner_radius / 2) + border_width)
				* (Math.cos(radianPoint)));
		pointY = (float) ((main_radius / 2) + ((inner_radius / 2) + border_width)
				* (Math.sin(radianPoint)));

		// invalidate();
	}

	public void setText(String text) {
		this.text = text;
		invalidate();
	}

	public int getMain_radius() {
		return main_radius;
	}

	public void setMain_radius(int main_radius) {
		this.main_radius = main_radius;

		invalidate();
	}

	public int getOuter_radius() {
		return outer_radius;
	}

	public void setOuter_radius(int outer_radius) {
		this.outer_radius = outer_radius;
		invalidate();
	}

	public int getText_size() {
		return text_size;

	}

	public void setText_size(int text_size) {
		this.text_size = text_size;
		invalidate();
	}

	public int getBorder_width() {
		return border_width;
	}

	public void setBorder_width(int border_width) {
		this.border_width = border_width;
		invalidate();
	}

	public int getArc_color1() {
		return arc_color1;

	}

	public void setArc_color1(int arc_color1) {
		this.arc_color1 = arc_color1;
		invalidate();
	}

	public int getArc_color2() {
		return arc_color2;
	}

	public void setArc_color2(int arc_color2) {
		this.arc_color2 = arc_color2;
		invalidate();
	}

	public int getArc_color3() {
		return arc_color3;

	}

	public void setArc_color3(int arc_color3) {
		this.arc_color3 = arc_color3;
		invalidate();
	}

	public int getArc_color4() {
		return arc_color4;
	}

	public void setArc_color4(int arc_color4) {
		this.arc_color4 = arc_color4;
		invalidate();
	}

	public int getInner_radius() {
		return inner_radius;
	}

	public void setInner_radius(int inner_radius) {
		this.inner_radius = inner_radius;
		invalidate();
	}

	public int getOuter_color() {
		return outer_color;
	}

	public void setOuter_color(int outer_color) {
		this.outer_color = outer_color;
		invalidate();
	}

	public int getInner_color() {
		return inner_color;
	}

	public void setInner_color(int inner_color) {
		this.inner_color = inner_color;
		invalidate();
	}

	public int getText_color() {
		return text_color;
	}

	public void setText_color(int text_color) {
		this.text_color = text_color;

		invalidate();
	}

	public float getArce_width() {
		return arce_width;
	}

	public void setArce_width(float arce_width) {
		this.arce_width = arce_width;
		invalidate();
	}

	public String getText() {
		return text;
	}

	public void setAngle(final int angele) {
		final Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				calculetePoints(rotation);

			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				thread.start();
				for (int i = 0; i > angele; i--)

				{
					rotation = 1;
					try {
						thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

}
