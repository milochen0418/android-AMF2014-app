package com.mobicloud.amf2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class QRCodeScanActivity extends Activity implements OnClickListener {
/*
 *	這頁中文叫  QR掃瞄, 下方要啟動相機
 * 
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode_scan);
		findViewById(R.id.back_btn).setOnClickListener(this);
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
