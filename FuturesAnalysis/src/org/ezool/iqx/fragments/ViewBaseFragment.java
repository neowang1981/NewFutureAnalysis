package org.ezool.iqx.fragments;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.ezool.iqx.MainActivity;

import android.support.v4.app.Fragment;

/**
 * 某一个tab的基类fragment
 */
abstract public class ViewBaseFragment extends Fragment{
	
	/** 本fragment在tab中的位置，从0开始 */
	private int indicatorPosition = -1;
	
	/** 本fragment的外层fragment，其必定是从CategoryBaseFragment派生的 */
	private CategoryBaseFragment containerFragment;

	/** 通信处理后的结果 */
	private int communicateResult;
	
	/**
	 * 获取本fragment在tab中的位置
	 * @return 位置。从0开始
	 */
	private int getIndicatorPosition() {
		return indicatorPosition;
	}

	/**
	 * 设置本fragment在tab中的位置
	 * 本方法由框架调用，不需要由使用者调用
	 */
	public final void setIndicatorPosition(int indicatorPosition) {
		this.indicatorPosition = indicatorPosition;
	}
	
	/**
	 * 取得本tab用以显示的数据
	 * 本方法将由本fragment下属的各个View调用
	 * @return 显示的数据。如果没有数据则返回null
	 */
	public final Serializable getViewDataFromSave()
	{
		MainActivity ma = (MainActivity)this.getActivity();
		return ma.getTabData(this.getIndicatorPosition());
	}
	
	/**
	 * 保存本tab用以显示的数据
	 * @param data 需要保存的显示数据
	 * @return true：成功 false：失败
	 */
	protected boolean setViewDataToSave(Serializable data)
	{
		MainActivity ma = (MainActivity)this.getActivity();
		return ma.setTabData(this.getIndicatorPosition(), data);
	}
	
	/**
	 * 设定本fragment的外层fragment，用来提供CategoryBaseFragment中的一些接口
	 * @param baseFragment 外层fragment
	 */
	public final void setContainerFragment(CategoryBaseFragment baseFragment)
	{
		this.containerFragment = baseFragment;
	}
	
	/**
	 * 得到本fragment的外层fragment，用来提供CategoryBaseFragment中的一些接口
	 * @return 外层fragment
	 */
	private final CategoryBaseFragment getContainerFragment()
	{
		return this.containerFragment;
	}
	
	/**
	 * 设置tab表示用的信息
	 * 采用的是代理模式
	 * 由子类调用
	 * @param key KEY值
	 * @param data 表示用的信息
	 */
	protected void setTabDisplayData(String key, Object data)
	{
		this.getContainerFragment().setTabDisplayData(
				this.getIndicatorPosition(), key, data);
	}
	
	
	/**
	 * 得到tab表示用的信息
	 * 采用的是代理模式
	 * 由子类调用
	 * @param key KEY值
	 * @return 表示用的信息。如果没有信息则返回null
	 */
	protected Object getTabDisplayData(String key)
	{
		return this.getContainerFragment().getTabDisplayData(
				this.getIndicatorPosition(), key);
	}
	
	/**
	 * 显示处理中界面
	 */
	abstract protected void showWaitingView();
	
	/**
	 * 关闭处理中界面并恢复到正常显示界面（或者报错）
	 */
	abstract protected void closeWaitingView();
	
	/**
	 * 服务器通信调用入口
	 * 用于获取本tab的fragment专属的显示数据
	 * 本处理必须在子类中被覆盖
	 * @return 通信后的数据
	 */
	protected Serializable communicate()
	{
		return null;
	}
	
	/**
	 * 设置通信处理的结果码
	 * @param code 结果码
	 */
	protected void setCommunicateResult(int code)
	{
		this.communicateResult = code;
	}
	
	/**
	 * 得到通信处理的结果码
	 * @return 结果码
	 */
	protected int getCommunicateResult()
	{
		return this.communicateResult;
	}
}
