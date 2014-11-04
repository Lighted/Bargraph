/**   
 * 文件名：BarView.java   
 * 包名:com.lighted.com.view
 * @Author:wangliu94@163.com
 * @Description:TODO
 * 版本信息：V1.0 
 * 日期：2014-11-4   
 * Copyright Ecity(Wuhan) Corporation 2014    
 * 版权所有   
 *   
 */

package com.lighted.android.bargraph.view;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.lighted.android.bargraph.R;
import com.lighted.android.bargraph.demo.DemoActivity;
import com.lighted.android.bargraph.demo.WelcomeActivity;

/**
 * @类名：BarView
 * @description:
 * @author : wangliu94@163.com
 * @version : 2014-11-4 下午02:06:56
 */

public class BarView extends View {
	private String statisticTitle = null; // 名称
	private String statisticType = null; // 统计方式
	private String statisticInfo = null; // 描述信息
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> statistics = null;
	int picHeight = 900;
	int picWidth = 1200;

	@SuppressWarnings("unused")
	private Context statisticContext = null;
	@SuppressWarnings("unused")
	private Canvas statisticCanvas = null;
	private Paint statisticPaint = null;
	// 绘制 具体统计数据范围
	private int left = 200;
	private int right = 1300;
	private int top = 250;
	private int bottom = 700;

	public BarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int screenWidth = WelcomeActivity.screenWidth; // 屏幕宽（像素，如：480px）
		int screenHeight = WelcomeActivity.screenHeight; // 屏幕高（像素，如：
		picWidth = screenWidth - 200;
		picHeight = screenHeight - 100;

		left = 150;
		right = picWidth;
		top = 180;
		bottom = picHeight - 150;

