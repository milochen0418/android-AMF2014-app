package com.mobicloud.amf2014;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.pieapple.loadwebimage.*;
import com.pieapple.loadwebimage.imageutils.*;



public class TeacherActivity extends Activity implements OnClickListener,ListView.OnItemClickListener {
	/*
	 * speech api 講師介紹 (用在 講師介紹 的GUI) 
	 * http://www.amf.com.tw/api/en/speech/index.php
	 * http://www.amf.com.tw/api/zh/speech/index.php
	 * 
	 */

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.left_content_btn).setOnClickListener(this);
		findViewById(R.id.right_content_btn).setOnClickListener(this);
		loadListView(0);
	}
	
	
	public ListView mListView;
	public ArrayAdapter mAdapter;
	public String[] mStrs;
	public int mCurrentTabIdx;
	
	

    public LazyAdapter mLazyAdapter;
    public String[] mImageUrls;
    
    public TeacherLazyAdapter mTeacherLazyAdapter;
    
    
	
	public void loadListView(int tabIdx) {
		if(tabIdx >= 0 && tabIdx <2 ) {
			mCurrentTabIdx = tabIdx;
		}
		else {
			Log.i("DEBUG_TAG", "loadListView() call but failed with tabIdx = "+ tabIdx);
			return; 
		}
		
		mListView = (ListView)findViewById(R.id.list_view);
		GlobalVars.AmfSpeechLector lector = GlobalVars.getSharedInstance().mAmfSpeech.mLectors.get(tabIdx);
		mStrs = new String[lector.mSpeechList.size()];
		mImageUrls = new String[lector.mSpeechList.size()];
		for (int i = 0; i < mStrs.length ; ++i ) {
			GlobalVars.AmfSpeechTeacher teacher = lector.mSpeechList.get(i);
			mStrs[i] = teacher.mName + "\n" +  teacher.mJobTitle ;
			mImageUrls[i] = teacher.mImgUrl;
			
		}
		mListView.setAdapter(null);
		boolean isUseLazy = true;
		if(isUseLazy) {
			/*
			mLazyAdapter = new LazyAdapter(this,mImageUrls);
			mListView.setAdapter(mLazyAdapter);
			*/
			TeacherLazyAdapter.LazyItemData[] lazyItemData = new TeacherLazyAdapter.LazyItemData[lector.mSpeechList.size()];
			int idx;
			for ( idx = 0; idx < lazyItemData.length;idx++) {
				GlobalVars.AmfSpeechTeacher teacher = lector.mSpeechList.get(idx);
				TeacherLazyAdapter.LazyItemData data = new TeacherLazyAdapter.LazyItemData(teacher.mName, teacher.mJobTitle, teacher.mImgUrl);
				lazyItemData[idx] = data;
			}
			mTeacherLazyAdapter = new TeacherLazyAdapter(this, lazyItemData);
			mListView.setAdapter(mTeacherLazyAdapter);
			mListView.setOnItemClickListener (this);
			
		}
		else {
			mAdapter = new ArrayAdapter (this, R.layout.teacher_list_item, mStrs);
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener (this);
		}

		
		//mListView.setOnItemClickListener(new OnItemClickListener())
	}
	
	
	@Override
	public void onClick(View v) {
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		case R.id.left_content_btn:
			loadListView(0);
			((Button)findViewById(R.id.right_content_btn)).setBackgroundColor(Color.rgb( 0x00, 0x99, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setBackgroundColor(Color.rgb( 0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setTextColor(Color.rgb(0x00, 0x99, 0xFF));
			

			break;
		case R.id.right_content_btn:
			loadListView(1);
			((Button)findViewById(R.id.left_content_btn)).setBackgroundColor(Color.rgb( 0x00, 0x99, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setBackgroundColor(Color.rgb( 0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setTextColor(Color.rgb(0x00, 0x99, 0xFF));
			break;
		}		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.i("DEBUG_TAG", "onItemClick position = "+position+"; id = "+ id + " when mCurrentTabIdx = " + mCurrentTabIdx);
		
		GlobalVars.getSharedInstance().mCurrentTeacher = 
				GlobalVars.getSharedInstance().mAmfSpeech.mLectors.get(mCurrentTabIdx).mSpeechList.get(position);
		Intent intent = new Intent(this, TeacherDetailActivity.class);
		this.startActivity(intent);
	}
	
	
    @Override
    public void onDestroy()
    {
    	if(mListView != null) {
    		mListView.setAdapter(null);
    	}
        super.onDestroy();
    }
	
}
