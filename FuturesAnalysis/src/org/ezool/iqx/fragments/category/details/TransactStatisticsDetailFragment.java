package org.ezool.iqx.fragments.category.details;

import org.ezool.iqx.R;
import org.ezool.iqx.fragments.PullToRefreshListBaseFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

/**
 * 交易统计的tab fragment
 * 一级tab：交易统计
 * 二级tab：交易统计
 *
 */
public class TransactStatisticsDetailFragment extends PullToRefreshListBaseFragment{
	
	/**
	 * 返回listview的适配器
	 */
	@Override
	protected ListAdapter getListAdapter(Context ctx)
	{
		return new TransactStatisticsListViewAdapter(
				ctx, new int[]{0, 1, 1, 2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 0, 1, 2});
	}
	
	private class TransactStatisticsListViewAdapter extends BaseAdapter{
		
		//数据源
		private Context context;
		private int []type;
	 
		//构造函数
		public TransactStatisticsListViewAdapter(Context context, int[] typeArray){
			
			this.context = context;
			this.type = typeArray;
			
		}
		 
		@Override
		public int getCount() {
			//return list.size();
			return this.type.length;
		}
		 
		@Override
		public Object getItem(int position) {
			//return list.get(position);
			return null;
		}
		 
		@Override
		public long getItemId(int position) {
			return position;
		}
	 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			//产生一个View
			View view = null;
			//根据type不同的数据类型构造不同的View
			if (type[position]==1)
			{
				view = mInflater.inflate(R.layout.transact_statistics_item_layout, null);
			}
			else if (type[position]==0)
			{
				view = mInflater.inflate(R.layout.transact_statistics_title_layout, null);
			}
			else if (type[position]==2)
			{
				view = mInflater.inflate(R.layout.transact_statistics_bottom_layout, null);
			}

			return view;
		}
		
	}
}
