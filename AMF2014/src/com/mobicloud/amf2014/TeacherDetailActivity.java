package com.mobicloud.amf2014;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class TeacherDetailActivity extends Activity implements OnClickListener {
	
	WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_detail);
		findViewById(R.id.back_btn).setOnClickListener(this);		
		Log.i("DEBUG_TAG", "TeacherDetailActivity onCreate() with data = " +  GlobalVars.getSharedInstance().mCurrentTeacher.toString());		
		mWebView = (WebView)findViewById(R.id.detail_webview);
		
		//JsonProcessUtility.loadUtf8HtmlToWebView(mWebView, GlobalVars.getSharedInstance().mCurrentTeacher.mContent);
		JsonProcessUtility.loadUtf8HtmlToWebView(mWebView, makeHtmlLayout(GlobalVars.getSharedInstance().mCurrentTeacher));
	}
	
	public String makeHtmlLayout(GlobalVars.AmfSpeechTeacher teacher) {
		String imgUrlStr = teacher.mImgUrl;
		String nameStr = teacher.mName;
		String jobTitleStr = teacher.mJobTitle;
		String contentStr = teacher.mContent;
/*		
		String htmlStr = 
				"<table width=100% height=50%>"+
						"<tr>"+
						"<td width=50% style='background: url("+imgUrlStr+"); background-size: 100% 100%;'>"+ "</td>"+
						"<td width=50% valign=top> <H3> <font color=#0099ff>" +nameStr+"</font></H3><br><font color=#777777>" +jobTitleStr + "</font></td>"+
						"</tr>" +
						
				"</table>"+
				"<hr>"+
				"<font color=#777777>"+
				contentStr+
				"</font>";
*/			
		String htmlStr = 
				"<table width=100%>"+
						"<tr>"+
						"<td width=50%>"+ "<img src=\"" +imgUrlStr+ "\" style=\"width:100%; height:auto;\"></td>"+
						"<td width=50% valign=top> <H3> <font color=#0099ff>" +nameStr+"</font></H3><br><font color=#777777>" +jobTitleStr + "</font></td>"+
						"</tr>" +
						
				"</table>"+
				"<hr>"+
				"<font color=#777777>"+
				contentStr+
				"</font>";		
		return htmlStr;
		
		
		//String imgUrlStr, String nameStr, String jobTitleStr, String contentStr ;
		//return null;
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