		setDrawingCacheEnabled(true); // 这函数是要打开图形缓存，这样才能getDrawingCache
		statisticContext = context;
		Bitmap bitmap = Bitmap.createBitmap(picWidth, picHeight,
				Bitmap.Config.ARGB_8888);
		statisticCanvas = new Canvas(bitmap);
		statisticPaint = new Paint();

	}

	@SuppressWarnings("rawtypes")
	public void setDrawInformation(String title, String type, String info,
			ArrayList<ArrayList> statistic) {
		statisticTitle = title;
		statisticType = type;
		statisticInfo = info;
		statistics = statistic;
	}

	protected void onDraw(Canvas canvas) {
		// 绘制Title 
		onDrawStatisticTitle(canvas);
		// 绘制 统计方式
		onDrawStatisticType(canvas);
		// 绘制描述信息
		onDrawStatisticInfo(canvas);
		// 绘制 底图
		onDrawBackgroundPic(canvas);
		// 绘制统计信息
		onDrawstatistics(canvas);

		super.onDraw(canvas);
	}

	// 绘制Title 
	protected void onDrawStatisticTitle(Canvas canvas) {
		if (statisticTitle == null)
			return;
		statisticPaint.setColor(Color.BLACK);
		statisticPaint.setTextSize(40);
		canvas.drawText(statisticTitle, (int) (picWidth / 2 - 100), 40,
				statisticPaint);
	}

	// 绘制 统计方式： 按照口径统计总长
	protected void onDrawStatisticType(Canvas canvas) {
		if (statisticType == null)
			return;
		statisticPaint.setColor(Color.BLACK);
		statisticPaint.setTextSize(25);
		canvas.drawText(statisticType, (int) (picWidth / 2), 100,
				statisticPaint);
	}

	// 绘制 描述信息
	protected void onDrawStatisticInfo(Canvas canvas) {
		if (statisticInfo == null)
			return;
		statisticPaint.setColor(Color.BLACK);
		statisticPaint.setTextSize(25);
		canvas.drawText(statisticInfo, 100, bottom - 50, statisticPaint);
	}

	// 绘制 底图
	@SuppressWarnings("deprecation")
	protected void onDrawBackgroundPic(Canvas canvas) {
		if (statistics == null)
			return;
		Resources res = getResources();
		InputStream is = null;
		BitmapDrawable bmpDraw = null;
		Bitmap bmp = null;
		Rect src = null;
		Rect dst = null;
		is = res.openRawResource(R.drawable.statistic_downpicture);
		bmpDraw = new BitmapDrawable(is);
		bmp = bmpDraw.getBitmap();
		src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		dst = new Rect(left - 100, bottom, right, bottom + bmp.getHeight());
		canvas.drawBitmap(bmp, src, dst, statisticPaint);
	}

	// 绘制统计信息
	@SuppressWarnings({ "unchecked", "deprecation" })
	protected void onDrawstatistics(Canvas canvas) {
		if (statistics == null)
			return;
		int numOfCharView = statistics.size();
		if (numOfCharView <= 0)
			return;
		double xDPsOfperCharView = (right - left) / numOfCharView;

		// 找到纵坐标 最大值 绘制时根据比例尺进行绘制高度
		double largestSum = 0.0d;
		for (int i = 0; i < numOfCharView; i++) {
			ArrayList<String> statistic = statistics.get(i);
			double sum = Double.parseDouble(statistic.get(1));
			if (largestSum < sum)
				largestSum = sum;
		}
		double yDPsOfperCharView = (bottom - top) / largestSum;
		// 绘制条件
		Resources res = getResources();
		InputStream is = null;
		BitmapDrawable bmpDraw = null;
		Bitmap bmp = null;
		Rect src = null;
		Rect dst = null;
		for (int i = 0; i < numOfCharView; i++) {
			ArrayList<String> statistic = statistics.get(i);
			String info = statistic.get(0);
			double sumValue = Double.parseDouble(statistic.get(1));

			// 绘制具体类型
			statisticPaint.setColor(Color.BLACK);
			statisticPaint.setTextSize(25);
			canvas.drawText(info, left + (int) (i * xDPsOfperCharView),
					bottom + 100, statisticPaint);
			// 绘制底部圆、
			is = res.openRawResource(R.drawable.statistic_chartview_down);
			bmpDraw = new BitmapDrawable(is);
			bmp = bmpDraw.getBitmap();
			src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
			dst = new Rect(left + (int) (i * xDPsOfperCharView), bottom - 10,
					left + (int) (i * xDPsOfperCharView) + bmp.getWidth(),
					bottom + bmp.getHeight() - 10);
			canvas.drawBitmap(bmp, src, dst, statisticPaint);
			// 绘制柱状图圆形部分
			is = res.openRawResource(R.drawable.statistic_chartview_bettwen);
			bmpDraw = new BitmapDrawable(is);
			bmp = bmpDraw.getBitmap();
			src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
			dst = new Rect(left + (int) (i * xDPsOfperCharView), bottom
					- (int) (sumValue * yDPsOfperCharView), left
					+ (int) (i * xDPsOfperCharView) + bmp.getWidth(), bottom);
			canvas.drawBitmap(bmp, src, dst, statisticPaint);
			// 绘制柱状图顶部分
			is = res.openRawResource(R.drawable.statistic_chartview_up);
			bmpDraw = new BitmapDrawable(is);
			bmp = bmpDraw.getBitmap();
			src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
			dst = new Rect(left + (int) (i * xDPsOfperCharView), bottom
					- (int) (sumValue * yDPsOfperCharView) - bmp.getHeight()
					+ 5, left + (int) (i * xDPsOfperCharView) + bmp.getWidth(),
					bottom - (int) (sumValue * yDPsOfperCharView) + 5);
			canvas.drawBitmap(bmp, src, dst, statisticPaint);
			// 绘制数量
			canvas.drawText(statistic.get(1), left
					+ (int) (i * xDPsOfperCharView), bottom
					- (int) (sumValue * yDPsOfperCharView) - 10, statisticPaint);
		}
	}
	// 获取绘制的具内容
	/*
	 * protected Bitmap getStatisticPicture() { Bitmap bitmap =
	 * getDrawingCache(); ContentResolver cr =
	 * statisticContext.getContentResolver();
	 * MediaStore.Images.Media.insertImage(cr, bitmap,
	 * "CrossSectionAnalysisPhoto", "CrossSectionAnalysisAlbum"); return bitmap;
	 * }
	 */
}
