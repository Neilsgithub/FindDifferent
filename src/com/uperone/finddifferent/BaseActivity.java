package com.uperone.finddifferent;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.game.UMGameAgent;

public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init( );
		initUmeng( );
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		UMGameAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		UMGameAgent.onPause(this);
	}
	
	private void initUmeng( ){
		UMGameAgent.setDebugMode(true);//设置输出运行时日志
		UMGameAgent.init( this );
	}
	
	private void init( ){
		setContentView( );
		findViews( );
		getData( );
		showContent( );
	}
	
	public abstract void setContentView( );
	public abstract void findViews( );
	public abstract void getData( );
	public abstract void showContent( );
}
