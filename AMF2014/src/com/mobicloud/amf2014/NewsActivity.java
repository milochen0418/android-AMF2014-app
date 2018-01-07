package com.mobicloud.amf2014;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.webkit.JavascriptInterface;
public class NewsActivity extends Activity implements OnClickListener,ListView.OnItemClickListener {
	/*
	 * 通訊錄 ，看起來是透過這個地方要資料 
	 * album api 相簿列表 (用在 新聞專區 中的 相簿列表 GUI)
	 * http://www.amf.com.tw/api/en/news/album/index.php
	 * http://www.amf.com.tw/api/zh/news/album/index.php
	 * 
	 * news api 最新消息  (用在 新聞專區 中的 新聞內容 GUI)
	 * http://www.amf.com.tw/api/en/news/news_content/index.php
	 * http://www.amf.com.tw/api/zh/news/news_content/index.php
	 * 
	 * 這個頁面有兩個tab, 一個叫新聞內容，另一個叫相簿列表
	 * 新聞內部的部份 ，是以listview show 出每個新聞
	 * 相簿列表是以兩個column 的gridview方式顯示照片
	 * 
	 * listview 所顯示出的新聞要為為何，大概要先等研究出json api之後才可以確定啦
	 * 
	 * 
	 */

	public Button mLeftContentBtn;
	public Button mRightContentBtn;
	public int mTabIdx= 0; 
	public WebView mPhotoWebView;
	public WebViewClient mPhotoWebClient;
	public JsInterface mJsInterface;
	
	public void startPhotoActivityWithImageUrl(String imageUrl) {
		
		NewsPhotoActivity.sImageUrl = imageUrl;
		Intent intent = new Intent(this, NewsPhotoActivity.class);
		
		startActivity(intent);
		
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.left_content_btn).setOnClickListener(this);
		findViewById(R.id.right_content_btn).setOnClickListener(this);
		mPhotoWebView = (WebView) findViewById(R.id.photo_web_view);
		
