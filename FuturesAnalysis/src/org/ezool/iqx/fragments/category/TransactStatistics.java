package org.ezool.iqx.fragments.category;

import org.ezool.iqx.fragments.CategoryBaseFragment;
import org.ezool.iqx.fragments.PullToRefreshListBaseFragment;
import org.ezool.iqx.fragments.TabhostWithRefreshableViewBaseFragment;
import org.ezool.iqx.fragments.ViewBaseFragment;
import org.ezool.iqx.fragments.category.details.TransactStatisticsDetailFragment;

/**
 * 交易统计
 */
public class TransactStatistics extends CategoryBaseFragment{
	
	// tab标题
	private final String[] titles= new String[]
			{
				"交易统计"
			};
	
	/**
	 * 返回指定位置tab的标题
	 * 派生自父类
	 * @param pos tab的位置，以0开始
	 * @return tab的标题
	 */
	protected String getTabsTitle(int pos)
	{
		return this.titles[pos];
	}
	
	/**
	 * 返回总共tab的个数，此处只是示例
	 * 派生自父类
	 * @return tab的个数
	 */
	protected int getTabsCount()
	{
		return this.titles.length;
	}
	
	/**
	 * 返回指定tab位置的fragment
	 * 派生自父类
	 * @param position tab位置
	 * @return tab的fragment
	 */
	protected ViewBaseFragment getFragmentByTabIndex(int position)
	{
		// 本category只有一个子tab，交易统计
		if (position == 0)
		{
			return new TransactStatisticsDetailFragment();
		}
		else
		{
			return null;
		}
	}
}
