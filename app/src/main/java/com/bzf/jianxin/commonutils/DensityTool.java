package com.bzf.jianxin.commonutils;

import android.content.Context;

public class DensityTool {

	/**
	 * dp转成px
	 * @time 2015-1-27
	 * @param context
	 * @param dpValue
	 * @return int
	 * @author baizhengfu
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 
	 * @time 2015-1-27
	 * @param context
	 * @param pxValue
	 * @return int
	 * @author baizhengfu
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/*
	 * 
	 * @param pxValue
	 * 
	 * @param fontScale ��DisplayMetrics��������scaledDensity��
	 * 
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * @param spValue
	 * @param fontScale
	 *            ��DisplayMetrics��������scaledDensity��
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}