package org.ezool.iqx.fragments;

import org.ezool.iqx.MainActivity;
import org.ezool.iqx.R;
import org.ezool.iqx.events.EventBusWrapper;
import org.ezool.iqx.fragments.category.TransactStatistics;
import org.ezool.iqx.fragments.category.TypeAnalysis;
import org.zool.iqx.config.ActionConfigurations;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 左侧的滑动菜单的主Fragment
 * 用以选择主屏幕的业务类别
 */
public class MenuListFragment extends ListFragment{
	
	private int previousSelectedList = -1;
	
	private MenuItem[] menuInfo = new MenuItem[] {
		new MenuItem("交易统计", android.R.drawable.ic_menu_search, TransactStatistics.class),
		new MenuItem("品种分析", android.R.drawable.ic_menu_search, TypeAnalysis.class),
		new MenuItem("CategoryBaseFragment", android.R.drawable.ic_menu_search, CategoryBaseFragment.class),
		new MenuItem("Sample List2", android.R.drawable.ic_menu_search, null)
	};
	
	/**
	 * 产生所显示的界面（只有一个ListView）
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	/**
	 * 初始化ListView
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MenuAdapter adapter = new MenuAdapter(getActivity());
		for(int i = 0; i < this.menuInfo.length; ++i)
		{
			adapter.add(this.menuInfo[i]);
		}
		this.setListAdapter(adapter);
	}

	/**
	 * 为了第一次显示时显示第一个菜单项，这里判断previousSelectedList
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		if(this.previousSelectedList < 0)
		{
			this.previousSelectedList = 0;
			this.simulateFirstItemSelected();
		}
	}

	/**
	 * 第一次启动时模拟选中第一项菜单项
	 * 复制了onListItemClick中的一部分代码
	 * 有点漏
	 */
	private void simulateFirstItemSelected()
	{
		//取得依附的Activity
		MainActivity ma = (MainActivity)this.getActivity();
		
		MenuItem mi = this.menuInfo[0];
		
		// sanity check
		// 如果有实例化的class且此class是派生自CategoryBaseFragment的话，则显示此fragment。否则不予显示
		if (mi.categoryFragment != null && CategoryBaseFragment.class.isAssignableFrom(mi.categoryFragment))
		{
			CategoryBaseFragment fragInst = this.loadCategoryFragmentInstance(0);
			
			//如果还没有实例，则生成并放入菜单信息中
			if (fragInst == null)
			{
				try {
					fragInst = (CategoryBaseFragment)mi.categoryFragment.newInstance();
					this.saveCategoryFragmentInstance(0, fragInst);
				} catch (java.lang.InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//主界面fragment的替换操作
			ma.switchContent(fragInst);
		}
	}
	
	
	/**
	 * 当菜单中某一项被点击时调用本方法
	 * 本方法将替换主显示界面中的fragment，并隐藏当前的滑动菜单
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		//取得依附的Activity
		MainActivity ma = (MainActivity)this.getActivity();
		
		//若相同的菜单项，啥都不做
		if (position == this.previousSelectedList)
		{
			ma.switchContent(null);
		}
		else
		{
			// 记住当前所选菜单项位置
			this.previousSelectedList = position;
			
			MenuItem mi = this.menuInfo[position];
			
			// sanity check
			// 如果有实例化的class且此class是派生自CategoryBaseFragment的话，则显示此fragment。否则不予显示
			if (mi.categoryFragment != null && CategoryBaseFragment.class.isAssignableFrom(mi.categoryFragment))
			{
				CategoryBaseFragment fragInst = this.loadCategoryFragmentInstance(position);
				
				//如果还没有实例，则生成并放入菜单信息中
				if (fragInst == null)
				{
					try {
						fragInst = (CategoryBaseFragment)mi.categoryFragment.newInstance();
						this.saveCategoryFragmentInstance(position, fragInst);
					} catch (java.lang.InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//若已经有了实例，则简单取得
				else
				{
					//貌似没处理了
				}
				
				//主界面fragment的替换操作
				ma.switchContent(fragInst);
			}
		}
		
	}
	
	/**
	 * 下面两个接口的说明
	 * 我们需要将生成的菜单对应的fragment缓存起来，因为它们包含了所有各个子tab显示所需要的信息
	 * 所以这些fragment不应该每次都由new产生，而只应该new一次
	 * 但是本fragment可能被销毁和重新生成，而在本应用中，唯一不会被摧毁的对象是MainActivity
	 * 所以下面两个方法实质上是把产生的fragment缓存到MainActivity中
	 */
	/**
	 * 将菜单对应的category保存到MainActivity
	 * @param position 菜单位置，从0开始
	 * @param f 对应需要保存的fragment
	 */
	private void saveCategoryFragmentInstance(int position, CategoryBaseFragment f)
	{
		// 直接调用MainActivity的接口方法
		((MainActivity)this.getActivity()).setCategoryBaseFragment(position, f);
	}
	
	/**
	 * 获取菜单对应的category
	 * @param position 菜单位置，从0开始
	 * @return 对应的被保存的fragment。如果没有则返回null
	 */
	private CategoryBaseFragment loadCategoryFragmentInstance(int position)
	{
		// 直接调用MainActivity的接口方法
		return ((MainActivity)this.getActivity()).getCategoryBaseFragment(position);
	}

	/**
	 * 菜单信息项
	 *
	 */
	private class MenuItem {
		/** 菜单文字 */
		public String tag;
		/** 菜单图标 */
		public int iconRes;
		/** 按下时所显示的Class */
		public Class categoryFragment;
		
		public MenuItem(String tag, int iconRes, Class c) {
			this.tag = tag; 
			this.iconRes = iconRes;
			this.categoryFragment = c;
		}
	}

	/**
	 * 左侧滑动菜单栏的适配器
	 */
	public class MenuAdapter extends ArrayAdapter<MenuItem> {

		public MenuAdapter(Context context) {
			super(context, 0);
		}

		/**
		 * 取得某一行菜单栏
		 * 完全按照getItem的结果来进行
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(this.getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(this.getItem(position).tag);
			return convertView;
		}
	}
}
