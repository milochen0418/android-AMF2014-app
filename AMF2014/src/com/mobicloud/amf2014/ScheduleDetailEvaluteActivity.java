package com.mobicloud.amf2014;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ScheduleDetailEvaluteActivity extends Activity implements OnClickListener{

	public Spinner mScoreSpinner;
	public EditText mSuggestionEditText;
	public String mCurrentScore; 
	//static public GlobalVars.AmfScheduleDayClass sDayClass;
	
	
	public String mScoreStrs[] = {"0","1","2","3","4","5","6","7","8","9","10"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_detail_evalute);
		
		mSuggestionEditText = (EditText)findViewById(R.id.suggestion_content_text_view);
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.complete_btn).setOnClickListener(this);
		mScoreSpinner = (Spinner) findViewById(R.id.score_content_spinner);
		//ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, "CountriesStr");
		
		//String scoreStrs[] = {"0","1","2","3","4","5","6","7","8","9","10"};
		ArrayList<String> scoreStrArray  = new ArrayList<String>();
		int idx = 0;
		for(idx =0; idx < mScoreStrs.length; idx++) {
			scoreStrArray.add(mScoreStrs[idx]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, scoreStrArray);
		mScoreSpinner.setAdapter(adapter);
		mScoreSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("DEBUG_TAG","position = " + position);
				mCurrentScore = mScoreStrs[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		GlobalVars.AmfScheduleDayClass currentClass =  GlobalVars.getSharedInstance().mSelectedDayClass;
		//mScoreSpinner.setPrompt(currentClass.mScore);
		//mScoreSpinner.setPrompt("3");
		mScoreSpinner.setSelection(Integer.parseInt(currentClass.mScore));
		mSuggestionEditText.setText(currentClass.mSuggestion);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int vid = v.getId();
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		
		case R.id.complete_btn:
			Toast.makeText(this.getBaseContext(),(String)"評分完成", 
	                Toast.LENGTH_SHORT).show();
			saveStatus();
			super.onBackPressed();
			break;
		}
	}
	public void saveStatus() {
		GlobalVars.AmfScheduleDayClass currentClass =  GlobalVars.getSharedInstance().mSelectedDayClass;
		currentClass.mScore = mCurrentScore;
		currentClass.mSuggestion = mSuggestionEditText.getText().toString(); 
		
				
		
	}
}
