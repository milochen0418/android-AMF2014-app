package com.mobicloud.amf2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class IntroductionActivity extends Activity implements OnClickListener {
/*
 * 按下"論壇簡介"之後，來到此頁面 ，此頁面的上方bar 叫作論壇介紹
 * 
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduction);
		findViewById(R.id.back_btn).setOnClickListener(this);
		WebView webview = (WebView) findViewById(R.id.introduction_web_view);
		
		String lang = getResources().getConfiguration().locale.getLanguage();
		if(lang.equals("zh")){
			//AmfDataHelper.setLanguage("zh");
			webview.loadUrl("file:///android_asset/introduction.html");
		}
		else {
			//AmfDataHelper.setLanguage("en");	
			webview.loadUrl("file:///android_asset/introduction_en.html");
		}
				
		
		
		//webview.loadUrl("file:///android_asset/introduction.html");
		webview.getSettings().setJavaScriptEnabled(true);
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		}		
	}
	

}
