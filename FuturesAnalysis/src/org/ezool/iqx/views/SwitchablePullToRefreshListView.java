package org.ezool.iqx.views;

import org.zool.iqx.config.ActionConfigurations;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 可以允许/禁止下拉滑动的一个下拉滑动更新list视图
 *
 */
public class SwitchablePullToRefreshListView extends PullToRefreshListView{

	public SwitchablePullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwitchablePullToRefreshListView(Context context) {
		super(context);
	}
	
	public SwitchablePullToRefreshListView(Context context, Mode mode) {
		super(context, mode);
	}

	public SwitchablePullToRefreshListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}
	
	/**
	 * 按照配置中的isPullToRefreshEnabled来禁用/使用下拉刷新
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (ActionConfigurations.getInstance().isPullToRefreshEnabled())
		{
			return super.onInterceptTouchEvent(event);
		}
		else
		{
			return false;
		}
	}

	/**
	 * 按照配置中的isPullToRefreshEnabled来禁用/使用下拉刷新
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (ActionConfigurations.getInstance().isPullToRefreshEnabled())
		{
			return super.onTouchEvent(event);
		}
		else
		{
			return false;
		}
	}
}
