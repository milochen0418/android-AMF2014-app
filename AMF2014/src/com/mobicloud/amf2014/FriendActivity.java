package com.mobicloud.amf2014;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class FriendActivity extends Activity implements OnClickListener{
/**
 * 友善連結可以參考這一頁作，但記得透過ListView 的方式，以簡單的方式將它layout 出來即可
 * http://www.amf.com.tw/link.php
 * 明確的 listview 感覺，請參考Crux 所寄的 pdf 文件
 * 
 */
	public VidAndUrlStr[] mVidAndUrlStrs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		findViewById(R.id.back_btn).setOnClickListener(this);
		
		mVidAndUrlStrs = new VidAndUrlStr[] {
			new VidAndUrlStr(R.id.friend_item_A1,"http://www.ufi.org/"),
			new VidAndUrlStr(R.id.friend_item_A2,"http://www.iccaworld.com/"),
			new VidAndUrlStr(R.id.friend_item_A3,"http://www.iaee.com/"),
			new VidAndUrlStr(R.id.friend_item_A4,"http://www.sydneyfestival.org.au/"),
			new VidAndUrlStr(R.id.friend_item_A5,"http://www.afeca.net/afeca"),
			new VidAndUrlStr(R.id.friend_item_B1,"http://www.taiwanconvention.org.tw/tcea_web/sys/index.html"),
			new VidAndUrlStr(R.id.friend_item_B2,"http://www.texco.org.tw/"),
			new VidAndUrlStr(R.id.friend_item_C1,"http://www.trade.gov.tw/"),
			new VidAndUrlStr(R.id.friend_item_C2,"http://www.meettaiwan.com/home/home_index.action?menuId=S001&request_locale=zh_TW"),
			new VidAndUrlStr(R.id.friend_item_C3,"http://www.taitra.org.tw/"),
			new VidAndUrlStr(R.id.friend_item_C4,"http://card.meettaiwan.com/"),
		};
		
		int idx;
		for (idx = 0 ;idx < mVidAndUrlStrs.length; idx++) {
			VidAndUrlStr vas = mVidAndUrlStrs[idx];
			View v = findViewById(vas.mVid);
			if(v!=null) {
				v.setOnClickListener(this);
			}
		}
	}

	@Override
	public void onClick(View v) {
		int idx;
		VidAndUrlStr vas;
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		default:
			if(mVidAndUrlStrs != null) {
				for (idx = 0; idx < mVidAndUrlStrs.length; idx++) {
					vas = mVidAndUrlStrs[idx];
					if(vid == vas.mVid) {
						launchFriendDetailActivity(vid, vas.mUrlStr);
					}
				}
			}
			break;
		}		
	}
	public void launchFriendDetailActivity(int vid, String urlStr) {
		Log.i("DEBUG_TAG","launchFriendDetailActivity with urlStr = " + urlStr );
		
		View v = findViewById(vid);
		if(v!=null) {
			v.setBackgroundColor(Color.rgb(0x00, 0x99, 0xFF));
		}
		
		//because some website will change address in defaul and activity will has white page there. 
		//so we set isLaunchExternalBrowser to open browser app to open the url  
		boolean isLaunchExternalBrowser = true;
		if(isLaunchExternalBrowser) {
			String url = urlStr;
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);			
		}
		else {
			FriendDetailActivity.sUrlStr = urlStr;
			Intent intent = new Intent(this, FriendDetailActivity.class);
			this.startActivity(intent);
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		int idx;
		for (idx = 0; idx < mVidAndUrlStrs.length; idx++) {
			VidAndUrlStr vas = mVidAndUrlStrs[idx];
			View v = findViewById(vas.mVid);
			if( v == null) continue;
			v.setBackgroundColor(Color.WHITE);
		}
		
	}
	
	
	
	public class VidAndUrlStr {
		public int mVid;
		public String mUrlStr;
		public VidAndUrlStr(int vid, String urlStr) {
			mVid = vid;
			mUrlStr = urlStr;
			
		}
	}
}
