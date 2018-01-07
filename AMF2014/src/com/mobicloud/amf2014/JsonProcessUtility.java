

package com.mobicloud.amf2014;

import java.io.UnsupportedEncodingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



import android.os.Build;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;



public class JsonProcessUtility {
	static public void openStrictMode () {
		if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD)
		{
		    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		        .detectDiskReads()
		        .detectDiskWrites()
		        .detectNetwork()   // or .detectAll() for all detectable problems
		        .penaltyLog()
		        .build());
		}		
	}
	
	static public void loadUtf8HtmlToWebView(WebView webView, String utf8HtmlString) {
		WebView webview = webView;
		webview.getSettings().setDefaultTextEncodingName("utf-8");
		webview.getSettings().setJavaScriptEnabled(true);
		//I comment the code because the code in android 2.3 will make failed when show some html content 
		//webview.loadData(utf8HtmlString, "text/html; charset=utf-8", null);
		webview.loadDataWithBaseURL(null, utf8HtmlString, "text/html", "utf-8", null);
	}
	
	static public void loadUrlStrToWebView(WebView webView, String urlStr) {
		if(urlStr == null) {
			return;
		}
		WebView webview = webView;
		webview.getSettings().setJavaScriptEnabled(true);		
		webview.loadUrl(urlStr);
	}
	
	static public String decodeBase64ToUtf8(String base64Str) {
		String textContentStr = base64Str;		
		byte[] data = Base64.decode(textContentStr, Base64.DEFAULT);
		String text = "base64 decoded failed";
		try {
			text = new String(data, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}

	static public String executeHttpGet(String urlStr) {
		String str = "";
		Log.i("DEBUB_TAG","executeHttpGet()");
		try {
			HttpClient client = new DefaultHttpClient();
			//HttpGet request = new HttpGet("http://www.google.com");
			HttpGet request = new HttpGet(urlStr);
			HttpResponse response = client.execute(request);
			String retSrc = EntityUtils.toString(response.getEntity());
			System.out.println(retSrc);
			Log.i("DEBUG_TAG",retSrc.substring(retSrc.length()-10, retSrc.length()));
			str = retSrc;
		} catch (Exception e) {
			Log.i("DEBUG_TAG", "exception:" + e.getMessage());
			Log.i("DEBUG_TAG", "exception:" + e.toString());
		} finally {
			
		}
		return str;
	}

	
}