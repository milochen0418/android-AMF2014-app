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
import android.widget.Button;

public class ScheduleActivity extends Activity implements OnClickListener {
/*
 * 議程表 Agenda , 實作方式，請參考本網址的表，把它用在 WebView 上面就可以 
 * http://www.amf.com.tw/schedule.php
 * 所以請使用內嵌的 html 來實作, 自己到官網去抄一抄吧 
 * 
 * 但Crux 好像沒有給議程表的相關內容  或 API ?
 * 不，應該是有些人它的主講人及談人的資料內容會比較多時，你就給它設定說，按下去後就再進入一頁，show 詳細的內容 
 */
	
	
	int[] day1_ids = new int [] { 
		R.id.table_row_day_1_01, R.id.table_row_day_1_02,R.id.table_row_day_1_03, R.id.table_row_day_1_04, 
		R.id.table_row_day_1_05, R.id.table_row_day_1_06, R.id.table_row_day_1_07 
	};
	
	int[] day2_ids = new int[] {
			R.id.table_row_day_2_01, R.id.table_row_day_2_02,R.id.table_row_day_2_03, R.id.table_row_day_2_04,
			R.id.table_row_day_2_05, R.id.table_row_day_2_06,R.id.table_row_day_2_07, R.id.table_row_day_2_08,R.id.table_row_day_2_09
	};
/*
	int[] day2_ids = new int [] { 
			R.id.table_row_day_1_01, R.id.table_row_day_1_02,R.id.table_row_day_1_03, R.id.table_row_day_1_04, 
			R.id.table_row_day_1_05, R.id.table_row_day_1_06, R.id.table_row_day_1_07 
	};
*/	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		
		//create AmfSchedule if it is null;
		if(GlobalVars.getSharedInstance().mAmfSchedule == null) {
			GlobalVars.getSharedInstance().mAmfSchedule = new GlobalVars.AmfSchedule();
		}
		
		
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.left_content_btn).setOnClickListener(this);
		findViewById(R.id.right_content_btn).setOnClickListener(this);
		/*
		findViewById(R.id.table_row_day_1_01).setOnClickListener(this);
		findViewById(R.id.table_row_day_1_02).setOnClickListener(this);
		findViewById(R.id.table_row_day_1_03).setOnClickListener(this);
		findViewById(R.id.table_row_day_1_04).setOnClickListener(this);
		findViewById(R.id.table_row_day_1_05).setOnClickListener(this);
		findViewById(R.id.table_row_day_1_06).setOnClickListener(this);
		findViewById(R.id.table_row_day_1_07).setOnClickListener(this);
*/
		int day1_idx = 0;
		for (day1_idx = 0 ; day1_idx < day1_ids.length; day1_idx++ ) {
			View v = findViewById(day1_ids[day1_idx]);
			if(v!=null) {
				v.setOnClickListener(this);
			}
		}
		int day2_idx = 0;
		for (day2_idx = 0 ; day2_idx < day2_ids.length; day2_idx++ ) {
			View v = findViewById(day2_ids[day2_idx]);
			if(v!=null) {
				v.setOnClickListener(this);
			}
		}


	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		case R.id.left_content_btn:
			findViewById(R.id.day_1_info_view).setVisibility(View.VISIBLE);
			findViewById(R.id.day_2_info_view).setVisibility(View.GONE);
			((Button)findViewById(R.id.right_content_btn)).setBackgroundColor(Color.rgb( 0x00, 0x99, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setBackgroundColor(Color.rgb( 0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setTextColor(Color.rgb(0x00, 0x99, 0xFF));
			

			break;
		case R.id.right_content_btn:
			findViewById(R.id.day_2_info_view).setVisibility(View.VISIBLE);
			findViewById(R.id.day_1_info_view).setVisibility(View.GONE);
			((Button)findViewById(R.id.left_content_btn)).setBackgroundColor(Color.rgb( 0x00, 0x99, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setBackgroundColor(Color.rgb( 0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setTextColor(Color.rgb(0x00, 0x99, 0xFF));
			
			break;
		case R.id.table_row_day_1_01:
			Log.i("DEBUG_TAG","day 1_01 來賓報到");
			break;
		case R.id.table_row_day_1_02:
			Log.i("DEBUG_TAG","day 1_02 開幕致詞");
			GlobalVars vars = GlobalVars.getSharedInstance();
			
			vars.mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay1.mClass2;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;
		case R.id.table_row_day_1_03:
			Log.i("DEBUG_TAG","day 1_03 專題演講1: 下一步，創新故事力");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay1.mClass3;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			
			break;
		case R.id.table_row_day_1_04:
			Log.i("DEBUG_TAG","day 1_04 CEO對談：會展趨勢新紀元!");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay1.mClass4;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			
			break;
		case R.id.table_row_day_1_05:
			Log.i("DEBUG_TAG","day 1_05 中場休息");
			break;
		case R.id.table_row_day_1_06:
			Log.i("DEBUG_TAG","day 1_06 專題演講2: 東京國際馬拉松!");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay1.mClass6;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			
			break;
		case R.id.table_row_day_1_07:
			Log.i("DEBUG_TAG","day 1_07 分組座1: 創新城市行銷");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay1.mClass7;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;

		case R.id.table_row_day_2_01:
			Log.i("DEBUG_TAG","day 2_01 ");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay2.mClass1;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;
		case R.id.table_row_day_2_02:
			Log.i("DEBUG_TAG","day 2_02 ");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay2.mClass2;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;			
		case R.id.table_row_day_2_04:
			Log.i("DEBUG_TAG","day 2_04 ");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay2.mClass4;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;			
		case R.id.table_row_day_2_05:
			Log.i("DEBUG_TAG","day 2_05 ");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay2.mClass5;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;			
		case R.id.table_row_day_2_07:
			Log.i("DEBUG_TAG","day 2_07 ");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay2.mClass7;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;			
		case R.id.table_row_day_2_09:
			Log.i("DEBUG_TAG","day 2_09 ");
			GlobalVars.getSharedInstance().mSelectedDayClass = GlobalVars.getSharedInstance().mAmfSchedule.mDay2.mClass9;  
			v.setBackgroundColor(Color.rgb(0x77, 0x77, 0x77));
			intent = new Intent(this, ScheduleDetailActivity.class);
			this.startActivity(intent);
			break;			
			
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int idx;
		for (idx = 0; idx < day1_ids.length; idx++) {
			View v = findViewById(day1_ids[idx]);
			if( v == null) continue;
			v.setBackgroundColor(Color.WHITE);
		}
		for (idx = 0; idx < day2_ids.length; idx++) {
			View v = findViewById(day2_ids[idx]);
			if( v == null) continue;
			v.setBackgroundColor(Color.WHITE);
		}
		
	}
	
}
