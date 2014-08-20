package com.uperone.finddifferent;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.uperone.finddifferent.util.DataUtils;
import com.uperone.finddifferent.view.FindDifferentGridAdapter;

public class FindDifferentActivity extends BaseActivity {
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_find_different_layout);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		keyUp( keyCode, event );
		return super.onKeyUp(keyCode, event);
	}
	
	private void keyUp( int keyCode, KeyEvent event ){
		switch( keyCode ){
		case KeyEvent.KEYCODE_MENU:{
			reset( );
			updateContent( );
			startGame( );
		}
		break;
		default:{
			
		}
		break;
		}
	}
	
	@Override
	public void findViews() {
		mTimeCounterTxt = ( TextView )findViewById(R.id.timeCounterTxtId);
		mRightCounterTxt = ( TextView )findViewById(R.id.rightCounterTxtId);
		mFindDifferentGridView = ( GridView )findViewById(R.id.findDifferentGridViewId);
		mFindDifferentGridView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if( arg2 == mKeySelect ){
					mRowCount += 1;
					mRightCnt += 1;
					if( mRowCount >= MAX_ROW_COUNT ){
						mRowCount = MAX_ROW_COUNT; 
					}
					
					updateContent( );
					showRightCounter( mRightCnt );
				}
			}
		});
		
		setGridViewWidth( );
	}
	
	@Override
	public void getData() {
		mKeySelect = getKeySelect( mRowCount );
		mContentList = getContentList( mRowCount, mKeySelect );
	}
	
	@Override
	public void showContent() {
		mFindDifferentGridAdapter = new FindDifferentGridAdapter( this );
		mFindDifferentGridView.setNumColumns( mRowCount );
		mFindDifferentGridAdapter.updateContents( mContentList );
		mFindDifferentGridView.setAdapter( mFindDifferentGridAdapter );
		
		startGame( );
	}
	
	private void startGame( ){
		showRightCounter( mRightCnt );
		showTimeCounter( GAME_TIME_LEN );
		startTickTimer( );
	}
	
	private void updateContent( ){
		getData( );
		mFindDifferentGridView.setNumColumns( mRowCount );
		mFindDifferentGridView.setColumnWidth( mGridWidth/ mRowCount );
		mFindDifferentGridAdapter.updateContents( mContentList );
	}
	
	private ArrayList<String> getContentList( int rowCount, int keySelect ){
		int select = getSelect( );
	
		ArrayList<String> contentList = new ArrayList<String>( );
		for( int rowIndex = 0; rowIndex < rowCount; rowIndex++ ){
			for( int coloumIndex = 0; coloumIndex < rowCount; coloumIndex++ ){
				if( rowIndex * rowCount + coloumIndex == keySelect ){
					contentList.add( DataUtils.mKeyWords[select][1] );
				}else{
					contentList.add( DataUtils.mKeyWords[select][0] );
				}
			}
		}
		
		return contentList;
	}
	
	private int getGridViewWidth( ){
		WindowManager manage=getWindowManager();
        Display display=manage.getDefaultDisplay();
        
        int screenHeight = display.getHeight();
        int screenWidth = display.getWidth();
        
        int gridWidth = ( screenHeight < screenWidth )?screenHeight:screenWidth;
        gridWidth = 5*gridWidth/7 ;
        
        return gridWidth;
	}
	
	private void setGridViewWidth( ){
		int width = getGridViewWidth( );
		LayoutParams layoutParams = (LayoutParams)mFindDifferentGridView.getLayoutParams( );
		
		layoutParams.width = width;
		layoutParams.height = width;
		
		mFindDifferentGridView.setLayoutParams( layoutParams );
	}
	
	private int getKeySelect( int rowCount ){
		int max = rowCount * rowCount;
		Random random = new Random( );
		
		return random.nextInt( max );
	}
	
	private int getSelect( ){
		Random random = new Random( );
		return random.nextInt( DataUtils.mKeyWords.length );
	}
	
	private void startTickTimer( ){
	    cancleTickTimer( );
	    
	    mTickTimer = new Timer( );
	    mTickTimer.schedule( new TickTimerTask( ), SECONDE_LEN );
	}
	
	private void cancleTickTimer( ){
	    if( null != mTickTimer ){
	        mTickTimer.cancel( );
	        mTickTimer = null;
	    }
	}
	
	class TickTimerTask extends java.util.TimerTask{
	    public void run( ){
	    	if( mCurrentTime == GAME_TIME_LEN ){
	    		cancleTickTimer( );
	    	}else{
	    		mCurrentTime++;
	    		showTimeCounter( GAME_TIME_LEN - mCurrentTime );
	    		startTickTimer( );
	    	}
	    }
	}
	
	private void reset( ){
		mCurrentTime = 0;
		mRowCount = 2;
		mRightCnt = 0;
	}
	
	private void showRightCounter( final int rightCount ){
		FindDifferentActivity.this.runOnUiThread( new Runnable( ) {
			@Override
			public void run() {
				mRightCounterTxt.setText( rightCount + "" );
			}
		});
	}
	
	private void showTimeCounter( final int currentTime ){
		FindDifferentActivity.this.runOnUiThread( new Runnable( ) {
			@Override
			public void run() {
				mTimeCounterTxt.setText( currentTime + "" );
			}
		});
	}
	
	private int mCurrentTime = 0;
	private static final int SECONDE_LEN = 1000;
	private static final int GAME_TIME_LEN = 30;
	private static final int MAX_ROW_COUNT = 15;
	private int mGridWidth = 600;
	private int mRowCount = 2;
	private int mKeySelect = -1;
	private int mRightCnt = 0;
	private TextView mRightCounterTxt = null;
	private TextView mTimeCounterTxt = null;
	private GridView mFindDifferentGridView = null;
	private FindDifferentGridAdapter mFindDifferentGridAdapter = null;
	private ArrayList<String> mContentList = null;
	
	private Timer mTickTimer = null;
}
