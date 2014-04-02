package org.ezool.iqx;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.ezool.iqx.events.EventBusWrapper;
import org.ezool.iqx.events.MainActivitySlidingEnableChangedEvent;
import org.ezool.iqx.fragments.AppMenuListFragment;
import org.ezool.iqx.fragments.CategoryBaseFragment;
import org.ezool.iqx.fragments.MenuListFragment;
import org.zool.iqx.config.ActionConfigurations;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

/**
 * 主界面，包含了两个侧滑菜单
 */
public class MainActivity extends BaseActivity {
	
	/** 当前正被显示的界面 */
	CategoryBaseFragment currentFragment = null;
	
	/** 缓存所有已经存在的界面 */
	Map<Integer, CategoryBaseFragment> categoryfragments = new HashMap<Integer, CategoryBaseFragment>();
	
	/**
	 * 构造函数
	 * 调用基类的构造函数
	 */
	public MainActivity()
	{
		super(R.string.main_title);
	}
	
	/**
	 * 构造界面
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// 构造左侧滑动菜单
		this.getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		this.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		// 主界面设置
		setContentView(R.layout.activity_main);
		
		// 构造右侧滑动菜单
		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_two, new AppMenuListFragment()).commit();
		
		// 取得当前设置是否可以滑动的设定，并设置到自身
		this.getSlidingMenu().setSlidingEnabled(ActionConfigurations.getInstance().isMainSlidingEnabled());
		
		
		// 将自身注册到监听器中用以监听MainActivitySlidingEnableChangedEvent
		// 请参阅EventBus
		EventBusWrapper.register(this);
	}
	
	/**
	 * 设定主界面的fragment
	 * @param fragment 主界面显示的fragment，总是CategoryBaseFragment的子类
	 */
	public void switchContent(CategoryBaseFragment fragment) {
		
		// 如果所需要显示的fragment不是当前的fragment，则进行主界面的替换处理
		if (fragment != null)
		{
			// 记住当前的fragment
			this.currentFragment = fragment;	
			this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, fragment)
				.commit();
		}
		
		// 隐藏侧滑菜单
		this.getSlidingMenu().showContent();
	}

	/**
	 * 当自身被销毁时，需要从EventBus中取消自身
	 */
	@Override
	protected void onDestroy()
	{
		// 将自身从监听器中移除作为监听MainActivitySlidingEnableChangedEvent的对象
		EventBusWrapper.unregister(this);
		super.onDestroy();
	}
	
	/**
	 * 接收到滑动可否设置变更的事件
	 * @param event 滑动可否设置被变更的事件
	 */
	public void onEvent(MainActivitySlidingEnableChangedEvent event) {
        this.getSlidingMenu().setSlidingEnabled(event.isSlidingEnabled());
    }
	
	/**
	 * 设置当前主界面fragment中的某一个tab的显示数据
	 * 这个数据被回调给主界面fragment进行保存，以便在主界面fragment被临时终止并再次恢复时可以在savedInstanceState中取得
	 * @param position tab的下标，从0开始
	 * @param data tab显示数据
	 * @return
	 */
	public boolean setTabData(int position, Serializable data)
	{
		if (this.currentFragment != null)
		{
			this.currentFragment.setTabData(position, data);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * setTabData的逆操作
	 * @param position tab的下标，从0开始
	 * @return tab显示数据。如果没有数据则返回null
	 */
	public Serializable getTabData(int position)
	{
		if (this.currentFragment == null)
		{
			return null;
		}
		else
		{
			return this.currentFragment.getTabData(position);
		}
	}
	
	
	/**
	 * 设置当前主界面fragment中的某一个tab的显示信息
	 * @param position tab的下标，从0开始
	 * @param key 显示的key值，由tab的view各自定义
	 * @param data tab显示数据
	 * @return
	 */
	public boolean setTabDisplayData(int position, String key, Object data)
	{
		if (this.currentFragment != null)
		{
			this.currentFragment.setTabDisplayData(position, key, data);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 得到当前主界面fragment中的某一个tab的显示信息
	 * @param position tab的下标，从0开始
	 * @param key 显示的key值，由tab的view各自定义
	 * @return tab显示数据
	 */
	public Object getTabDisplayData(int position, String key)
	{
		if (this.currentFragment != null)
		{
			return this.currentFragment.getTabDisplayData(position, key);
		}
		else
		{
			return null;
		}
	}
	
	
	/**
	 * 下面两个接口的说明参见MenuListFragment中的saveCategoryFragmentInstance以及loadCategoryFragmentInstance
	 */
	
	/**
	 * 取得key对应的category
	 * @param key
	 * @return category
	 */
	public CategoryBaseFragment getCategoryBaseFragment(int key)
	{
		if (this.categoryfragments.containsKey(Integer.valueOf(key)))
		{
			return this.categoryfragments.get(Integer.valueOf(key));
		}	
		else
		{
			return null;
		}
	}
	
	/**
	 * 保存key对应的category
	 * @param key
	 * @param f category
	 * @return 保存成功与否
	 */
	public boolean setCategoryBaseFragment(int key, CategoryBaseFragment f)
	{
		if (this.categoryfragments.containsKey(Integer.valueOf(key)))
		{
			return false;
		}	
		else
		{
			this.categoryfragments.put(Integer.valueOf(key), f);
			return true;
		}
	}
}