		mPhotoWebClient = new WebViewClient(){
		 
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView  view, String  url){
		        return true;
		    }
		    // here you execute an action when the URL you want is about to load
		    @Override
		    public void onLoadResource(WebView  view, String  url){
		    	Log.i("DEBUG_TAG", "click image url = "+ url);
		    	if(url == null || url.length() < 3) return;
		    	
		    	if(url.substring(url.length()-3, url.length()).equals("jpg")) {
		    		//NewsActivity.this.startPhotoActivityWithImageUrl(url);	
		    	}
				
		    }
		};
		
		
		
		//mPhotoWebView.getSettings().setPluginsEnable(true);
		//mPhotoWebView.setWebViewClient(mPhotoWebClient);
		mJsInterface = new JsInterface(this);
		
		String photoContentHtml = makeNewsPhotoHtmlStr();
		JsonProcessUtility.loadUtf8HtmlToWebView(mPhotoWebView, photoContentHtml);
		
		mPhotoWebView.addJavascriptInterface(mJsInterface, "android");
		mPhotoWebView.getSettings().setJavaScriptEnabled(true);
		
		loadListView(0);
		
	}
	
	
	
	
	 public class JsInterface {
		 
		  // function that will be called from assets/test.js
		  // js example: android.log('my message');
		 Context mContext;
		 
		 public JsInterface(Context context) {
			 mContext = context;
		 }
		 
		 @JavascriptInterface
		 public void openimage(String imgurl) {
			 Log.i("DEBUG_TAG","openimage("+imgurl+")");
			 startPhotoActivityWithImageUrl(imgurl);
			 
		 }
		 @JavascriptInterface
		  public void log(String msg) {
		   //Log.d("MSG FROM JAVASCRIPT", msg);
		   //Toast.makeText(getApplicationContext(), "JavaScript working...", 1).show();
			 
			 
			  Log.i("DEBUG_TAG", msg);
		  }
	 }
	public String makeNewsPhotoHtmlStr() {
		String result = "";
		int idx;
		GlobalVars vars = GlobalVars.getSharedInstance();
	
		result = result + "<html>"+
		"<head>" +
		"<script type=\"text/javascript\">"+
		"function callbackmessage(imgurl){"+
		"android.log(imgurl);"+
		"android.openimage(imgurl);"+
		"}"+
		"</script>"+
		"</head>"+
		"<body>";
		
		result = result + "<table width=100% cellpadding='5'>";
		for (idx = 0 ; idx < vars.mAmfAlbum.mActivePhoto.size(); idx+=2) {
			result = result + "<tr>";
			
			//String imgUrlStr = vars.mAmfAlbum.mActivePhoto.get(idx).mImgUrl;
			//String imgUrlStr1 = vars.mAmfAlbum.mActivePhoto.get(idx+1).mImgUrl;
			int colIdx = 0;
			for (colIdx = 0; colIdx < 2; colIdx++) {
				
				if(idx+colIdx < vars.mAmfAlbum.mActivePhoto.size() ) {
					GlobalVars.AmfAlbumItem item = vars.mAmfAlbum.mActivePhoto.get(idx+colIdx);
					//result = result+"<td width=50% >"+ "<a href="+item.mImgUrl+"><img src=\"" +item.mImgUrl+ "\" style=\"width:100%; height:auto;\"></a></td>";
					result = result+
					"<td width=50% >"+ 
					//"<a href=\""+item.mImgUrl+"\" " +
					"<a href=\"javascript:void();\" " +
					" onclick=\"callbackmessage('"+item.mImgUrl+"');\" "+
					//"<a href=\"javascript:displaymessage();\" " +
					" >" +
					"<img src=\"" +item.mImgUrl+ "\" style=\"width:100%; height:auto;\">"+
					"</a>"+
					"</td>";
				}
				else {
					result = result+"<td width=50%></td>";					
				}
			}
			
			result = result + "</tr>";
			
		}
		result = result + "</table>";
		result = result + "</body></html>";
		
		return result;
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		case R.id.left_content_btn:
			this.mListView.setVisibility(View.VISIBLE);
			loadListView(0);
			((Button)findViewById(R.id.right_content_btn)).setBackgroundColor(Color.rgb( 0x00, 0x99, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setBackgroundColor(Color.rgb( 0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setTextColor(Color.rgb(0x00, 0x99, 0xFF));
			

			break;
		case R.id.right_content_btn:
			this.mListView.setVisibility(View.GONE);
			//loadListView(1);
			((Button)findViewById(R.id.left_content_btn)).setBackgroundColor(Color.rgb( 0x00, 0x99, 0xFF));
			((Button)findViewById(R.id.left_content_btn)).setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setBackgroundColor(Color.rgb( 0xFF, 0xFF, 0xFF));
			((Button)findViewById(R.id.right_content_btn)).setTextColor(Color.rgb(0x00, 0x99, 0xFF));
			
			break;
		}		
	}
	
	
	public ListView mListView;
	public ArrayAdapter mAdapter;
	public String[] mStrs;

	public void loadListView(int tabIdx) {
		mTabIdx = tabIdx;
		mListView = (ListView)findViewById(R.id.list_view);
		
		GlobalVars.AmfNews news = GlobalVars.getSharedInstance().mAmfNews;
		GlobalVars.AmfAlbum album = GlobalVars.getSharedInstance().mAmfAlbum;
		int count = 0;
		if(tabIdx == 0) {
			count = news.mNews.size();
			//count = member.mItems.size();
		}
		else if(tabIdx == 1) {
			count = album.mActivePhoto.size();
		}
		
		mStrs = new String[count];
		for (int i = 0; i < mStrs.length ; ++i ) {
			if(tabIdx == 0 ) {
				//mStrs[i] = member.mItems.get(i).mName + " - " + member.mItems.get(i).mJobTitle; 
				mStrs[i] = news.mNews.get(i).mDate + "\n" + news.mNews.get(i).mTitle;
			}else if(tabIdx == 1) {
				mStrs[i] = album.mActivePhoto.get(i).mTitle + "\n" + album.mActivePhoto.get(i).mImgUrl;
			}
		}
		mListView.setAdapter(null);
		mAdapter = new ArrayAdapter (this, R.layout.news_list_item, mStrs);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {		  
		int idx = position;
		if(mTabIdx != 0) return;
		GlobalVars vars = GlobalVars.getSharedInstance();
		String content = vars.mAmfNews.mNews.get(idx).mContent;
		NewsDetailActivity.sHtmlContent = content;
		Intent intent = new Intent(this, NewsDetailActivity.class);
		this.startActivity(intent);
	}
	

}
