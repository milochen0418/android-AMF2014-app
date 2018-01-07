package com.mobicloud.amf2014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ScheduleDetailActivity extends Activity implements OnClickListener {

	public TextView mTimeHeaderTextView;
	public TextView mTimeContentTextView;
	public TextView mClassHeaderTextView;
	public TextView mClassContentTextView;
	public TextView mSpeakerHeaderTextView;
	public TextView mSpeakerContentTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_detail);
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.evalute_score_btn).setOnClickListener(this);
		mTimeHeaderTextView = (TextView) findViewById(R.id.time_header_text_view);
		mTimeContentTextView = (TextView) findViewById(R.id.time_content_text_view);
		mClassHeaderTextView = (TextView) findViewById(R.id.class_header_text_view);
		mClassContentTextView = (TextView) findViewById(R.id.class_content_text_view);
		mSpeakerHeaderTextView = (TextView) findViewById(R.id.speaker_header_text_view);
		mSpeakerContentTextView = (TextView) findViewById(R.id.speaker_content_text_view);
		
		
		GlobalVars.AmfScheduleDayClass currentClass =  GlobalVars.getSharedInstance().mSelectedDayClass;
		if(currentClass == null) {
			Log.i("DEBUG_TAG", "mSelectedDayClass is not set before launch this Activity in ScheduleActivity");
			super.onBackPressed();
		}
		GlobalVars vars = GlobalVars.getSharedInstance();
		mTimeContentTextView.setText( vars.mSelectedDayClass.mTime);
		mClassContentTextView.setText( vars.mSelectedDayClass.mClassName);
		mSpeakerContentTextView.setText( vars.mSelectedDayClass.mSpeakerIntroduction);
		
	//GlobalVars.AmfSchedule GlobalVars.getSharedInstance().mSelectedDayClass
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		case R.id.evalute_score_btn:
			Log.i("DEBUG_TAG", "call evalute score");
			intent = new Intent(this, ScheduleDetailEvaluteActivity.class);
			this.startActivity(intent);
			break;			
		}
	}
}
