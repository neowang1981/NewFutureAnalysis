package org.ezool.iqx.fragments;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.ezool.iqx.MainActivity;
import org.ezool.iqx.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 种别category的基类（对应于左侧滑菜单中的一项）
 *
 */
public class CategoryBaseFragment extends Fragment {
	
	private static String TAB_DATA_PREFIX = "TAB_DATA_";
	
	/** 内部视图  */
	private View innerView;
	
	/** 本fragment的数据。下位的tab通过MainActivity进行获取 */
	private Map<String, Serializable> tabsData = new HashMap<String, Serializable>();
	
	/** 本fragment下属tab的各自显示状态的信息。本信息不进行保存，仅仅由下属的各个tab设置及使用 */
	private Map<String, Map<String, Object>> displayData = new HashMap<String, Map<String, Object>>();
	
	public CategoryBaseFragment() { 
		setRetainInstance(true);
	}
	
	/**
	 * 构建内部View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// tab视图的adapter
		CategoryBaseAdapter adapter = new CategoryBaseAdapter(this.getChildFragmentManager());
		
		// 构造内部视图的frame
		View innerView = inflater.inflate(R.layout.category_base_tabs, container, false);
		ViewPager pager = (ViewPager)innerView.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)innerView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);	
        
        // 在类内部保存视图
        this.innerView = innerView;
        
        // 针对当前显示tab的位置调整侧滑菜单
        MainActivity parentActivity = (MainActivity)this.getActivity();
        this.setSlidingMenu(parentActivity.getSlidingMenu());
        
        this.setViewPager(pager);
        
        // 重建各个tab已经解析好的data（如果有的话）
        // 这些data在onSaveInstanceState(Bundle outState)中被保存
        if (savedInstanceState != null)
        {
	        for(int i = 0; i < this.getTabsCount(); ++i)
	        {
	        	if (savedInstanceState.containsKey(this.getTabDataKey(i)))
	        	{
	        		this.tabsData.put(this.getTabDataKey(i), 
	        				savedInstanceState.getSerializable(this.getTabDataKey(i)));
	        	}
	        }
        }
        
		return innerView;
	}
	
	
	/**
	 * 重写ondetach
	 * android.support.v4.app.Fragment的BUG修正
	 * 请参阅下述资料
	 * http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 在fragment被临时销毁时保存各个tab的数据
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (this.tabsData != null)
		{
			 Set<Entry<String, Serializable>> dataSet = this.tabsData.entrySet();
			 for(Entry<String, Serializable> entry : dataSet)
			 {
				 outState.putSerializable(entry.getKey(), entry.getValue());
			 }
		}
	}
	
	/**
	 * 返回在保存tab数据时的key值
	 * @param position tab位置，从0开始
	 * @return key值
	 */
	private String getTabDataKey(int position)
	{
		return TAB_DATA_PREFIX + position;
	}
	
	/**
	 * 设置tab显示数据
	 * 由MainActivity调用
	 * @param position tab位置，从0开始
	 * @param data tab显示数据
	 */
	public void setTabData(int position, Serializable data)
	{
		this.tabsData.put(this.getTabDataKey(position), data);
	}
	
	/**
	 * 取得tab显示数据
	 * 由MainActivity调用
	 * @param position tab位置，从0开始
	 * @return tab显示数据。如果没有数据则返回null
	 */
	public Serializable getTabData(int position)
	{
		String k = this.getTabDataKey(position);

		if (this.tabsData.containsKey(k))
		{
			return this.tabsData.get(k);
		}

		return null;
	}
	
