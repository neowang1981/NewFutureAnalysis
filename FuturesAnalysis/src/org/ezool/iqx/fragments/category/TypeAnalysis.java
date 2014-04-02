package org.ezool.iqx.fragments.category;

import org.ezool.iqx.fragments.CategoryBaseFragment;
import org.ezool.iqx.fragments.PullToRefreshListBaseFragment;
import org.ezool.iqx.fragments.TabhostBaseFragment;
import org.ezool.iqx.fragments.TabhostWithRefreshableViewBaseFragment;
import org.ezool.iqx.fragments.ViewBaseFragment;

import android.support.v4.app.Fragment;

/**
 * 品种分析
 */
public class TypeAnalysis extends CategoryBaseFragment {
	
	// tab标题
	private final String[] titles= new String[]
			{
				"品种多空胜率",
				"成交结构",
				"持仓结构",
				"品种盈亏",
				"品种多空成交",
				"品种多空盈利",
				"品种笔数"
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
		if (position == 1)
		{
			TabhostWithRefreshableViewBaseFragment f = 
				new TabhostWithRefreshableViewBaseFragment();
			return f;
		}
		else
		{
			return new PullToRefreshListBaseFragment();
		}
	}
}
