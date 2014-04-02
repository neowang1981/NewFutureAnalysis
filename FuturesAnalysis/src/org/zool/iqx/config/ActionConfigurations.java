package org.zool.iqx.config;

import org.ezool.iqx.MainActivity;
import org.ezool.iqx.events.EventBusWrapper;
import org.ezool.iqx.events.MainActivitySlidingEnableChangedEvent;

/**
 * 动作配置管理类
 * @author WANGJI
 */
public class ActionConfigurations {
	
	/**
	 * 构造函数为私有，采用singleton模式，全局唯一
	 */
	private ActionConfigurations()
	{}
	
	/** 唯一的instance */
	private static ActionConfigurations instance = null;
	
	/********************** 设置主界面是否允许左右滑动的功能 **************************/
	
	/** 左右滑动是否允许 */
	private boolean isScrollActionEnabled = true;
	
	/**
	 * 得到全局instance
	 * @return 全局instance
	 */
	public static ActionConfigurations getInstance()
	{
		if (ActionConfigurations.instance == null)
		{
			ActionConfigurations.instance = new ActionConfigurations();
		}
		
		return ActionConfigurations.instance;
	}

	/**
	 * 左右滑动是否允许
	 * @return TRUE：允许    FALSE：不允许
	 */
	public boolean isScrollActionEnabled() {
		return isScrollActionEnabled;
	}

	/**
	 * 设置左右滑动是否允许
	 * @param isScrollActionEnabled 是否允许
	 */
	public void setScrollActionEnabled(boolean isScrollActionEnabled) {
		this.isScrollActionEnabled = isScrollActionEnabled;
	}
	
	/********************** 设置主界面是否可以侧滑功能 **************************/
	
	/** 是否可以侧滑的flag */
	private boolean isMainSlidingEnabled = true;
	
	/**
	 * 主界面的侧滑是否允许
	 * @return TRUE：允许  FALSE：不允许
	 */
	public boolean isMainSlidingEnabled() {
		return this.isMainSlidingEnabled;
	}

	/**
	 * 设置主界面的侧滑是否允许
	 * @param isMainSlidingEnabled是否允许
	 */
	public void setMainSlidingEnabled(boolean isMainSlidingEnabled) {
		this.isMainSlidingEnabled = isMainSlidingEnabled;
		
		// 向所有MainActivity发送事件
		MainActivitySlidingEnableChangedEvent evnt = new MainActivitySlidingEnableChangedEvent();
		evnt.setSlidingEnabled(this.isMainSlidingEnabled);
		EventBusWrapper.post(evnt);
	}
	

	/********************** 设置主界面是否可以上下拉动刷新 **************************/
	
	/** 是否可以拉动刷新 */
	private boolean isPullToRefreshEnabled = true;
	
	/**
	 * 是否可以拉动刷新
	 * @return TRUE:可以拉动刷新  FALSE：不可以拉动刷新
	 */
	public boolean isPullToRefreshEnabled() {
		return isPullToRefreshEnabled;
	}

	/**
	 * 设置是否可以拉动刷新
	 * @param isPullToRefreshEnabled 是否可以刷新
	 */
	public void setPullToRefreshEnabled(boolean isPullToRefreshEnabled) {
		this.isPullToRefreshEnabled = isPullToRefreshEnabled;
	}
}
