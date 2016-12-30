package com.bzf.jianxin.main.maintab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bzf.jianxin.R;

public class GradientTextView extends FrameLayout {
	
	

	private TextView vTopText;
	private TextView vBottomText;
	
	private float mAlpha = 1;

	public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientTextView, defStyle, 0);
		int topColor = a.getColor(R.styleable.GradientTextView_top_text_color, Color.parseColor("#9A9A9A"));
		int bottomColor = a.getColor(R.styleable.GradientTextView_bottm_text_color, Color.parseColor("#45C01A"));
		float textSize = a.getDimension(R.styleable.GradientTextView_textSize,12);
		String text = a.getString(R.styleable.GradientTextView_text);
		setTopColor(topColor);
		setBottomColor(bottomColor);
		setText(text);
		setTextSize(textSize);
		a.recycle();
		setTextAlpha(mAlpha);
	}

	/**
	 * 0：选中  1：未选中
	 * @param alpha
	 */
	public void setTextAlpha(float alpha) {
		vTopText.setAlpha(alpha);
		vBottomText.setAlpha(1-alpha);
		
	}

	public void setTextSize(float textSize) {
		vTopText.setTextSize(textSize);
		vBottomText.setTextSize(textSize);
	}

	public void setText(String text) {
		vTopText.setText(text);
		vBottomText.setText(text);
		
	}

	public void setBottomColor(int bottomColor) {
		vBottomText.setTextColor(bottomColor);
		
	}

	public void setTopColor(int topColor) {
		vTopText.setTextColor(topColor);
		
	}

	public GradientTextView(Context context) {
		this(context,null,0);
	}

	public GradientTextView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	private void initView(Context context) {
		View view  = LayoutInflater.from(context).inflate(R.layout.gradient_text_layout, this,true);
		vTopText = (TextView) view.findViewById(R.id.vTopText);
		vBottomText = (TextView) view.findViewById(R.id.vBottomText);
	}	
}
