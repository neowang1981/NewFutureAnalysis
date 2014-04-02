package org.ezool.iqx;


import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.graphics.Bitmap.Config;
import android.os.Looper;
import android.util.Log;

public class FuturesAnalysisApplication extends Application{
		private static final String TAG = FuturesAnalysisApplication.class.getName();

		// 启动的Activity集合
		public List<Activity> mActivityList = new ArrayList<Activity>();

		/** 程序启动时的处理 */
		@Override
		public void onCreate() {
			super.onCreate();
			// 出现应用级异常时的处理
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable throwable) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// 弹出报错并强制退出的对话框
						if (mActivityList.size() > 0) {
							Looper.prepare();
							new AlertDialog.Builder(getCurrentActivity())
									.setTitle(R.string.app_name)
									.setMessage(R.string.err_fatal)
									.setPositiveButton(
											R.string.confirm,
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// 强制退出程序
													finish();
												}
											}).show();
							Looper.loop();
						}
					}
				}).start();

				// 错误LOG
				Log.e(TAG, throwable.getMessage(), throwable);
				}
			});
			init();
		}

		private void init()  {
			try {
				// 此处为了加快速度
				Class.forName("org.ezool.iqx.events.EventBusWrapper");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
			
			/*DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisc(true)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 
			.bitmapConfig(Config.RGB_565)  // 防止内存溢出 如Bitmap.Config.ARGB_8888
			//.showImageOnLoading(R.drawable.load_flower)   //默认图片       
			//.showImageOnFail(R.drawable.k2k2k2k)// 加载失败显示的图片
			//.displayer(new RoundedBitmapDisplayer(5))  //圆角，不需要请删除
			.build();


	


			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			//.memoryCacheExtraOptions(480, 800)  // 缓存在内存的图片的宽和高度
			//.discCacheExtraOptions(480, 800, CompressFormat.PNG, 70,null) //CompressFormat.PNG类型，70质量（0-100）
			.memoryCache(new WeakMemoryCache()) 
			.memoryCacheSize(2 * 1024 * 1024)  //缓存到内存的最大数据
			.discCacheSize(100 * 1024 * 1024)  //缓存到文件的最大数据
			.discCacheFileCount(1000)  //文件数量
			.defaultDisplayImageOptions(options).  //上面的options对象，一些属性配置
			build();
			
			ImageLoader.getInstance().init(config);  //初始化
			*/
		}

		/**
		 * 关闭程序所有的Activity
		 */
		public void clearActivityList() {
			for (int i = 0; i < mActivityList.size(); i++) {
				mActivityList.get(i).finish();
			}
			mActivityList.clear();
		}

		/**
		 * 关闭时的处理
		 */
		public void finish() {
			// 关闭程序所有的Activity
			clearActivityList();

			// 退出
			System.exit(0);
		}

		/**
		 * 获得当前最前端的Activity
		 */
		public Activity getCurrentActivity() {
			if (mActivityList.size() > 0) {
				return mActivityList.get(mActivityList.size() - 1);
			}
			return null;
		}
}