	/**
	 * 设置tab表示用的信息
	 * 由MainActivity调用
	 * @param position tab位置，从0开始
	 * @param key KEY值
	 * @param data 表示用的信息
	 */
	public void setTabDisplayData(int position, String key, Object data)
	{
		if (this.displayData.containsKey(this.getTabDataKey(position)))
		{
			Map<String, Object> dispData = this.displayData.get(this.getTabDataKey(position));
			dispData.put(key, data);
		}
		else
		{
			Map<String, Object> dispData = new HashMap<String, Object>();
			dispData.put(key, data);
			this.displayData.put(this.getTabDataKey(position), dispData);
		}
	}
	
	
	/**
	 * 得到tab表示用的信息
	 * 由MainActivity调用
	 * @param position tab位置，从0开始
	 * @param key KEY值
	 * @return 表示用的信息。如果没有信息则返回null
	 */
	public Object getTabDisplayData(int position, String key)
	{
		if (this.displayData.containsKey(this.getTabDataKey(position)))
		{
			Map<String, Object> dispData = this.displayData.get(this.getTabDataKey(position));
			return dispData.get(key);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 侧滑菜单的设置
	 * 根据在第一个和最后一个tab时，侧滑菜单的动作会有变化
	 * @param menu 目标侧滑菜单
	 */
	private void setSlidingMenu(final SlidingMenu menu)
	{
		if (this.innerView != null)
		{
			TabPageIndicator indicator = (TabPageIndicator)innerView.findViewById(R.id.indicator);
			indicator.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrollStateChanged(int arg0) { }

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) { }

				/**
				 * 当某个page被选定显示时调用
				 * 
				 * @param position 所选定的tab位置，从0开始
				 */
				@Override
				public void onPageSelected(int position) {
					// 如果只有一个tab，则左右滑动均可
					/*if (getTabsCount() <= 1)
					{
						menu.setMode(SlidingMenu.LEFT_RIGHT);
						menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					}
					else
					{
						// 如果是第一个或者最后一个tab，则侧滑菜单只有一边启用
						if (position == 0 || position == (getTabsCount() - 1))
						{
							menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
							if (position == 0)
							{
								menu.setMode(SlidingMenu.LEFT);
							}
							else
							{
								menu.setMode(SlidingMenu.RIGHT);
							}
						}
						// 其他的情况，左右侧滑菜单均启用
						else
						{
							menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
							menu.setMode(SlidingMenu.LEFT_RIGHT);
						}
					}*/
				}

			});
			
			// 当前所选tab为第一个
			indicator.setCurrentItem(0);
			
			// 上述语句貌似不能触发onPageSelected事件，所以这里手动处理。。。。。。
			/*if (getTabsCount() <= 1)
			{
				menu.setMode(SlidingMenu.LEFT);
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
			else
			{
				menu.setMode(SlidingMenu.LEFT_RIGHT);
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}*/
		}
			
	}
	
	/**
	 * 由于设置了仅仅在margin时的侧滑菜单
	 * 所以在这里跟踪手指在viewpager上的滑动轨迹，并在适当使其时期打开侧滑菜单
	 * @param pg viewpager
	 */
	private void setViewPager(final ViewPager pg)
	{
		pg.setOnTouchListener(new OnTouchListener() {
			
			// 由于本方法同时跟踪了move和up事件，所以需要保留move的前一个的坐标
			// move1->move2->up
			// 在上面这个事件序列中，move2和up具有相同的X坐标
			// 为了避免这种情况，下面设置了previousOnClickX
			private float onClickX = 0; 
			private float previousOnClickX = 0;
			private int tabCount = getTabsCount();
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					Log.e("ACTION_DOWN", "ACTION_DOWN");
					previousOnClickX = onClickX;
					onClickX = event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					Log.e("ACTION_MOVE", "ACTION_MOVE");
					previousOnClickX = onClickX;
					onClickX = event.getX();
					break;
				case MotionEvent.ACTION_UP:
					Log.e("ACTION_UP", "ACTION_UP");
					if((tabCount - 1) == pg.getCurrentItem()) { // 为最后一个时
						if(previousOnClickX > event.getX()) { // 向左滑动，右边已无view直接打开右侧滑菜单。
							((MainActivity) getActivity()).getSlidingMenu().showSecondaryMenu();
						}
					} 
					
					if(0 == pg.getCurrentItem()) {
						if(previousOnClickX < event.getX()) { // 向右滑动，左边已无view直接打开左侧滑菜单。
							((MainActivity) getActivity()).getSlidingMenu().showMenu();
						}
					}
					onClickX = 0; // 还原坐标
					previousOnClickX = 0;
					break;
				default:
					break;
				}
				return false;
			}
		});
	}
	
	/**
	 * 返回指定位置tab的标题，此处只是示例
	 * 此方法必须在子类中重写
	 * @param pos tab的位置，以0开始
	 * @return tab的标题
	 */
	//TODO:需要改成abstract
	protected String getTabsTitle(int pos)
	{
		return "我的标题 " + pos;
	}
	
	/**
	 * 返回总共tab的个数，此处只是示例
	 * 此方法必须在子类中重写
	 * @return tab的个数
	 */
	//TODO:需要改成abstract
	protected int getTabsCount()
	{
		return 7;
	}
	
	/**
	 * 返回指定tab位置的fragment
	 * 本方法仅仅调用框架的getFragmentByTabIndex，
	 * 并在返回的fragment中设置此fragment的tab位置（保存以及取得显示数据时使用）
	 * @param position tab位置
	 * @return
	 */
	private Fragment getFragmentByTabIndexImpl(int position)
	{
		ViewBaseFragment f = this.getFragmentByTabIndex(position);
		if (f != null)
		{
			f.setIndicatorPosition(position);
			f.setContainerFragment(this);
		}
		
		return f;
	}
	
	/**
	 * 返回指定tab位置的fragment
	 * 本方法应当在子类中派生
	 * @param position tab位置
	 * @return
	 */
	//TODO:需要改成abstract
	protected ViewBaseFragment getFragmentByTabIndex(int position)
	{
		//if (position < 1)
    	//{
    	//	return TestFragment.newInstance(new Integer(position).toString());
    	//}
    	//else
    	{
    		//return new PullToRefreshBaseFragment();
    		TabhostBaseFragment f = new TabhostBaseFragment();
    		return f;
    	}
	}
	
	/**
	 * tabs的适配器
	 *
	 */
    private class CategoryBaseAdapter extends FragmentPagerAdapter {
        public CategoryBaseAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	return getFragmentByTabIndexImpl(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getTabsTitle(position);
        }

        @Override
        public int getCount() {
            return getTabsCount();
        }
    }
	
}
