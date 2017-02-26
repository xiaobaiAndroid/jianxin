package com.bzf.jianxin.commonutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.Random;

/**
 * @Description: TODO(二维码工具) 
 * @author baizhengfu 
 * @date 2016年6月22日 
 */
public class QRCodeTool {

	/**
	 * 生成二维码
	 * 
	 * @param content
	 * @param width
	 * @param height
	 * @param logoBm
	 * @return
	 */
	public static Bitmap creatQRCode(String content, int width, int height, Bitmap logoBm) {
		try {
			if (TextUtils.isEmpty(content)) {
				return null;
			}
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hints.put(EncodeHintType.MARGIN, 0);
			
			//设置二维码的最小识别区域和最大识别区域
			hints.put(EncodeHintType.MIN_SIZE, 100);
			hints.put(EncodeHintType.MAX_SIZE, 480);
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			 int[] pixels = createColorStyle1(width, height, bitMatrix);
			// 生成二维码图片的格式，使用RGB_565
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			if (logoBm != null) {
				bitmap = addLogo(bitmap, logoBm);
			}
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	/**
//	 * 随机换个样式
//	 * @param width
//	 * @param height
//	 * @param bitMatrix
//	 * @return
//	 */
//	private static int[] getQRStyle(int width, int height, BitMatrix bitMatrix) {
//		int[] pixels;
//		Random random = new Random();
//		int num = random.nextInt(4);
//		switch (num) {
//		case 0:
//			pixels = createColorStyle1(width, height, bitMatrix);
//			break;
//		case 1:
//			pixels = createColorStyle2(width, height, bitMatrix);
//			break;
//		case 2:
//			pixels = createColorStyle3(width, height, bitMatrix);
//			break;
//		default:
//			pixels = creatBlackWhite(width, height, bitMatrix);
//			break;
//		}
//		return pixels;
//	}

	/**
	 * 生成黑色二维码
	 * 
	 * @param width
	 * @param height
	 * @param bitMatrix
	 * @return
	 */
	private static int[] creatBlackWhite(int width, int height, BitMatrix bitMatrix) {
		int[] pixels = new int[width * height];
		// 下面这里按照二维码的算法，逐个生成二维码的图片，
		// 两个for循环是图片横列扫描的结果
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (bitMatrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				} else {
					pixels[y * width + x] = 0xffffffff;
				}
			}
		}
		return pixels;
	}

	/**
	 * 生成彩色二维码
	 * 
	 * @param width
	 * @param height
	 * @param bitMatrix
	 * @return
	 */
	private static int[] createColorStyle1(int width, int height, BitMatrix bitMatrix) {
		int[] pixels = new int[width * height];
		// 下面这里按照二维码的算法，逐个生成二维码的图片，
		// 两个for循环是图片横列扫描的结果
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (bitMatrix.get(x, y)) {
					if (x < width / 2 && y < height / 2) {
						pixels[y * width + x] = 0xFF0094FF;// 蓝色
					} else if (x < width / 2 && y > height / 2) {
						pixels[y * width + x] = 0xFFFED545;// 黄色
					} else if (x > width / 2 && y > height / 2) {
						pixels[y * width + x] = 0xFF5ACF00;// 绿色
					} else {
						pixels[y * width + x] = 0xFF000000;// 黑色
					}
				} else {
					pixels[y * width + x] = 0xffffffff;
				}
			}
		}
		return pixels;
	}

	/**
	 * 生成彩色二维码
	 * 
	 * @param width
	 * @param height
	 * @param bitMatrix
	 * @return
	 */
	private static int[] createColorStyle2(int width, int height, BitMatrix bitMatrix) {
		int[] pixels = new int[width * height];
		// 下面这里按照二维码的算法，逐个生成二维码的图片，
		// 两个for循环是图片横列扫描的结果
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (bitMatrix.get(x, y)) {
					if (x < width) {
						pixels[y * width + y] = 0xFF0094FF;// 蓝色
						if (x <= y) {
							pixels[y * width + x] = 0xFFFED545;// 黄色
						}
					}
				} else {
					pixels[y * width + x] = 0xffffffff;
				}
			}
		}
		return pixels;
	}

	/**
	 * 生成彩色二维码
	 * 
	 * @param width
	 * @param height
	 * @param bitMatrix
	 * @return
	 */
	private static int[] createColorStyle3(int width, int height, BitMatrix bitMatrix) {
		int[] pixels = new int[width * height];
		// 下面这里按照二维码的算法，逐个生成二维码的图片，
		// 两个for循环是图片横列扫描的结果
		Random random = new Random();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (bitMatrix.get(x, y)) {
					int num = random.nextInt(3);
					switch (num) {
					case 0:
						pixels[y * width + x] = 0xFF0094FF;// 蓝色
						break;
					case 1:
						pixels[y * width + x] = 0xFFFED545;// 黄色
						break;
					default:
						pixels[y * width + x] = 0xFF5ACF00;// 绿色

					}
				} else {
					pixels[y * width + x] = 0xffffffff;
				}
			}
		}
		return pixels;
	}

	/**
	 * 在二维码中间添加Logo图案
	 */
	private static Bitmap addLogo(Bitmap qrBitmap, Bitmap logo) {

		// 获取图片的宽高
		int srcWidth = qrBitmap.getWidth();
		int srcHeight = qrBitmap.getHeight();
		int logoWidth = logo.getWidth();
		int logoHeight = logo.getHeight();

		if (srcWidth == 0 || srcHeight == 0) {
			return null;
		}

		if (logoWidth == 0 || logoHeight == 0) {
			return qrBitmap;
		}

		// logo大小为二维码整体大小的1/5
		float scaleFactor = srcWidth * 1.0f / 4 / logoWidth;
		try {
			Canvas canvas = new Canvas(qrBitmap);
			canvas.drawBitmap(qrBitmap, 0, 0, null);
			canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
			canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
		} catch (Exception e) {
			e.getStackTrace();
		}

		return qrBitmap;
	}

	/**
	 * 背景图片
	 * 
	 * @param qrBitmap
	 *            二维码图片
	 * @param bgBitmap
	 *            背景图片
	 * @param showHeight
	 * @param showWidth
	 * @return
	 */
	public static Bitmap addBackground(Bitmap qrBitmap, Bitmap bgBitmap, int showWidth, int showHeight) {
		Bitmap newBitmap = Bitmap.createBitmap(showWidth, showHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawBitmap(bgBitmap, 0, 0, null);
		float qrLeft = (newBitmap.getWidth() - qrBitmap.getWidth()) / 2;
		float qrTop = (newBitmap.getHeight() - qrBitmap.getHeight()) / 2;
		canvas.drawBitmap(qrBitmap, qrLeft, qrTop, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return newBitmap;
	}

}
