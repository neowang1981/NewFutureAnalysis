package org.ezool.iqx;

import org.ezool.iqx.fragments.MenuListFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


/**
 * 主显示类的基类
 * @author WANGJI
 */
public class BaseActivity extends SlidingFragmentActivity {

	/** 标题的资源号 */
	private int mTitleRes;
	
	/** 左侧滑动菜单的fragment */
	protected ListFragment mFrag;

	public BaseActivity(int titleRes) {
		this.mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(this.mTitleRes);

		// 设定左侧滑动菜单的布局
		this.setBehindContentView(R.layout.menu_frame);
		
		// 用MenuListFragment来替换布局中的占位符
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			this.mFrag = new MenuListFragment();
			t.replace(R.id.menu_frame, this.mFrag);
			t.commit();
		} else {
			this.mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// 左侧滑菜单设置
		SlidingMenu sm = this.getSlidingMenu();
		//sm.setShadowWidthRes(R.dimen.shadow_width);
		//sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.75f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);


		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}