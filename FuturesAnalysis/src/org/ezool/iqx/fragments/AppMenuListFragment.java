package org.ezool.iqx.fragments;

import org.ezool.iqx.MainActivity;
import org.ezool.iqx.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 右侧的滑动菜单的主Fragment
 * 用以选择应用程序设定功能
 */
public class AppMenuListFragment extends ListFragment{

	private MenuItem[] menuInfo = new MenuItem[] {
		new MenuItem("退出程序", android.R.drawable.ic_menu_search)
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
	 * 当菜单中某一项被点击时调用本方法
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		//取得依附的Activity
		MainActivity ma = (MainActivity)this.getActivity();
		
		if (position == 0)
		{
			System.exit(0);
		}
		
	}
	
	/**
	 * 右侧菜单信息项
	 *
	 */
	private class MenuItem {
		/** 菜单文字 */
		public String tag;
		/** 菜单图标 */
		public int iconRes;
		
		public MenuItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}
	
	/**
	 * 右侧滑动菜单栏的适配器
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
