package org.ezool.iqx.views;

import org.zool.iqx.config.ActionConfigurations;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可以控制滑动的ViewPager
 * 本控件仅仅用于CategoryBaseFragment的布局文件中
 */
public class SwitchableViewPager extends ViewPager{

	public SwitchableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwitchableViewPager(Context context) {
		super(context);
	}

	/**
	 * 触摸控制事件处理
	 * 如果全局动作配置中不允许左右滑动，则直接返回FALSE
	 * 否则，按照原处理进行
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (ActionConfigurations.getInstance().isScrollActionEnabled())
		{
			return super.onTouchEvent(arg0);
		}
		else
		{
			return false;
		}
	}

	/**
	 * 拦截触摸事件
	 * 如果全局动作配置中不允许左右滑动，则直接返回FALSE
	 * 否则，按照原处理进行
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (ActionConfigurations.getInstance().isScrollActionEnabled())
		{
			return super.onInterceptTouchEvent(arg0);
		}
		else
		{
			return false;
		}
	}
	
	
}
