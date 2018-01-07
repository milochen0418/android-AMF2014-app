package com.mobicloud.amf2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class FacebookActivity extends Activity implements OnClickListener {
	/*
	 * 這頁叫  AMF FB() 就只是show https://m.facebook.com/asianmiceforum
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);
		findViewById(R.id.back_btn).setOnClickListener(this);
		WebView webview = (WebView)findViewById(R.id.facebook_web_view);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("https://m.facebook.com/asianmiceforum");
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
	
	
	//https://m.facebook.com/asianmiceforum

}
