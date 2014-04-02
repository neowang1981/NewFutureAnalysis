package org.ezool.iqx.views;

import org.zool.iqx.config.ActionConfigurations;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

public class SwitchablePullToRefreshGeneralView extends PullToRefreshGeneralView{
	public SwitchablePullToRefreshGeneralView(Context context) {
		super(context);
	}

	public SwitchablePullToRefreshGeneralView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwitchablePullToRefreshGeneralView(Context context, Mode mode) {
		super(context, mode);
	}

	public SwitchablePullToRefreshGeneralView(Context context, Mode mode, AnimationStyle style) {
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
