package com.mobicloud.amf2014;

import java.lang.reflect.InvocationTargetException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

public class NewsPhotoActivity extends Activity implements OnClickListener {
	
	WebView mWebView;
	public static String sImageUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_photo);
		findViewById(R.id.back_btn).setOnClickListener(this);		
		//Log.i("DEBUG_TAG", "TeacherDetailActivity onCreate() with data = " +  GlobalVars.getSharedInstance().mCurrentTeacher.toString());		
		mWebView = (WebView)findViewById(R.id.photo_detail_webview);
		//letWebViewSupportZoom();
		//JsonProcessUtility.loadUtf8HtmlToWebView(mWebView, GlobalVars.getSharedInstance().mCurrentTeacher.mContent);
		JsonProcessUtility.loadUtf8HtmlToWebView(mWebView, makeHtmlLayout(sImageUrl));		
	}
	public void letWebViewSupportZoom() {
		
		//mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		//makesure you webview is not in scroll view  
		//http://stackoverflow.com/questions/7121053/how-to-enable-zoom-controls-and-pinch-zoom-in-a-webview
		if(true) return ;
		
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			    // Use the API 11+ calls to disable the controls
			    // Use a seperate class to obtain 1.6 compatibility
			    new Runnable() {
			      public void run() {
			        mWebView.getSettings().setDisplayZoomControls(false);
			      }
			    }.run();
			  } else {
			    ZoomButtonsController zoom_controll;
				try {
					zoom_controll = (ZoomButtonsController) mWebView.getClass().getMethod("getZoomButtonsController").invoke(mWebView, null);
					zoom_controll.getContainer().setVisibility(View.GONE);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			  }		
		

	}
	public String makeHtmlLayout(String imageUrl) {
		String result = "<html><head></head><body>";
		result += "<table width=100% cellpadding='1'>";
		result += "<tr>";
		result += "<td width=100%>";
		result += "<img src=\"" +imageUrl+ "\" style=\"width:100%; height:auto;\">";
		result += "</td>";
		result += "</tr>";
		result += "</table>";
		//result += "This is text with imageurl = " + imageUrl;
		result += "</body></html>";
		
		return result; 
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
