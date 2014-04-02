package org.ezool.iqx.fragments;

import java.io.Serializable;

import org.ezool.iqx.R;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

/**
 * 内部为一个在界面最下端的tabhost，且作为一个tab视图的fragment
 * tab的数量可以从1到3，可配置
 * 具备通信中等待界面的切换功能，以及ViewBaseFragment中的所有功能
 * 具体请参见ViewBaseFragment
 */
public class TabhostBaseFragment extends ViewBaseFragment{
	
	private static final String VIEWDATA_TABHOST_CURRENT_INDEX = "VIEWDATA_TABHOST_CURRENT_INDEX";
	
	private static final String VIEWDATA_INITIALIZATION_DONE = "VIEWDATA_INITIALIZATION_DONE";
	
	/** 第一个tab的显示frame */
	private RelativeLayout tabViewFrame1;
	
	/** 第二个tab的显示frame */
	private RelativeLayout tabViewFrame2;
	
	/** 第三个tab的显示frame */
	private RelativeLayout tabViewFrame3;
	
	/** tabhost，显示在界面的最下端 */
	private TabHost tabHost;
	
	/** "处理中"界面 */
	private RelativeLayout processingView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// 生成所有view
		View v = inflater.inflate(R.layout.view_with_tabhost_frame, container, false);
		this.tabHost = (TabHost)v.findViewById(android.R.id.tabhost);
		this.tabViewFrame1 = (RelativeLayout)v.findViewById(R.id.tab_first);
		this.tabViewFrame2 = (RelativeLayout)v.findViewById(R.id.tab_second);
		this.tabViewFrame3 = (RelativeLayout)v.findViewById(R.id.tab_third);
		
		this.processingView = (RelativeLayout)v.findViewById(R.id.ready_wait_layout);
		
		// 三个内部框架的ID数据
		final int[] innerIdArray = new int[] {R.id.tab_first, R.id.tab_second, R.id.tab_third};
		// 三个内部框架的layout数据
		final RelativeLayout[] innerLayoutArray = new RelativeLayout[] {this.tabViewFrame1, this.tabViewFrame2, this.tabViewFrame3};
		
		// 根据tab数量和tab标题设置tabhost
		this.tabHost.setup();
		for(int i = 0; i < this.getTabCount(); ++i)
		{
			this.tabHost.addTab(this.tabHost.newTabSpec(this.getTabSpec(i))
					.setIndicator(this.getTabSpec(i), getResources().getDrawable(android.R.drawable.ic_menu_call))
					.setContent(innerIdArray[i]));
		}
		
		this.tabHost.setOnTabChangedListener(new OnTabChangeListener(){
			
			@Override
			public void onTabChanged(String arg0) {
				int currentIndex = TabhostBaseFragment.this.tabHost.getCurrentTab();
				TabhostBaseFragment.this.setTabDisplayData(
						VIEWDATA_TABHOST_CURRENT_INDEX, Integer.valueOf(currentIndex));
			}
			
		});
		
		// 根据tab数量和tab标题将不可见的tab隐去
		for(int i = this.getTabCount(); i < innerLayoutArray.length; ++i)
		{
			innerLayoutArray[i].setVisibility(View.GONE);
		}
		
		// 根据tab数量生成tab内部的VIEW
		for(int i = 0; i < this.getTabCount(); ++i)
		{
			this.createTabViewImpl(this.getActivity(), i);
		}
		
		Integer idx = (Integer)this.getTabDisplayData(VIEWDATA_TABHOST_CURRENT_INDEX);
		if (idx != null)
		{
			this.tabHost.setCurrentTab(idx.intValue());
		}
		else
		{
			// 设置显示第一个tab的view
			this.tabHost.setCurrentTab(0);
			this.setTabDisplayData(VIEWDATA_TABHOST_CURRENT_INDEX, Integer.valueOf(0));
		}
		
		
		// 试图取得本fragment显示所需要的数据
		Serializable viewData = this.getViewDataFromSave();
		// 取得是否已经做过一次初始化数据取得的flag
		Boolean b = (Boolean)this.getTabDisplayData(VIEWDATA_INITIALIZATION_DONE);
		
		// 如果没有数据且未做过初始化，则与服务器进行通信
		if (viewData == null && b == null)
		{
			this.showWaitingView();
			
			this.setTabDisplayData(VIEWDATA_INITIALIZATION_DONE, Boolean.valueOf(true));
		
			v.postDelayed(new Runnable() {
	
				@Override
				public void run() {
					TabhostBaseFragment.this.closeWaitingView();
				}
			}, 
			3000);
		}
		else
		{
			this.closeWaitingView();
			// TODO
		}
		
		return v;
	}
	
	private void createTabViewImpl(Context ctx, int position)
	{
		View v = this.createTabView(ctx, position);
		if (v != null)
		{
			v.setTag(this);
			this.addView(v,  position);
		}
		
	}
	
	protected View createTabView(Context ctx, int position)
	{
		if (position == 0)
		{
			ImageView iv = new ImageView(ctx);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.weng8);
			return iv;
		}
		else if (position == 1)
		{
		
			LayoutInflater li = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = li.inflate(R.layout.pull_to_refresh_view_frame, null);
			return v;
		}
		else
		{
			return null;
		}
	}
	
	
	private void addView(View innerView, int position)
	{
		RelativeLayout.LayoutParams lp = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
						RelativeLayout.LayoutParams.MATCH_PARENT);

		if (position == 0)
		{
			this.tabViewFrame1.addView(innerView, lp);
		}
		else if (position == 1)
		{
			this.tabViewFrame2.addView(innerView, lp);
		}
		else if (position == 2)
		{
			this.tabViewFrame3.addView(innerView, lp);
		}
		else
		{
			assert false;
		}
	}

	
	protected int getTabCount()
	{
		return 3;
	}
	
	protected String getTabSpec(int tabPos)
	{
		return "测试用";
	}

	@Override
	public void showWaitingView() {
		this.tabHost.setVisibility(View.INVISIBLE);
		this.processingView.setVisibility(View.VISIBLE);
	}

	@Override
	public void closeWaitingView() {
		this.tabHost.setVisibility(View.VISIBLE);
		this.processingView.setVisibility(View.INVISIBLE);
		
	}
}
