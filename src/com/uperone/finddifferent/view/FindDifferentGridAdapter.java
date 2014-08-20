package com.uperone.finddifferent.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uperone.finddifferent.R;

public class FindDifferentGridAdapter extends BaseAdapter {
	public FindDifferentGridAdapter( Context context ){
		mLayoutInflater = ( LayoutInflater )context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	}
	
	public void updateContents( ArrayList<String> contentList ){
		if( null != mContentList ){
			mContentList.clear( );
		}
		
		mContentList = contentList;
		
		notifyDataSetChanged( );
	}
	
	@Override
	public int getCount() {
		return mContentList.size( );
	}

	@Override
	public Object getItem(int position) {
		return mContentList.get( position );
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if( null == convertView ){
			mViewHolder = new ViewHolder( );
			
			convertView = mLayoutInflater.inflate( R.layout.adapter_find_different_layout, null );
			
			mViewHolder.mItemTxt = ( TextView )convertView.findViewById( R.id.contentTxtId );
			
			convertView.setTag( mViewHolder );
		}else{
			mViewHolder = ( ViewHolder )convertView.getTag( );
		}
		
		showContent( mContentList, position );
		
		return convertView;
	}
	
	private void showContent( ArrayList<String> contentList, int position ){
		mViewHolder.mItemTxt.setText( contentList.get( position ) );
	}
	
	class ViewHolder{
		TextView mItemTxt = null;
	}

	private LayoutInflater mLayoutInflater = null;
	private ArrayList<String> mContentList = null;
	
	private ViewHolder mViewHolder = null;
}
