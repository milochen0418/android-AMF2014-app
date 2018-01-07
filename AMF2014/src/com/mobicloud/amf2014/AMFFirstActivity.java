package com.mobicloud.amf2014;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class AMFFirstActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JsonProcessUtility.openStrictMode();
		/*
		if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD)
		{
		    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		        .detectDiskReads()
		        .detectDiskWrites()
		        .detectNetwork()   // or .detectAll() for all detectable problems
		        .penaltyLog()
		        .build());
		}	
		*/	

		
		setContentView(R.layout.activity_amffirst);
		findViewById(R.id.go_introduction_btn).setOnClickListener(this);
		findViewById(R.id.go_contact_btn).setOnClickListener(this);
		findViewById(R.id.go_friend_btn).setOnClickListener(this);
		findViewById(R.id.go_linkfb_btn).setOnClickListener(this);
		findViewById(R.id.go_news_btn).setOnClickListener(this);
		findViewById(R.id.go_personal_btn).setOnClickListener(this);
		findViewById(R.id.go_qrcode_btn).setOnClickListener(this);
		findViewById(R.id.go_teacher_btn).setOnClickListener(this);
		findViewById(R.id.go_schedule_btn).setOnClickListener(this);
		
		ImageView img  = (ImageView) findViewById(R.id.bottom_banner_img);
		img.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("DEBUG_TAG","onTouchListener");
				
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://taiwanshows.tw/afeca2014agm"));
				AMFFirstActivity.this.startActivity(intent);
				
				return false;
			}
		});
		
		
		
		
		//create all data in first page
		String lang = getResources().getConfiguration().locale.getLanguage();
		if(lang.equals("zh")){
			AmfDataHelper.setLanguage("zh");
		}
		else {
			AmfDataHelper.setLanguage("en");	
		}
		
		try{
			AmfDataHelper.setAllData();
		}
		catch(Exception e) {
			Toast.makeText(this.getBaseContext(), "網路狀態不穩定，請檢示是否連線或是頻寬通暢", Toast.LENGTH_SHORT).show();
		}
		
		
		//this.testReadWriteTextInSandbox();
		
		
	}
	
	
	


	@Override
	public void onClick(View v) {
		int vid = v.getId();
		Intent intent = null;
		GlobalVars vars  = GlobalVars.getSharedInstance();
		switch(vid) {
		case R.id.go_introduction_btn:
			Log.i("DEBUG_TAG","click go_introduction_btn");
			intent = new Intent(this, IntroductionActivity.class);
			break;
		case R.id.go_schedule_btn:
			Log.i("DEBUG_TAG","click go_schedule_btn");
			intent = new Intent(this, ScheduleActivity.class);
			break;
		case R.id.go_teacher_btn:
			Log.i("DEBUG_TAG","click go_teacher_btn");
			intent = new Intent(this, TeacherActivity.class);
			break;
		case R.id.go_personal_btn:
			Log.i("DEBUG_TAG","click go_personal_btn");
			PersonalActivity.sAmfPersonalInfomration = vars.mAmfPersonalInformation;
			intent = new Intent(this, PersonalActivity.class);
			break;
		case R.id.go_qrcode_btn:
			Log.i("DEBUG_TAG","click go_qrcode_btn");
			//intent = new Intent(this, QRCodeScanActivity.class);
			//intent = new Intent(this, CaptureActivity.class);
			//intent = new Intent (this, com.mobicloud.amf2014.zxing.CaptureActivity.class);
			intent = new Intent (this, com.google.zxing.client.android.CaptureActivity.class);
			break;
		case R.id.go_contact_btn:
			Log.i("DEBUG_TAG","click go_contact_btn");
			intent = new Intent(this, ContactActivity.class);
			break;
		case R.id.go_news_btn:
			Log.i("DEBUG_TAG","click go_news_btn");
			intent = new Intent(this, NewsActivity.class);
			break;
		case R.id.go_linkfb_btn:
			Log.i("DEBUG_TAG","click go_linkfb_btn");
			intent = new Intent(this,FacebookActivity.class);
			break;
		case R.id.go_friend_btn:
			Log.i("DEBUG_TAG","click go_friend_btn");
			intent = new Intent(this, FriendActivity.class);
			break;
		default:
			return;
		}
		if(intent != null) {
			this.startActivity(intent);
		}
		
	}
}
