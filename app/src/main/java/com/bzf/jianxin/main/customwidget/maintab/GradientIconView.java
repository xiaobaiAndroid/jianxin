package com.bzf.jianxin.main.customwidget.maintab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bzf.jianxin.R;


public class GradientIconView extends FrameLayout {

	private ImageView vTopIcon;
	private ImageView vBottmIcon;
	
	private float mAlpha = 1;

	public GradientIconView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientIconView,defStyle,0);
		int count = a.getIndexCount();
		for(int i=0; i<count; i++){
			int attr = a.getIndex(i);
			if(attr==R.styleable.GradientIconView_top_icon){
				BitmapDrawable topBitmap = (BitmapDrawable) a.getDrawable(attr);
				setTopBitmap(topBitmap);
			}else if(attr==R.styleable.GradientIconView_bottm_icon){
				BitmapDrawable bottomBitmap = (BitmapDrawable) a.getDrawable(attr);
				setBottomBitmap(bottomBitmap);
			}
		}
		a.recycle();
		setIconAlpha(mAlpha);
	}

	/**
	 * @param alpha
	 */
	public void setIconAlpha(float alpha) {
		vTopIcon.setAlpha(alpha);
		vBottmIcon.setAlpha(1-alpha);
		
	}

	public GradientIconView(Context context) {
		this(context,null,0);
	}

	public GradientIconView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	
	private void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.gradient_icon_layout, this,true);
		vTopIcon = (ImageView) view.findViewById(R.id.vTop);
		vBottmIcon = (ImageView) view.findViewById(R.id.vBottom);
	}
	
	
	public void setTopBitmap(Drawable bitmap){
		vTopIcon.setImageDrawable(bitmap);
	}
	
	public void setBottomBitmap(Drawable bitmap){
		vBottmIcon.setImageDrawable(bitmap);
	}
}
