/**   
 * 文件名：MyApplication.java   
 * 包名:com.lighted.android.bargraph.demo
 * @Author:wangliu94@163.com
 * @Description:TODO
 * 版本信息：V1.0 
 * 日期：2014-11-4   
 * Copyright Ecity(Wuhan) Corporation 2014    
 * 版权所有   
 *   
 */

package com.lighted.android.bargraph.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.lighted.android.bargraph.R;

/**
 * @类名：MyApplication
 * @description:
 * @author : wangliu94@163.com
 * @version : 2014-11-4 下午03:49:10
 */

public class WelcomeActivity extends Activity {
	public static int screenWidth;
	public static int screenHeight;

	private ImageView welcome_page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();

		createAnimation();
	}

	public void initView() {

		welcome_page = (ImageView) this.findViewById(R.id.welcome_page);
	}

	private void createAnimation() {
		// TODO Auto-generated method stub
		AlphaAnimation alpAni = new AlphaAnimation(0.0f, 1.0f);
		alpAni.setDuration(2000);
		welcome_page.startAnimation(alpAni);
		alpAni.setAnimationListener(new MyAnimationListener());
	}

	@Override
	protected void onStart() {
		super.onStart();
		screenWidth = getWindowManager().getDefaultDisplay()
				.getWidth(); // 屏幕宽（像素，如：480px）
		screenHeight = getWindowManager().getDefaultDisplay()
				.getHeight(); // 屏幕高（像素，如：
	}

	private class MyAnimationListener implements AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub

			Intent intent = new Intent();
			intent.setClass(WelcomeActivity.this, DemoActivity.class);
			startActivity(intent);
			finish();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}
	}

}
