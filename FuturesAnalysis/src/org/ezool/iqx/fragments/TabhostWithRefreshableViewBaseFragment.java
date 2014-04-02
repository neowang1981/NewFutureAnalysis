package org.ezool.iqx.fragments;

import org.ezool.iqx.R;
import org.ezool.iqx.views.PullToRefreshGeneralView;
import org.ezool.iqx.views.SwitchablePullToRefreshGeneralView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 第一个tab为下拉刷新式视图，且可以自定义的tab
 * 缺省tab为3个，包括tab标题在内均可以自定义
 *
 */
public class TabhostWithRefreshableViewBaseFragment extends TabhostBaseFragment{
	
	private FrameLayout contentFrame;
	
	@Override
	protected View createTabView(Context ctx, int position)
	{
		if (position == 0)
		{
			LayoutInflater li = LayoutInflater.from(ctx);
			PullToRefreshGeneralView v = 
					(PullToRefreshGeneralView)li.inflate(R.layout.pull_to_refresh_view_frame, null, false);
			
			this.contentFrame = (FrameLayout)v.findViewById(R.id.pull_to_refresh_innerview);
			this.contentFrame.addView(this.getFirstChildView(ctx), 
					new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return v;
		}
		else
		{
			return this.getFirstElseChildView(ctx, position);
		}
	}
	
	/**
	 * 得到第一个tab的view（可以刷新的）
	 * 此方法应当在派生类中被重写
	 * @param ctx
	 * @return
	 */
	protected View getFirstChildView(Context ctx)
	{
		ImageView iv = new ImageView(ctx);
		iv.setBackgroundResource(R.drawable.weng8);
		return iv;
	}
	
	/**
	 * 得到第一个tab以外的view（不可以刷新的）
	 * 此方法应当在派生类中被重写
	 * @param ctx
	 * @param pos
	 * @return
	 */
	protected View getFirstElseChildView(Context ctx, int pos)
	{
		if (pos == 1)
		{
			ImageView iv = new ImageView(ctx);
			iv.setBackgroundResource(R.drawable.weng8);
			return iv;
		}
		else
		{
			return null;
		}
	}
}
