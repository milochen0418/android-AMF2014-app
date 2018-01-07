package com.mobicloud.amf2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class FriendDetailActivity extends Activity implements OnClickListener {

	static public String sUrlStr;
	public String mUrlStr;
	public WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_detail);
		mUrlStr  = sUrlStr;
		findViewById(R.id.back_btn).setOnClickListener(this);
		mWebView = (WebView) findViewById(R.id.web_view);
		JsonProcessUtility.loadUrlStrToWebView(mWebView, mUrlStr);
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
