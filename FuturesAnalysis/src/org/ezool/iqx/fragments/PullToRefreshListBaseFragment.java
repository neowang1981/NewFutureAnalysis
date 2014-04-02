package org.ezool.iqx.fragments;

import java.io.Serializable;

import org.ezool.iqx.R;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

/**
 * 下拉刷新列表的基类fragment
 * 是单个下拉刷新list界面的基类
 */
public class PullToRefreshListBaseFragment extends ViewBaseFragment{
	
	/** 初次刷新的flag */
	private static final String VIEWDATA_INITIALIZATION_DONE = "VIEWDATA_INITIALIZATION_DONE";
	
	/** 更新中界面的frame view */
	private RelativeLayout processingView;
	
	/** 下拉刷新界面的主list */
	private PullToRefreshListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// 生成内部view并初始化各个子view变量
		View v = inflater.inflate(R.layout.view_with_refresh_list_frame, container, false);
		this.processingView = (RelativeLayout)v.findViewById(R.id.ready_wait_layout);
		this.listView = (PullToRefreshListView)v.findViewById(R.id.pull_refresh_list);
		
		// 试图取得本fragment显示所需要的数据
		Serializable viewData = this.getViewDataFromSave();
		// 取得是否已经做过一次初始化数据取得的flag
		Boolean b = (Boolean)this.getTabDisplayData(VIEWDATA_INITIALIZATION_DONE);
		
		// 设置list的适配器
		this.listView.setAdapter(this.getListAdapter(this.getActivity()));
		
		// 如果没有数据且未做过初始化，则与服务器进行通信
		if (viewData == null && b == null)
		{
			// 首先显示更新中界面
			this.showWaitingView();
			
			// 设置是否已经做过一次初始化数据取得的flag为true
			this.setTabDisplayData(VIEWDATA_INITIALIZATION_DONE, Boolean.valueOf(true));
		
			
			v.postDelayed(new Runnable() {
	
				@Override
				public void run() {
					PullToRefreshListBaseFragment.this.closeWaitingView();
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
	
	/**
	 * 显示处理中界面
	 */
	@Override
	public void showWaitingView() {
		this.listView.setVisibility(View.INVISIBLE);
		this.processingView.setVisibility(View.VISIBLE);
	}

	/**
	 * 关闭处理中界面并恢复正常显示
	 */
	@Override
	public void closeWaitingView() {
		this.listView.setVisibility(View.VISIBLE);
		this.processingView.setVisibility(View.INVISIBLE);
		
	}
	
	/**
	 * 设置list的适配器
	 * 本方法必须在子类中实现
	 * @param ctx 本界面的context
	 * @return list的适配器
	 */
	protected ListAdapter getListAdapter(Context ctx)
	{
		return null;
	}
	
	
}
