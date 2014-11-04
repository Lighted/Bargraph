/**   
 * 文件名：DemoActivity.java   
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;

import com.lighted.android.bargraph.R;
import com.lighted.android.bargraph.view.BarView;

/**
 * @类名：DemoActivity
 * @description:
 * @author : wangliu94@163.com
 * @version : 2014-11-4 下午02:49:49
 */

public class DemoActivity extends Activity {
	
	private BarView barview;
	private ArrayList<ArrayList> pipelineStatisticsInfo = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		
	
		initView();

	}

	private void initView() {
		// 读取统计信息XML数据
		pipelineStatisticsInfo = new ArrayList<ArrayList>();
		getPipelineStatisticXMLInformation(pipelineStatisticsInfo);

		// 初始化第一个Item图像
		barview = (BarView) findViewById(R.id.barview);
		if (pipelineStatisticsInfo.size() > 0) {
			ArrayList<Object> StatisticNode = (ArrayList<Object>) pipelineStatisticsInfo
					.get(0);
			barview.setDrawInformation((String) StatisticNode.get(2),
					(String) StatisticNode.get(1),
					(String) StatisticNode.get(3),
					(ArrayList<ArrayList>) StatisticNode.get(4));
			barview.postInvalidate();

		}

	}

	public boolean getPipelineStatisticXMLInformation(
			ArrayList<ArrayList> pipelineStatisticsInfo) {

		// 读取XML文件成功
		InputStream stream = null;
		XmlPullParser xmlParse = Xml.newPullParser();
		try {
			stream = getResources().openRawResource(R.raw.datas);
			xmlParse.setInput(stream, "utf-8");
			// 成功开始读取XML文件成功
			// 申请一定空间用于存放数据库、表、条件
			// StatisticNode 内包含值顺序为：ID、类型、名称、信息、管道信息总体
			ArrayList<Object> StatisticNode = null;
			ArrayList<ArrayList> Statistic = null;
			ArrayList<String> perStatistic = null;

			int evnType = xmlParse.getEventType();
			while (evnType != XmlPullParser.END_DOCUMENT) {
				switch (evnType) {
				case XmlPullParser.START_TAG:
					String tag = xmlParse.getName();
					if (tag.equalsIgnoreCase("statisticNode")) {
						// 信息统计节点 进行赋值
						StatisticNode = new ArrayList<Object>();
						Statistic = new ArrayList<ArrayList>();

					} else if (tag.equalsIgnoreCase("statisticID")) {
						// 读取管道ID值
						StatisticNode.add(xmlParse.nextText());

					} else if (tag.equalsIgnoreCase("statisticType")) {
						// 读取统计类型
						StatisticNode.add(xmlParse.nextText());

					}
					if (tag.equalsIgnoreCase("statisticName")) {
						// 读取统计管道名字
						StatisticNode.add(xmlParse.nextText());

					} else if (tag.equalsIgnoreCase("statistic")) {
						// 开辟空间进行读取每一值段管道数据
						perStatistic = new ArrayList<String>();
					} else if (tag.equalsIgnoreCase("statisticKey")) {
						// 读取具体值段
						perStatistic.add(xmlParse.nextText());

					} else if (tag.equalsIgnoreCase("statisticKeyValue")) {
						// 读取具体值段值
						perStatistic.add(xmlParse.nextText());

					} else if (tag.equalsIgnoreCase("statisticInfo")) {
						// 读取统计信息说明
						StatisticNode.add(xmlParse.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					String endtag = xmlParse.getName();
					if (endtag.equalsIgnoreCase("statistic")) {
						// 读取完毕添加值到 管道具体信息项
						Statistic.add(perStatistic);
					} else if (endtag.equalsIgnoreCase("statisticNode")) {
						// 添加具体数据 总体
						StatisticNode.add(Statistic);
						// 添加一条记录
						pipelineStatisticsInfo.add(StatisticNode);
					}
					break;
				default:
					break;
				}
				evnType = xmlParse.next();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		if (pipelineStatisticsInfo.size() > 0)
			return true; // 查询到数据
		return false; // 读取失败
	}

}
