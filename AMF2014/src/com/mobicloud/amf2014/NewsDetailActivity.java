package com.mobicloud.amf2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class NewsDetailActivity extends Activity implements OnClickListener {

	public WebView mWebView;
	static public String sHtmlContent;
	public String mHtmlContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		findViewById(R.id.back_btn).setOnClickListener(this);
		mWebView = (WebView)findViewById(R.id.web_view);
		mHtmlContent = sHtmlContent;
		JsonProcessUtility.loadUtf8HtmlToWebView(mWebView,"<font color=\"#070707\">"+ mHtmlContent + "</font>");

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
